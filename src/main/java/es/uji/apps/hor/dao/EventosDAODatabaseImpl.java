package es.uji.apps.hor.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hsqldb.lib.HashMap;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.ItemCircuitoDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.ItemDetalleDTO;
import es.uji.apps.hor.db.QDiaSemanaDTO;
import es.uji.apps.hor.db.QItemCircuitoDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.QItemDetalleDTO;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class EventosDAODatabaseImpl extends BaseDAODatabaseImpl implements EventosDAO
{
    @Override
    public List<Evento> getEventosByEstudioAndCurso(Long estudioId, Long cursoId,
            Date rangoFechasInicio, Date rangoFechasFin)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;
        QItemDetalleDTO detalleItem = QItemDetalleDTO.itemDetalleDTO;

        List<ItemDetalleDTO> listaItemsDTO = query
                .from(detalleItem)
                .join(detalleItem.item, item)
                .where(item.estudio.id.eq(estudioId).and(item.cursoId.eq(new BigDecimal(cursoId)))
                        .and(detalleItem.inicio.goe(rangoFechasInicio))
                        .and(detalleItem.fin.loe(rangoFechasFin))).list(detalleItem);

        List<Evento> eventos = new ArrayList<Evento>();

        for (ItemDetalleDTO itemDTO : listaItemsDTO)
        {
            eventos.add(creaEventoDesde(itemDTO));
        }

        return eventos;
    }

    private Evento creaEventoDesde(ItemDetalleDTO detalleItemDTO)
    {
        ItemDTO itemDTO = detalleItemDTO.getItem();
        String titulo = itemDTO.toString();

        Calendario calendario = obtenerCalendarioAsociadoPorTipoSubgrupo(itemDTO);

        Calendar inicio = creaCalendarDesdeFechaHoraInicioYFin(detalleItemDTO.getInicio());
        Calendar fin = creaCalendarDesdeFechaHoraInicioYFin(detalleItemDTO.getFin());

        return new Evento(itemDTO.getId(), calendario, titulo, inicio.getTime(), fin.getTime());
    }

    private Calendar creaCalendarDesdeFechaHoraInicioYFin(Date fecha)
    {
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(fecha);

        return inicio;
    }

    private Calendario obtenerCalendarioAsociadoPorTipoSubgrupo(ItemDTO itemDTO)
    {
        String tipoSubgrupoId = itemDTO.getTipoSubgrupoId();
        return new Calendario(TipoSubgrupo.valueOf(tipoSubgrupoId).getCalendarioAsociado());
    }

    @Override
    public List<Evento> getEventosSemanaGenerica(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<String> tiposCalendarios = TipoSubgrupo.getTiposSubgrupos(calendariosIds);

        List<ItemDTO> listaItemsDTO = query
                .from(item)
                .where(item.estudio.id.eq(estudioId).and(
                        item.cursoId.eq(new BigDecimal(cursoId))
                                .and(item.semestre.id.eq(semestreId)).and(item.grupoId.eq(grupoId))
                                .and(item.diaSemana.isNotNull())
                                .and(item.tipoSubgrupoId.in(tiposCalendarios)))).list(item);

        List<Evento> eventos = new ArrayList<Evento>();

        for (ItemDTO itemDTO : listaItemsDTO)
        {
            eventos.add(creaEventoDesde(itemDTO));
        }

        return eventos;
    }

    private Evento creaEventoDesde(ItemDTO itemDTO)
    {
        String titulo = itemDTO.toString();
        Calendario calendario = obtenerCalendarioAsociadoPorTipoSubgrupo(itemDTO);

        Calendar inicio = generaItemCalendarioSemanaGenerica(itemDTO.getDiaSemana().getId()
                .intValue(), itemDTO.getHoraInicio());
        Calendar fin = generaItemCalendarioSemanaGenerica(
                itemDTO.getDiaSemana().getId().intValue(), itemDTO.getHoraFin());
        return new Evento(itemDTO.getId(), calendario, titulo, inicio.getTime(), fin.getTime());

    }

    private Calendar generaItemCalendarioSemanaGenerica(Integer dia, Date fecha)
    {
        Calendar base = Calendar.getInstance();
        Calendar actual = Calendar.getInstance();
        actual.setTime(fecha);

        base.setFirstDayOfWeek(Calendar.MONDAY);
        Integer dayOfWeek = base.get(Calendar.DAY_OF_WEEK);
        base.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY - dayOfWeek);
        actual.set(Calendar.YEAR, base.get(Calendar.YEAR));
        actual.set(Calendar.MONTH, base.get(Calendar.MONTH));
        actual.set(Calendar.DAY_OF_MONTH, base.get(Calendar.DAY_OF_MONTH));
        actual.add(Calendar.DAY_OF_WEEK, dia - 1);

        return actual;
    }

    @Override
    public Evento modificaDiaYHoraGrupoAsignatura(Long grupoAsignaturaId, Date inicio, Date fin)
    {
        QItemDTO qItem = QItemDTO.itemDTO;
        JPAQuery query = new JPAQuery(entityManager);
        JPAQuery query2 = new JPAQuery(entityManager);

        query.from(qItem).where(qItem.id.eq(grupoAsignaturaId));
        ItemDTO item = query.list(qItem).get(0);

        Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();

        calInicio.setTime(inicio);
        calFin.setTime(fin);

        String diaSemana = getNombreDiaSemana(calInicio.get(Calendar.DAY_OF_WEEK));

        QDiaSemanaDTO qDiaSemana = QDiaSemanaDTO.diaSemanaDTO;
        query2.from(qDiaSemana).where(qDiaSemana.nombre.eq(diaSemana));
        DiaSemanaDTO diaSemanaDTO = query2.list(qDiaSemana).get(0);

        item.setHoraInicio(inicio);
        item.setHoraFin(fin);
        item.setDiaSemana(diaSemanaDTO);
        update(item);

        return creaEventoDesde(item);
    }

    private String getNombreDiaSemana(Integer diaSemana)
    {
        HashMap semana = new HashMap();
        semana.put(Calendar.SUNDAY, "Diumenge");
        semana.put(Calendar.MONDAY, "Dilluns");
        semana.put(Calendar.TUESDAY, "Dimarts");
        semana.put(Calendar.WEDNESDAY, "Dimecres");
        semana.put(Calendar.THURSDAY, "Dijous");
        semana.put(Calendar.FRIDAY, "Divendres");
        semana.put(Calendar.SATURDAY, "Dissabte");

        return (String) semana.get(diaSemana);

    }

    @Override
    public List<Evento> getEventosDeUnCurso(Long estudioId, Long cursoId, Long semestreId,
            String grupoId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO item = QItemDTO.itemDTO;

        List<ItemDTO> listaItemsDTO = query
                .from(item)
                .where(item.estudio.id.eq(estudioId).and(
                        item.cursoId.eq(new BigDecimal(cursoId))
                                .and(item.semestre.id.eq(semestreId)).and(item.grupoId.eq(grupoId))
                                .and(item.diaSemana.isNotNull()))).list(item);

        List<Evento> eventos = new ArrayList<Evento>();

        for (ItemDTO itemDTO : listaItemsDTO)
        {
            eventos.add(creaEventoDesde(itemDTO));
        }

        return eventos;
    }

    @Override
    public void deleteEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO item = QItemDTO.itemDTO;

        ItemDTO evento = (ItemDTO) get(ItemDTO.class, eventoId).get(0);
        if (evento != null)
        {
            List<ItemDTO> listaItemsDTO = query
                    .from(item)
                    .where(item.estudio.id.eq(evento.getEstudio().getId())
                            .and(item.cursoId.eq(evento.getCursoId()))
                            .and(item.semestre.id.eq(evento.getSemestre().getId()))
                            .and(item.grupoId.eq(evento.getGrupoId()))
                            .and(item.asignaturaId.eq(evento.getAsignaturaId()))
                            .and(item.subgrupoId.eq(evento.getSubgrupoId()))
                            .and(item.tipoSubgrupoId.eq(evento.getTipoSubgrupoId()))
                            .and(item.id.ne(eventoId))).list(item);

            // Borramos los items detalle

            QItemDetalleDTO itemDetalle = QItemDetalleDTO.itemDetalleDTO;

            List<ItemDetalleDTO> listaItemsDetalleDTO = query.from(itemDetalle)
                    .where(itemDetalle.item.id.eq(eventoId)).list(itemDetalle);

            for (ItemDetalleDTO itemDetalleDTO : listaItemsDetalleDTO)
            {
                delete(ItemDetalleDTO.class, itemDetalleDTO.getId());
            }

            if (listaItemsDTO.size() > 0) // Podemos borrar la clase
            {
                QItemCircuitoDTO itemCircuito = QItemCircuitoDTO.itemCircuitoDTO;

                List<ItemCircuitoDTO> listaItemsCircuitosDTO = query.from(itemCircuito)
                        .where(itemCircuito.item.id.eq(eventoId)).list(itemCircuito);
                if (listaItemsCircuitosDTO.size() > 0)
                {
                    delete(ItemCircuitoDTO.class, listaItemsCircuitosDTO.get(0).getId());
                }

                delete(ItemDTO.class, eventoId);
            }
            else
            // Desasignamos la clase
            {
                evento.setDiaSemana(null);
                update(evento);
            }
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }
}