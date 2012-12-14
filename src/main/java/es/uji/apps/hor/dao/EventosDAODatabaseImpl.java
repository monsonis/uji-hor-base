package es.uji.apps.hor.dao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hsqldb.lib.HashMap;
import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.QTuple;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.db.AsignaturaComunDTO;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.ItemCircuitoDTO;
import es.uji.apps.hor.db.ItemComunDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.ItemDetalleCompletoDTO;
import es.uji.apps.hor.db.ItemDetalleDTO;
import es.uji.apps.hor.db.QAsignaturaComunDTO;
import es.uji.apps.hor.db.QDiaSemanaDTO;
import es.uji.apps.hor.db.QItemCircuitoDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.QItemDetalleCompletoDTO;
import es.uji.apps.hor.db.QItemDetalleDTO;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDocencia;
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

    private Evento creaEventoDesde(ItemDTO itemDTO)
    {
        String titulo = itemDTO.toString();
        Calendario calendario = obtenerCalendarioAsociadoPorTipoSubgrupo(itemDTO);

        Calendar inicio = generaItemCalendarioSemanaGenerica(itemDTO.getDiaSemana().getId()
                .intValue(), itemDTO.getHoraInicio());
        Calendar fin = generaItemCalendarioSemanaGenerica(
                itemDTO.getDiaSemana().getId().intValue(), itemDTO.getHoraFin());
        Evento evento = new Evento(itemDTO.getId(), calendario, titulo, inicio.getTime(),
                fin.getTime());

        evento.setDetalleManual(itemDTO.getDetalleManual());
        evento.setNumeroIteraciones(itemDTO.getNumeroIteraciones());
        evento.setRepetirCadaSemanas(itemDTO.getRepetirCadaSemanas());
        evento.setDesdeElDia(itemDTO.getDesdeElDia());
        evento.setHastaElDia(itemDTO.getHastaElDia());
        if (itemDTO.getComun() > 0)
        {
            evento.setComunes(itemDTO.getComunes());
        }
        if (itemDTO.getAulasPlanificacion() != null)
        {
            evento.setAulaPlanificacionId(itemDTO.getAulasPlanificacion().getId());
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

        List<ItemComunDTO> comunes = get(ItemComunDTO.class, grupoAsignaturaId);

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

            // Borramos los items detalle -- Esto se llevará a otro servicio
            JPAQuery query2 = new JPAQuery(entityManager);
            QItemDetalleDTO itemDetalle = QItemDetalleDTO.itemDetalleDTO;

            List<ItemDetalleDTO> listaItemsDetalleDTO = query2.from(itemDetalle)
                    .where(itemDetalle.item.id.eq(eventoId)).list(itemDetalle);

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
                        .where(itemCircuito.item.id.eq(eventoId)).list(itemCircuito);

                for (ItemCircuitoDTO itemCircuitoDTO : listaItemsCircuitosDTO)
                {
                    delete(ItemCircuitoDTO.class, itemCircuitoDTO.getId());
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

        // Modificamos la hora de fin del evento seleccionado
        Long horaFin = evento.getHoraInicio().getTime()
                + ((evento.getHoraFin().getTime() - evento.getHoraInicio().getTime()) / 2);

        evento.setHoraFin(new Date(horaFin));
        evento = update(evento);

        // Creamos el nuevo evento
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setAsignatura(evento.getAsignatura());
        itemDTO.setAsignaturaId(evento.getAsignaturaId());
        itemDTO.setAulasPlanificacion(evento.getAulasPlanificacion());
        itemDTO.setCaracter(evento.getCaracter());
        itemDTO.setCaracterId(evento.getCaracterId());
        itemDTO.setComun(evento.getComun());
        itemDTO.setCursoId(evento.getCursoId());
        itemDTO.setDesdeElDia(evento.getDesdeElDia());
        itemDTO.setDiaSemana(evento.getDiaSemana());
        itemDTO.setEstudio(evento.getEstudio());
        itemDTO.setEstudioDesc(evento.getEstudioDesc());
        itemDTO.setGrupoId(evento.getGrupoId());
        itemDTO.setHastaElDia(evento.getHastaElDia());
        itemDTO.setHoraFin(evento.getHoraFin());
        itemDTO.setHoraInicio(evento.getHoraInicio());
        itemDTO.setPlazas(evento.getPlazas());
        itemDTO.setPorcentajeComun(evento.getPorcentajeComun());
        itemDTO.setProfesor(evento.getProfesor());
        itemDTO.setSemestre(evento.getSemestre());
        itemDTO.setSubgrupoId(evento.getSubgrupoId());
        itemDTO.setTipoAsignatura(evento.getTipoAsignatura());
        itemDTO.setTipoAsignaturaId(evento.getTipoAsignaturaId());
        itemDTO.setTipoEstudio(evento.getTipoEstudio());
        itemDTO.setTipoEstudioId(evento.getTipoEstudio());
        itemDTO.setTipoSubgrupo(evento.getTipoSubgrupo());
        itemDTO.setTipoSubgrupoId(evento.getTipoSubgrupoId());
        itemDTO.setDesdeElDia(evento.getDesdeElDia());
        itemDTO.setHastaElDia(evento.getHastaElDia());
        itemDTO.setRepetirCadaSemanas(evento.getRepetirCadaSemanas());
        itemDTO.setNumeroIteraciones(evento.getNumeroIteraciones());
        itemDTO.setDetalleManual(evento.getDetalleManual());
        itemDTO = insert(itemDTO);

        // Copiamos los circuitos

        JPAQuery query = new JPAQuery(entityManager);
        QItemCircuitoDTO itemCircuito = QItemCircuitoDTO.itemCircuitoDTO;

        List<ItemCircuitoDTO> listaItemsCircuitosDTO = query.from(itemCircuito)
                .where(itemCircuito.item.id.eq(eventoId)).list(itemCircuito);

        for (ItemCircuitoDTO itemCircuitoDTO : listaItemsCircuitosDTO)
        {
            ItemCircuitoDTO aux = new ItemCircuitoDTO();
            aux.setCircuito(itemCircuitoDTO.getCircuito());
            aux.setItem(itemDTO);
            aux.setPlazas(itemCircuitoDTO.getPlazas());
            insert(aux);
        }

        // ¿Lo mismo para cada común?
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

        List<ItemComunDTO> comunes = get(ItemComunDTO.class, grupoAsignaturaId);

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

        List<ItemComunDTO> comunes = get(ItemComunDTO.class, eventoId);

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
            List<ItemComunDTO> comunes = get(ItemComunDTO.class, eventoId);

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
    public List<Evento> getEventosDetalle(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO item = QItemDTO.itemDTO;
        QItemDetalleDTO itemDetalle = QItemDetalleDTO.itemDetalleDTO;
        List<String> tiposCalendarios = TipoSubgrupo.getTiposSubgrupos(calendariosIds);

        List<Tuple> listaTuplas = query
                .from(item, itemDetalle)
                .where(itemDetalle.item.id.eq(item.id).and(
                        item.estudio.id.eq(estudioId).and(
                                item.cursoId.eq(cursoId).and(item.semestre.id.eq(semestreId))
                                        .and(itemDetalle.inicio.goe(rangoFechaInicio))
                                        .and(itemDetalle.fin.loe(rangoFechaFin))
                                        .and(item.grupoId.eq(grupoId))
                                        .and(item.diaSemana.isNotNull())
                                        .and(item.tipoSubgrupoId.in(tiposCalendarios)))))
                .list(new QTuple(itemDetalle.id, itemDetalle.inicio, itemDetalle.fin,
                        item.asignaturaId, item.tipoSubgrupoId, item.subgrupoId));

        System.out.println(listaTuplas);
        List<Evento> eventos = new ArrayList<Evento>();

        for (Tuple tupla : listaTuplas)
        {
            String titulo = MessageFormat.format("{0} {1}{2}", tupla.get(item.asignaturaId),
                    tupla.get(item.tipoSubgrupoId), tupla.get(item.subgrupoId));
            Calendario calendario = new Calendario(TipoSubgrupo.valueOf(
                    tupla.get(item.tipoSubgrupoId)).getCalendarioAsociado());
            eventos.add(new Evento(tupla.get(itemDetalle.id), calendario, titulo, tupla
                    .get(itemDetalle.inicio), tupla.get(itemDetalle.fin)));
        }
        return eventos;

    }

    @Override
    public Evento actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId, boolean propagarComunes)
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

        if (aulaId != null) // Comprobamos que el aula está asignada al estudio del evento
        {
            AulaPlanificacionDTO aula;
            try
            {
                aula = get(AulaPlanificacionDTO.class, aulaId).get(0);
            }
            catch (Exception e)
            {
                throw new RegistroNoEncontradoException();
            }

            if (!aula.getEstudio().getId().equals(item.getEstudio().getId()))
            {
                throw new AulaNoAsignadaAEstudioDelEventoException();
            }

            item.setAulasPlanificacion(aula);
        }
        else
        {
            item.setAulasPlanificacion(null);
        }

        Evento evento = creaEventoDesde(update(item));

        if (propagarComunes && item.getComun().equals(new Long(1)))
        {
            List<ItemComunDTO> comunes = get(ItemComunDTO.class, eventoId);

            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemComun.setAulasPlanificacion(item.getAulasPlanificacion());
                    update(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }

        return evento;
    }
}