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

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoDetalleSinEventoException;
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
        Calendario calendario = obtenerCalendarioAsociadoPorTipoSubgrupo(itemDTO);

        Evento evento = new Evento();
        evento.setCalendario(calendario);

        if (itemDTO.getHoraInicio() != null && itemDTO.getHoraFin() != null)
        {
            Calendar inicio = generaItemCalendarioSemanaGenerica(itemDTO.getDiaSemana().getId()
                    .intValue(), itemDTO.getHoraInicio());

            Calendar fin = generaItemCalendarioSemanaGenerica(itemDTO.getDiaSemana().getId()
                    .intValue(), itemDTO.getHoraFin());

            try
            {
                evento.setFechaInicioYFin(inicio.getTime(), fin.getTime());
            }
            catch (DuracionEventoIncorrectaException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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

        asignatura.setComun(itemDTO.getComun() == 1);
        if (itemDTO.getComun() > 0)
        {
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
    public long cantidadEventosDelMismoGrupo(Evento evento)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO item = QItemDTO.itemDTO;

        return query
                .from(item)
                .where(item.estudio.id
                        .eq(evento.getAsignatura().getEstudio().getId())
                        .and(item.cursoId.eq(evento.getAsignatura().getCursoId()))
                        .and(item.semestre.id.eq(evento.getSemestre().getSemestre()))
                        .and(item.grupoId.eq(evento.getGrupoId()))
                        .and(item.asignaturaId.eq(evento.getAsignatura().getId()))
                        .and(item.subgrupoId.eq(evento.getSubgrupoId()))
                        .and(item.tipoSubgrupoId.eq(TipoSubgrupo.getTipoSubgrupo(evento
                                .getCalendario().getId())))).list(item).size();
    }

    @Override
    public void deleteEventoDetalle(EventoDetalle detalle)
    {
        delete(ItemDetalleDTO.class, detalle.getId());
    }

    @Override
    public void deleteDetallesDeEvento(Evento evento)
    {
        delete(ItemDetalleDTO.class, "item_id=" + evento.getId());

    }

    @Override
    public void deleteEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException
    {
        // Los comunes no hará falta tratarlos
        deleteCircuitosDeItem(eventoId);

        try
        {
            delete(ItemDTO.class, eventoId);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }

    }

    private void deleteCircuitosDeItem(Long itemId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemCircuitoDTO itemCircuito = QItemCircuitoDTO.itemCircuitoDTO;

        List<ItemCircuitoDTO> listaItemsCircuitosDTO = query.from(itemCircuito)
                .where(itemCircuito.item.id.eq(itemId)).list(itemCircuito);

        for (ItemCircuitoDTO itemCircuitoDTO : listaItemsCircuitosDTO)
        {
            delete(ItemCircuitoDTO.class, itemCircuitoDTO.getId());
        }
    }

    @Override
    public Evento modificaDetallesGrupoAsignatura(Evento evento)
    {
        ItemDTO item = creaItemDTODesde(evento);

        DiaSemanaDTO diaSemanaDTO = getDiaSemanaDTOParaFecha(evento.getInicio());

        item.setHoraInicio(evento.getInicio());
        item.setHoraFin(evento.getFin());
        item.setDiaSemana(diaSemanaDTO);
        item.setDesdeElDia(evento.getDesdeElDia());
        item.setNumeroIteraciones(evento.getNumeroIteraciones());
        item.setRepetirCadaSemanas(evento.getRepetirCadaSemanas());
        item.setHastaElDia(evento.getHastaElDia());
        item.setDetalleManual(evento.hasDetalleManual());
        update(item);

        return creaEventoDesde(item);
    }

    DiaSemanaDTO getDiaSemanaDTOParaFecha(Date fecha)
    {
        if (fecha == null)
        {
            return null;
        }

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(fecha);
        String diaSemana = getNombreDiaSemana(calInicio.get(Calendar.DAY_OF_WEEK));

        QDiaSemanaDTO qDiaSemana = QDiaSemanaDTO.diaSemanaDTO;
        JPAQuery query = new JPAQuery(entityManager);
        query.from(qDiaSemana).where(qDiaSemana.nombre.eq(diaSemana));
        DiaSemanaDTO diaSemanaDTO = query.list(qDiaSemana).get(0);
        return diaSemanaDTO;
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
    public List<EventoDocencia> getDiasDocenciaDeUnEventoByEventoId(Long eventoId)
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
    public void actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId)
            throws RegistroNoEncontradoException
    {
        ItemDTO item;
        AulaPlanificacionDTO aulaPlanificacion;
        try
        {
            item = get(ItemDTO.class, eventoId).get(0);
            aulaPlanificacion = get(AulaPlanificacionDTO.class, aulaId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }

        item.setAulaPlanificacion(aulaPlanificacion);
        item.setAulaPlanificacionNombre(aulaPlanificacion.getNombre());

        update(item);
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
    public Evento updateEvento(Evento evento)
    {
        ItemDTO itemDTO = creaItemDTODesde(evento);
        itemDTO = update(itemDTO);

        return creaEventoDesde(itemDTO);
    }

    @Override
    public Evento insertEvento(Evento evento)
    {
        ItemDTO itemDTO = creaItemDTODesde(evento);
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

    private ItemDTO creaItemDTODesde(Evento evento)
    {
        // Creamos el nuevo evento
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(evento.getId());
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
        return itemDTO;
    }

    @Override
    @Transactional
    public void updateHorasEventoYSusDetalles(Evento evento)
    {

        DiaSemanaDTO diaSemanaDTO = getDiaSemanaDTOParaFecha(evento.getInicio());

        QItemDTO qItem = QItemDTO.itemDTO;
        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qItem);
        updateClause.where(qItem.id.eq(evento.getId())).set(qItem.horaInicio, evento.getInicio())
                .set(qItem.horaFin, evento.getFin()).set(qItem.diaSemana, diaSemanaDTO).execute();

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

    @Override
    public EventoDetalle insertEventoDetalle(EventoDetalle eventoDetalle)
            throws EventoDetalleSinEventoException
    {
        ItemDetalleDTO itemDetalle = new ItemDetalleDTO();
        itemDetalle.setDescripcion(eventoDetalle.getDescripcion());
        itemDetalle.setInicio(eventoDetalle.getInicio());
        itemDetalle.setFin(eventoDetalle.getFin());

        ItemDTO item = new ItemDTO();
        item.setId(eventoDetalle.getEvento().getId());
        itemDetalle.setItem(item);

        itemDetalle = this.insert(itemDetalle);

        return creaItemDetalleEnEventoDetalle(itemDetalle);
    }

    private EventoDetalle creaItemDetalleEnEventoDetalle(ItemDetalleDTO itemDetalle)
            throws EventoDetalleSinEventoException
    {
        EventoDetalle eventoDetalle = new EventoDetalle();

        eventoDetalle.setDescripcion(itemDetalle.getDescripcion());
        eventoDetalle.setId(itemDetalle.getId());
        eventoDetalle.setFin(itemDetalle.getFin());
        eventoDetalle.setInicio(itemDetalle.getInicio());
        try
        {
            eventoDetalle.setEvento(getEventoById(itemDetalle.getItem().getId()));
        }
        catch (RegistroNoEncontradoException e)
        {
            throw new EventoDetalleSinEventoException();
        }

        return eventoDetalle;
    }

    @Override
    public List<Evento> getGruposComunesAEvento(Long eventoId)
    {
        ItemDTO item = get(ItemDTO.class, eventoId).get(0);

        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO qItem = QItemDTO.itemDTO;
        List<ItemDTO> items = query
                .from(qItem)
                .where(qItem.asignaturaId.eq(item.getAsignaturaId())
                        .and(qItem.estudio.id.eq(item.getEstudio().getId()))
                        .and(qItem.cursoId.eq(item.getCursoId()))
                        .and(qItem.semestre.id.eq(item.getSemestre().getId()))
                        .and(qItem.grupoId.eq(item.getGrupoId()))
                        .and(qItem.tipoSubgrupoId.eq(item.getTipoSubgrupoId()))
                        .and(qItem.subgrupoId.eq(item.getSubgrupoId()))
                        .and(qItem.id.ne(item.getId()))).list(qItem);

        List<Evento> eventos = new ArrayList<Evento>();
        for (ItemDTO itemDTO : items)
        {
            eventos.add(creaEventoDesde(itemDTO));
        }

        return eventos;
    }
}