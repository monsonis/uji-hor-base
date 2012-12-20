package es.uji.apps.hor.dao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hsqldb.lib.HashMap;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.ItemCircuitoDTO;
import es.uji.apps.hor.db.ItemComunDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.ItemDetalleCompletoDTO;
import es.uji.apps.hor.db.ItemDetalleDTO;
import es.uji.apps.hor.db.QDiaSemanaDTO;
import es.uji.apps.hor.db.QItemCircuitoDTO;
import es.uji.apps.hor.db.QItemComunDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.QItemDetalleCompletoDTO;
import es.uji.apps.hor.db.QItemDetalleDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.apps.hor.model.Semestre;
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
                .where(item.estudio.id.eq(estudioId).and(item.cursoId.eq(cursoId))
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
        TipoSubgrupo tipoSubgrupo = TipoSubgrupo.valueOf(tipoSubgrupoId);

        return new Calendario(tipoSubgrupo.getCalendarioAsociado(), tipoSubgrupo.getNombre());
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
                        item.cursoId.eq(cursoId).and(item.semestre.id.eq(semestreId))
                                .and(item.grupoId.eq(grupoId)).and(item.diaSemana.isNotNull())
                                .and(item.tipoSubgrupoId.in(tiposCalendarios)))).list(item);

        List<Evento> eventos = new ArrayList<Evento>();

        for (ItemDTO itemDTO : listaItemsDTO)
        {
            eventos.add(creaEventoDesde(itemDTO));
        }

        return eventos;
    }

    private List<EventoDetalle> getEventosDetalle(Evento evento)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemDetalleDTO qItemDetalleDTO = QItemDetalleDTO.itemDetalleDTO;

        query.from(qItemDetalleDTO).where(qItemDetalleDTO.item.id.eq(evento.getId()));

        List<EventoDetalle> listaEventosDetalle = new ArrayList<EventoDetalle>();

        for (ItemDetalleDTO itemDetalleDTO : query.list(qItemDetalleDTO))
        {
            listaEventosDetalle.add(creaEventoDetalleDesdeItemDetalleDTO(itemDetalleDTO));
        }
        return listaEventosDetalle;
    }

    private EventoDetalle creaEventoDetalleDesdeItemDetalleDTO(ItemDetalleDTO itemDetalleDTO)
    {
        EventoDetalle eventoDetalle = new EventoDetalle();

        eventoDetalle.setId(itemDetalleDTO.getId());
        eventoDetalle.setDescripcion(itemDetalleDTO.getDescripcion());
        eventoDetalle.setInicio(itemDetalleDTO.getInicio());

        String titulo = MessageFormat.format("{0} {1}{2}", itemDetalleDTO.getItem()
                .getAsignaturaId(), itemDetalleDTO.getItem().getTipoEstudioId(), itemDetalleDTO
                .getItem().getTipoSubgrupoId());
        eventoDetalle.setDescripcion(titulo);
        eventoDetalle.setFin(itemDetalleDTO.getFin());

        return eventoDetalle;
    }

    private Evento creaEventoConDetallesDesde(ItemDTO itemDTO)
    {
        Evento evento = creaEventoDesde(itemDTO);
        evento.setEventosDetalle(getEventosDetalle(evento));

        return evento;
    }

    private Evento creaEventoDesde(ItemDTO itemDTO)
    {
        String titulo = itemDTO.toString();
        Calendario calendario = obtenerCalendarioAsociadoPorTipoSubgrupo(itemDTO);

        Evento evento = new Evento();
        evento.setCalendario(calendario);
        evento.setTitulo(titulo);

        if (itemDTO.getHoraInicio() != null)
        {
            Calendar inicio = generaItemCalendarioSemanaGenerica(itemDTO.getDiaSemana().getId()
                    .intValue(), itemDTO.getHoraInicio());
            evento.setInicio(inicio.getTime());
        }

        if (itemDTO.getHoraFin() != null)
        {
            Calendar fin = generaItemCalendarioSemanaGenerica(itemDTO.getDiaSemana().getId()
                    .intValue(), itemDTO.getHoraFin());
            evento.setFin(fin.getTime());
        }

        evento.setDetalleManual(itemDTO.getDetalleManual());
        evento.setNumeroIteraciones(itemDTO.getNumeroIteraciones());
        evento.setRepetirCadaSemanas(itemDTO.getRepetirCadaSemanas());
        evento.setDesdeElDia(itemDTO.getDesdeElDia());
        evento.setHastaElDia(itemDTO.getHastaElDia());
        evento.setGrupoId(itemDTO.getGrupoId());
        evento.setSubgrupoId(itemDTO.getSubgrupoId());
        evento.setPlazas(itemDTO.getPlazas());
        evento.setId(itemDTO.getId());

        Estudio estudio = new Estudio();
        estudio.setId(itemDTO.getEstudio().getId());
        estudio.setNombre(itemDTO.getEstudioDesc());
        estudio.setTipoEstudio(itemDTO.getTipoEstudio());
        estudio.setTipoEstudioId(itemDTO.getTipoEstudioId());

        Asignatura asignatura = new Asignatura();

        if (itemDTO.getComun() > 0)
        {
            asignatura.setComun(itemDTO.getComun() == 1);
            asignatura.setComunes(itemDTO.getComunes());
        }

        asignatura.setNombre(itemDTO.getAsignatura());
        asignatura.setId(itemDTO.getAsignaturaId());
        asignatura.setCursoId(itemDTO.getCursoId());
        asignatura.setCaracter(itemDTO.getCaracter());
        asignatura.setCaracterId(itemDTO.getCaracterId());
        asignatura.setEstudio(estudio);
        asignatura.setPorcentajeComun(itemDTO.getPorcentajeComun());
        asignatura.setTipoAsignatura(itemDTO.getTipoAsignatura());
        asignatura.setTipoAsignaturaId(itemDTO.getTipoAsignaturaId());
        evento.setAsignatura(asignatura);

        Semestre semestre = new Semestre();
        semestre.setSemestre(itemDTO.getSemestre().getId());
        semestre.setNombre(itemDTO.getSemestre().getNombre());
        evento.setSemestre(semestre);

        if (itemDTO.getAulaPlanificacion() != null)
        {
            AulaPlanificacion aulaPlanificacion = new AulaPlanificacion();
            aulaPlanificacion.setId(itemDTO.getAulaPlanificacion().getId());
            aulaPlanificacion.setNombre(itemDTO.getAulaPlanificacionNombre());
            evento.setAulaPlanificacion(aulaPlanificacion);
        }

        return evento;
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

        List<ItemComunDTO> comunes = getItemsComunes(grupoAsignaturaId);

        item.setHoraInicio(inicio);
        item.setHoraFin(fin);
        item.setDiaSemana(diaSemanaDTO);
        item.setDetalleManual(false);
        item = update(item);

        if (item.getComun().equals(new Long(1))) // Propagamos en las asignaturas comunes
        {
            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemComun.setHoraInicio(inicio);
                    itemComun.setHoraFin(fin);
                    itemComun.setDiaSemana(diaSemanaDTO);
                    itemComun.setDetalleManual(false);
                    update(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }

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
                        item.cursoId.eq(cursoId).and(item.semestre.id.eq(semestreId))
                                .and(item.grupoId.eq(grupoId)).and(item.diaSemana.isNotNull())))
                .list(item);

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
        ItemDTO evento;
        try
        {
            evento = get(ItemDTO.class, eventoId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }

        List<ItemDTO> itemsBorrar = new ArrayList<ItemDTO>();
        itemsBorrar.add(evento);

        if (evento.getComun().equals(new Long(1)))
        {
            // Obtenemos todos los eventos comunes
            List<ItemComunDTO> comunes = getItemsComunes(eventoId);

            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemsBorrar.add(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }

        for (ItemDTO itemBorrar : itemsBorrar)
        {
            JPAQuery query = new JPAQuery(entityManager);
            QItemDTO item = QItemDTO.itemDTO;

            List<ItemDTO> listaItemsDTO = query
                    .from(item)
                    .where(item.estudio.id.eq(itemBorrar.getEstudio().getId())
                            .and(item.cursoId.eq(itemBorrar.getCursoId()))
                            .and(item.semestre.id.eq(itemBorrar.getSemestre().getId()))
                            .and(item.grupoId.eq(itemBorrar.getGrupoId()))
                            .and(item.asignaturaId.eq(itemBorrar.getAsignaturaId()))
                            .and(item.subgrupoId.eq(itemBorrar.getSubgrupoId()))
                            .and(item.tipoSubgrupoId.eq(itemBorrar.getTipoSubgrupoId()))
                            .and(item.id.ne(itemBorrar.getId()))).list(item);

            // Borramos los items detalle -- Esto se llevará a otro servicio
            JPAQuery query2 = new JPAQuery(entityManager);
            QItemDetalleDTO itemDetalle = QItemDetalleDTO.itemDetalleDTO;

            List<ItemDetalleDTO> listaItemsDetalleDTO = query2.from(itemDetalle)
                    .where(itemDetalle.item.id.eq(itemBorrar.getId())).list(itemDetalle);

            for (ItemDetalleDTO itemDetalleDTO : listaItemsDetalleDTO)
            {
                delete(ItemDetalleDTO.class, itemDetalleDTO.getId());
            }

            if (listaItemsDTO.size() > 0) // Podemos borrar la clase
            {
                // Borramos los items circuitos
                JPAQuery query3 = new JPAQuery(entityManager);
                QItemCircuitoDTO itemCircuito = QItemCircuitoDTO.itemCircuitoDTO;

                List<ItemCircuitoDTO> listaItemsCircuitosDTO = query3.from(itemCircuito)
                        .where(itemCircuito.item.id.eq(itemBorrar.getId())).list(itemCircuito);

                for (ItemCircuitoDTO itemCircuitoDTO : listaItemsCircuitosDTO)
                {
                    delete(ItemCircuitoDTO.class, itemCircuitoDTO.getId());
                }

                // Eliminamos la relación de item común
                JPAQuery query4 = new JPAQuery(entityManager);
                QItemComunDTO itemComunDTO = QItemComunDTO.itemComunDTO;

                List<ItemComunDTO> itemsComunes = query4
                        .from(itemComunDTO)
                        .where(itemComunDTO.item.id.eq(itemBorrar.getId()).or(
                                itemComunDTO.itemComun.id.eq(itemBorrar.getId())))
                        .list(itemComunDTO);

                for (ItemComunDTO itemComun : itemsComunes)
                {
                    delete(ItemComunDTO.class, itemComun.getId());
                }

                delete(ItemDTO.class, itemBorrar.getId());
            }
            else
            // Desasignamos la clase
            {
                itemBorrar.setDiaSemana(null);
                update(itemBorrar);
            }
        }

    }

    @Override
    public void divideEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException,
            EventoNoDivisibleException
    {
        ItemDTO evento = (ItemDTO) get(ItemDTO.class, eventoId).get(0);

        if (evento == null)
        {
            throw new RegistroNoEncontradoException();
        }

        // Comprobamos que el evento se pueda dividir (de momento que no tenga menos de 1h???)

        if (evento.getHoraFin().getTime() - evento.getHoraInicio().getTime() < 3600 * 1000)
        {
            throw new EventoNoDivisibleException();
        }

        List<ItemDTO> itemsDividir = new ArrayList<ItemDTO>();
        itemsDividir.add(evento);

        if (evento.getComun().equals(new Long(1)))
        {
            // Obtenemos todos los eventos comunes
            List<ItemComunDTO> comunes = getItemsComunes(eventoId);

            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemsDividir.add(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }

        // Modificamos la hora de fin del evento seleccionado
        Long horaFin = evento.getHoraInicio().getTime()
                + ((evento.getHoraFin().getTime() - evento.getHoraInicio().getTime()) / 2);

        List<ItemDTO> itemsDivididos = new ArrayList<ItemDTO>();

        for (ItemDTO itemDividir : itemsDividir)
        {
            itemDividir.setHoraFin(new Date(horaFin));
            itemDividir = update(itemDividir);

            // Creamos el nuevo evento
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setAsignatura(itemDividir.getAsignatura());
            itemDTO.setAsignaturaId(itemDividir.getAsignaturaId());
            itemDTO.setAulaPlanificacion(itemDividir.getAulaPlanificacion());
            itemDTO.setAulaPlanificacionNombre(itemDividir.getAulaPlanificacionNombre());
            itemDTO.setCaracter(itemDividir.getCaracter());
            itemDTO.setCaracterId(itemDividir.getCaracterId());
            itemDTO.setComun(itemDividir.getComun());
            itemDTO.setCursoId(itemDividir.getCursoId());
            itemDTO.setDesdeElDia(itemDividir.getDesdeElDia());
            itemDTO.setDiaSemana(itemDividir.getDiaSemana());
            itemDTO.setEstudio(itemDividir.getEstudio());
            itemDTO.setEstudioDesc(itemDividir.getEstudioDesc());
            itemDTO.setGrupoId(itemDividir.getGrupoId());
            itemDTO.setHastaElDia(itemDividir.getHastaElDia());
            itemDTO.setHoraFin(itemDividir.getHoraFin());
            itemDTO.setHoraInicio(itemDividir.getHoraInicio());
            itemDTO.setPlazas(itemDividir.getPlazas());
            itemDTO.setPorcentajeComun(itemDividir.getPorcentajeComun());
            itemDTO.setProfesor(itemDividir.getProfesor());
            itemDTO.setSemestre(itemDividir.getSemestre());
            itemDTO.setSubgrupoId(itemDividir.getSubgrupoId());
            itemDTO.setTipoAsignatura(itemDividir.getTipoAsignatura());
            itemDTO.setTipoAsignaturaId(itemDividir.getTipoAsignaturaId());
            itemDTO.setTipoEstudio(itemDividir.getTipoEstudio());
            itemDTO.setTipoEstudioId(itemDividir.getTipoEstudio());
            itemDTO.setTipoSubgrupo(itemDividir.getTipoSubgrupo());
            itemDTO.setTipoSubgrupoId(itemDividir.getTipoSubgrupoId());
            itemDTO.setDesdeElDia(itemDividir.getDesdeElDia());
            itemDTO.setHastaElDia(itemDividir.getHastaElDia());
            itemDTO.setRepetirCadaSemanas(itemDividir.getRepetirCadaSemanas());
            itemDTO.setNumeroIteraciones(itemDividir.getNumeroIteraciones());
            itemDTO.setDetalleManual(itemDividir.getDetalleManual());
            itemDTO.setComunes(itemDividir.getComunes());
            itemDTO = insert(itemDTO);

            // Copiamos los circuitos

            JPAQuery query = new JPAQuery(entityManager);
            QItemCircuitoDTO itemCircuito = QItemCircuitoDTO.itemCircuitoDTO;

            List<ItemCircuitoDTO> listaItemsCircuitosDTO = query.from(itemCircuito)
                    .where(itemCircuito.item.id.eq(itemDividir.getId())).list(itemCircuito);

            for (ItemCircuitoDTO itemCircuitoDTO : listaItemsCircuitosDTO)
            {
                ItemCircuitoDTO aux = new ItemCircuitoDTO();
                aux.setCircuito(itemCircuitoDTO.getCircuito());
                aux.setItem(itemDTO);
                aux.setPlazas(itemCircuitoDTO.getPlazas());
                insert(aux);
            }

            itemsDivididos.add(itemDTO);
        }

        // Creamos todos los items comunes relacionados
        if (evento.getComun().equals(new Long(1)) && itemsDivididos.size() > 1)
        {
            for (ItemDTO itemDividido : itemsDivididos)
            {
                for (ItemDTO itemDivididoComun : itemsDivididos)
                {
                    if (!itemDividido.getId().equals(itemDivididoComun.getId()))
                    {
                        ItemComunDTO itemComun = new ItemComunDTO();
                        itemComun.setItem(itemDividido);
                        itemComun.setAsignaturaId(itemDividido.getAsignaturaId());
                        itemComun.setItemComun(itemDivididoComun);
                        itemComun.setAsignaturaComunId(itemDivididoComun.getAsignaturaId());
                        insert(itemComun);
                    }
                }
            }
        }
    }

    @Override
    public Evento modificaDetallesGrupoAsignatura(Long grupoAsignaturaId, Date inicio, Date fin,
            Date desdeElDia, Integer numeroIteraciones, Integer repetirCadaSemanas,
            Date hastaElDia, Boolean detalleManual)
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

        List<ItemComunDTO> comunes = getItemsComunes(grupoAsignaturaId);

        item.setHoraInicio(inicio);
        item.setHoraFin(fin);
        item.setDiaSemana(diaSemanaDTO);
        item.setDesdeElDia(desdeElDia);
        item.setNumeroIteraciones(numeroIteraciones);
        item.setRepetirCadaSemanas(repetirCadaSemanas);
        item.setHastaElDia(hastaElDia);
        item.setDetalleManual(detalleManual);
        update(item);

        if (item.getComun().equals(new Long(1))) // Propagamos en las asignaturas comunes
        {
            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemComun.setHoraInicio(inicio);
                    itemComun.setHoraFin(fin);
                    itemComun.setDiaSemana(diaSemanaDTO);
                    itemComun.setDesdeElDia(desdeElDia);
                    itemComun.setNumeroIteraciones(numeroIteraciones);
                    itemComun.setRepetirCadaSemanas(repetirCadaSemanas);
                    itemComun.setHastaElDia(hastaElDia);
                    itemComun.setDetalleManual(detalleManual);
                    update(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }

        return creaEventoDesde(item);
    }

    @Override
    public List<Evento> getEventosDetalleByEventoId(Long eventoId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;
        QItemDetalleDTO detalleItem = QItemDetalleDTO.itemDetalleDTO;

        List<ItemDetalleDTO> listaItemsDTO = query.from(detalleItem).join(detalleItem.item, item)
                .where(item.id.eq(eventoId)).orderBy(detalleItem.inicio.asc()).list(detalleItem);

        List<Evento> eventos = new ArrayList<Evento>();

        for (ItemDetalleDTO itemDTO : listaItemsDTO)
        {
            eventos.add(creaEventoDesde(itemDTO));
        }

        return eventos;
    }

    @Override
    public List<EventoDocencia> getEventosDocenciaByEventoId(Long eventoId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDetalleCompletoDTO item = QItemDetalleCompletoDTO.itemDetalleCompletoDTO;

        List<ItemDetalleCompletoDTO> listaItemsDetalleCompletoDTO = query.from(item)
                .where(item.id.eq(eventoId)).orderBy(item.fecha.asc()).list(item);

        List<EventoDocencia> eventosDocencia = new ArrayList<EventoDocencia>();

        for (ItemDetalleCompletoDTO itemDetalleCompletoDTO : listaItemsDetalleCompletoDTO)
        {
            eventosDocencia.add(creaEventoDocenciaDesde(itemDetalleCompletoDTO));
        }

        return eventosDocencia;
    }

    private EventoDocencia creaEventoDocenciaDesde(ItemDetalleCompletoDTO itemDetalleCompletoDTO)
    {
        EventoDocencia eventoDocencia = new EventoDocencia();

        eventoDocencia.setEventoId(itemDetalleCompletoDTO.getId());
        eventoDocencia.setFecha(itemDetalleCompletoDTO.getFecha());
        eventoDocencia.setDocencia(itemDetalleCompletoDTO.getDocencia());
        eventoDocencia.setTipoDia(itemDetalleCompletoDTO.getTipoDia());

        return eventoDocencia;
    }

    @Override
    public Evento updateEventoConDetalleManual(Long eventoId, List<Date> fechas, Date inicio,
            Date fin) throws RegistroNoEncontradoException
    {
        ItemDTO evento;

        try
        {
            evento = get(ItemDTO.class, eventoId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }

        List<ItemComunDTO> comunes = getItemsComunes(eventoId);

        evento.setDetalleManual(true);
        evento.setHoraInicio(inicio);
        evento.setHoraFin(fin);

        evento = update(evento);

        delete(ItemDetalleDTO.class, "item_id=" + eventoId);

        List<ItemDTO> itemsActualizar = new ArrayList<ItemDTO>();
        itemsActualizar.add(evento);

        if (evento.getComun().equals(new Long(1))) // Propagamos en las asignaturas comunes
        {
            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemsActualizar.add(itemComun);
                    itemComun.setDetalleManual(true);
                    itemComun.setHoraInicio(inicio);
                    itemComun.setHoraFin(fin);

                    update(itemComun);

                    delete(ItemDetalleDTO.class, "item_id=" + itemComun.getId());
                }
                catch (Exception e)
                {
                }
            }
        }

        // Insertamos el detalle del evento y sus comunes según las fechas

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(evento.getHoraInicio());

        int horaInicio = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoInicio = calendar.get(Calendar.MINUTE);
        int segundoInicio = calendar.get(Calendar.SECOND);

        calendar.setTime(evento.getHoraFin());

        int horaFin = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoFin = calendar.get(Calendar.MINUTE);
        int segundoFin = calendar.get(Calendar.SECOND);

        // Obtenemos las fechas válidas y que no caigan en festivo

        JPAQuery query = new JPAQuery(entityManager);
        QItemDetalleCompletoDTO itemDetalleCompleto = QItemDetalleCompletoDTO.itemDetalleCompletoDTO;

        List<ItemDetalleCompletoDTO> listaItemsDetalleCompletoDTO = query
                .from(itemDetalleCompleto)
                .where(itemDetalleCompleto.id.eq(eventoId).and(itemDetalleCompleto.tipoDia.ne("F")))
                .list(itemDetalleCompleto);

        List<Date> fechasValidas = new ArrayList<Date>();
        for (ItemDetalleCompletoDTO itemDetalle : listaItemsDetalleCompletoDTO)
        {
            fechasValidas.add(itemDetalle.getFecha());
        }

        for (Date fecha : fechas)
        {
            calendar.setTime(fecha);
            calendar.add(Calendar.SECOND, segundoInicio);
            calendar.add(Calendar.MINUTE, minutoInicio);
            calendar.add(Calendar.HOUR_OF_DAY, horaInicio);

            Date fechaInicio = calendar.getTime();

            calendar.setTime(fecha);
            calendar.add(Calendar.SECOND, segundoFin);
            calendar.add(Calendar.MINUTE, minutoFin);
            calendar.add(Calendar.HOUR_OF_DAY, horaFin);

            Date fechaFin = calendar.getTime();

            for (ItemDTO item : itemsActualizar)
            {
                ItemDetalleDTO itemDetalle = new ItemDetalleDTO();
                itemDetalle.setItem(item);
                itemDetalle.setInicio(fechaInicio);
                itemDetalle.setFin(fechaFin);

                insert(itemDetalle);
            }
        }

        return creaEventoDesde(evento);
    }

    @Override
    public boolean isDetalleManualYNoCambiaDiaSemana(Long eventoId, Date inicio)
            throws RegistroNoEncontradoException
    {
        ItemDTO item;

        try
        {
            item = get(ItemDTO.class, eventoId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }

        if (item.getDetalleManual())
        {
            // Miramos el día de la semana
            JPAQuery query = new JPAQuery(entityManager);

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(inicio);

            String diaSemana = getNombreDiaSemana(calendar.get(Calendar.DAY_OF_WEEK));

            QDiaSemanaDTO qDiaSemana = QDiaSemanaDTO.diaSemanaDTO;
            query.from(qDiaSemana).where(qDiaSemana.nombre.eq(diaSemana));
            DiaSemanaDTO diaSemanaDTO = query.list(qDiaSemana).get(0);

            if (diaSemanaDTO.getId().equals(item.getDiaSemana().getId()))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public Evento updateHorasEventoDetalleManual(Long eventoId, Date inicio, Date fin)
            throws RegistroNoEncontradoException
    {
        ItemDTO evento;

        try
        {
            evento = get(ItemDTO.class, eventoId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }

        List<ItemDTO> itemsActualizar = new ArrayList<ItemDTO>();
        itemsActualizar.add(evento);

        if (evento.getComun().equals(new Long(1))) // Propagamos en las asignaturas comunes
        {
            List<ItemComunDTO> comunes = getItemsComunes(eventoId);

            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemsActualizar.add(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }

        // Establecemos las fechas para el detalle del evento

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(evento.getHoraInicio());

        int horaInicio = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoInicio = calendar.get(Calendar.MINUTE);
        int segundoInicio = calendar.get(Calendar.SECOND);

        calendar.setTime(evento.getHoraFin());

        int horaFin = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoFin = calendar.get(Calendar.MINUTE);
        int segundoFin = calendar.get(Calendar.SECOND);

        for (ItemDTO item : itemsActualizar)
        {
            item.setDetalleManual(true);
            item.setHoraInicio(inicio);
            item.setHoraFin(fin);
            item = update(item);

            List<ItemDetalleDTO> itemDetalles = get(ItemDetalleDTO.class, "item_id=" + item.getId());

            for (ItemDetalleDTO itemDetalle : itemDetalles)
            {
                calendar.setTime(itemDetalle.getInicio());
                calendar.set(Calendar.SECOND, segundoInicio);
                calendar.set(Calendar.MINUTE, minutoInicio);
                calendar.set(Calendar.HOUR_OF_DAY, horaInicio);

                itemDetalle.setInicio(calendar.getTime());

                calendar.setTime(itemDetalle.getFin());
                calendar.set(Calendar.SECOND, segundoFin);
                calendar.set(Calendar.MINUTE, minutoFin);
                calendar.set(Calendar.HOUR_OF_DAY, horaFin);

                itemDetalle.setFin(calendar.getTime());

                update(itemDetalle);
            }
        }

        return creaEventoDesde(evento);
    }

    @Override
    public List<EventoDetalle> getEventosDetalle(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO item = QItemDTO.itemDTO;
        QItemDetalleDTO itemDetalle = QItemDetalleDTO.itemDetalleDTO;
        List<String> tiposCalendarios = TipoSubgrupo.getTiposSubgrupos(calendariosIds);

        List<ItemDetalleDTO> listaItemsDetalleDTO = query
                .from(itemDetalle)
                .join(itemDetalle.item, item)
                .fetch()
                .where(itemDetalle.item.id.eq(item.id).and(
                        item.estudio.id.eq(estudioId).and(
                                item.cursoId.eq(cursoId).and(item.semestre.id.eq(semestreId))
                                        .and(itemDetalle.inicio.goe(rangoFechaInicio))
                                        .and(itemDetalle.fin.loe(rangoFechaFin))
                                        .and(item.grupoId.eq(grupoId))
                                        .and(item.diaSemana.isNotNull())
                                        .and(item.tipoSubgrupoId.in(tiposCalendarios)))))

                .list(itemDetalle);

        List<EventoDetalle> listaEventosDetalle = new ArrayList<EventoDetalle>();
        for (ItemDetalleDTO itemDetalleDTO : listaItemsDetalleDTO)
        {

            EventoDetalle eventoDetalle = creaEventoDetalleDesdeItemDetalleDTO(itemDetalleDTO);
            eventoDetalle.setEvento(creaEventoDesde(itemDetalleDTO.getItem()));
            listaEventosDetalle.add(eventoDetalle);
        }
        return listaEventosDetalle;
    }

    @Override
    public List<Evento> actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId, boolean propagar)
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        ItemDTO item;
        try
        {
            item = get(ItemDTO.class, eventoId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }
        AulaPlanificacionDTO aulaPlanificacion = null;
        String nombreAula = null;
        if (aulaId != null) // Comprobamos que el aula está asignada al estudio del evento
        {
            try
            {
                aulaPlanificacion = get(AulaPlanificacionDTO.class, aulaId).get(0);
                nombreAula = aulaPlanificacion.getAula().getCodigo();
            }
            catch (Exception e)
            {
                throw new RegistroNoEncontradoException();
            }
            if (!aulaPlanificacion.getEstudio().getId().equals(item.getEstudio().getId()))
            {
                throw new AulaNoAsignadaAEstudioDelEventoException();
            }
        }
        List<ItemDTO> items = new ArrayList<ItemDTO>();

        if (propagar)
        {
            JPAQuery query = new JPAQuery(entityManager);
            QItemDTO itemDTO = QItemDTO.itemDTO;
            items = query
                    .from(itemDTO)
                    .where(itemDTO.asignaturaId.eq(item.getAsignaturaId())
                            .and(itemDTO.estudio.id.eq(item.getEstudio().getId()))
                            .and(itemDTO.cursoId.eq(item.getCursoId()))
                            .and(itemDTO.semestre.id.eq(item.getSemestre().getId()))
                            .and(itemDTO.grupoId.eq(item.getGrupoId()))
                            .and(itemDTO.tipoSubgrupoId.eq(item.getTipoSubgrupoId()))
                            .and(itemDTO.subgrupoId.eq(item.getSubgrupoId()))
                            .and(itemDTO.id.ne(item.getId()))).list(itemDTO);
        }
        items.add(0, item);

        List<Evento> eventos = new ArrayList<Evento>();
        for (ItemDTO itemAux : items)
        {
            itemAux.setAulaPlanificacion(aulaPlanificacion);
            itemAux.setAulaPlanificacionNombre(nombreAula);
            eventos.add(creaEventoDesde(update(itemAux)));
            List<ItemComunDTO> comunes = getItemsComunes(itemAux.getId());
            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemComun.setAulaPlanificacion(aulaPlanificacion);
                    itemComun.setAulaPlanificacionNombre(nombreAula);
                    update(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }
        return eventos;
    }

    @Override
    public Evento getEventoById(Long eventoId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<ItemDTO> listaItemsDTO = query.from(item).where(item.id.eq(eventoId)).list(item);

        if (listaItemsDTO.size() == 1)
        {
            return creaEventoConDetallesDesde(listaItemsDTO.get(0));
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    public Evento insertEvento(Evento evento)
    {
        // Creamos el nuevo evento
        ItemDTO itemDTO = new ItemDTO();
        AulaPlanificacionDTO aulaPlanificacionDTO = new AulaPlanificacionDTO();
        // aulaPlanificacionDTO.setId(evento.getAulaPlanificacion().getId());

        EstudioDTO estudioDTO = new EstudioDTO();
        estudioDTO.setId(evento.getAsignatura().getEstudio().getId());

        SemestreDTO semestreDTO = new SemestreDTO();
        semestreDTO.setId(evento.getSemestre().getSemestre());
        semestreDTO.setNombre(evento.getSemestre().getNombre());

        itemDTO.setAsignatura(evento.getAsignatura().getNombre());
        itemDTO.setAsignaturaId(evento.getAsignatura().getId());
        itemDTO.setPlazas(evento.getPlazas());
        itemDTO.setComun(evento.getAsignatura().getComun() ? new Long(1) : new Long(0));
        itemDTO.setComunes(evento.getAsignatura().getComunes());
        itemDTO.setPorcentajeComun(evento.getAsignatura().getPorcentajeComun());
        itemDTO.setTipoAsignaturaId(evento.getAsignatura().getTipoAsignaturaId());
        itemDTO.setTipoAsignatura(evento.getAsignatura().getTipoAsignatura());
        itemDTO.setEstudio(estudioDTO);
        itemDTO.setEstudioDesc(evento.getAsignatura().getEstudio().getNombre());
        itemDTO.setTipoEstudioId(evento.getAsignatura().getEstudio().getTipoEstudioId());
        itemDTO.setTipoEstudio(evento.getAsignatura().getEstudio().getTipoEstudio());
        itemDTO.setCursoId(evento.getAsignatura().getCursoId());
        itemDTO.setCaracter(evento.getAsignatura().getCaracter());
        itemDTO.setCaracterId(evento.getAsignatura().getCaracterId());

        if (evento.getDia() != null)
        {
            DiaSemanaDTO diaSemanaDTO = new DiaSemanaDTO();
            diaSemanaDTO.setId(new Long(evento.getDia()));
            itemDTO.setDiaSemana(diaSemanaDTO);
        }

        itemDTO.setSemestre(semestreDTO);
        itemDTO.setGrupoId(evento.getGrupoId());
        itemDTO.setSubgrupoId(evento.getSubgrupoId());

        // itemDTO.setAulaPlanificacion(aulaPlanificacionDTO);
        itemDTO.setHastaElDia(evento.getHastaElDia());
        itemDTO.setHoraFin(evento.getFin());
        itemDTO.setHoraInicio(evento.getInicio());
        itemDTO.setDesdeElDia(evento.getDesdeElDia());
        itemDTO.setHastaElDia(evento.getHastaElDia());
        itemDTO.setRepetirCadaSemanas(evento.getRepetirCadaSemanas());
        itemDTO.setNumeroIteraciones(evento.getNumeroIteraciones());
        itemDTO.setTipoSubgrupoId(TipoSubgrupo.getTipoSubgrupo(evento.getCalendario().getId()));
        itemDTO.setTipoSubgrupo(evento.getCalendario().getNombre());
        itemDTO.setDetalleManual(evento.hasDetalleManual());
        itemDTO = insert(itemDTO);

        if (evento.hasDetalleManual())
        {
            for (EventoDetalle eventoDetalle : evento.getEventosDetalle())
            {
                ItemDetalleDTO itemDetalleDTO = new ItemDetalleDTO();
                itemDetalleDTO.setDescripcion(eventoDetalle.getDescripcion());
                itemDetalleDTO.setInicio(eventoDetalle.getInicio());
                itemDetalleDTO.setFin(eventoDetalle.getFin());
                itemDetalleDTO.setItem(itemDTO);
                insert(itemDetalleDTO);
            }
        }

        return this.creaEventoDesde(itemDTO);

    }

    @Override
    @Transactional
    public void updateHorasEvento(Evento evento)
    {
        QItemDTO qItem = QItemDTO.itemDTO;

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qItem);
        updateClause.where(qItem.id.eq(evento.getId())).set(qItem.horaInicio, evento.getInicio())
                .set(qItem.horaFin, evento.getFin()).execute();

        if (evento.hasDetalleManual())
        {
            for (EventoDetalle eventoDetalle : evento.getEventosDetalle())
            {
                updateHorasEventoDetalle(eventoDetalle);
            }
        }
    }

    @Override
    @Transactional
    public void updateHorasEventoDetalle(EventoDetalle eventoDetalle)
    {
        QItemDetalleDTO qItemDetalle = QItemDetalleDTO.itemDetalleDTO;

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qItemDetalle);
        updateClause.where(qItemDetalle.id.eq(eventoDetalle.getId()))
                .set(qItemDetalle.inicio, eventoDetalle.getInicio())
                .set(qItemDetalle.fin, eventoDetalle.getFin()).execute();

    }

    private List<ItemComunDTO> getItemsComunes(Long itemId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemComunDTO itemComunDTO = QItemComunDTO.itemComunDTO;

        query.from(itemComunDTO).where(itemComunDTO.item.id.eq(itemId));

        return query.list(itemComunDTO);
    }
}