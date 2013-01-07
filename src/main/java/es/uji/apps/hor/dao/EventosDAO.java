package es.uji.apps.hor.dao;

import java.util.Date;
import java.util.List;

import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface EventosDAO extends BaseDAO
{
    List<Evento> getEventosByEstudioAndCurso(Long estudioId, Long cursoId, Date rangoFechasInicio,
            Date rangoFechasFin);

    List<Evento> getEventosSemanaGenerica(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds);

    Evento modificaDiaYHoraGrupoAsignatura(Long grupoAsignaturaId, Date inicio, Date fin);

    List<Evento> getEventosDeUnCurso(Long estudioId, Long cursoId, Long semestreId, String grupoId);

    void deleteEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException;

    void divideEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException,
            EventoNoDivisibleException;

    List<Evento> getEventosDetalleByEventoId(Long eventoId);

    List<EventoDocencia> getEventosDocenciaByEventoId(Long eventoId);

    Evento updateEventoConDetalleManual(Long eventoId, List<Date> fechas, Date inicio, Date fin)
            throws RegistroNoEncontradoException;

    boolean isDetalleManualYNoCambiaDiaSemana(Long eventoId, Date inicio)
            throws RegistroNoEncontradoException;

    Evento updateHorasEventoDetalleManual(Long eventoId, Date inicio, Date fin)
            throws RegistroNoEncontradoException;

    List<EventoDetalle> getEventosDetalle(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin);

    void actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId)
            throws RegistroNoEncontradoException;

    Evento getEventoById(Long eventoId) throws RegistroNoEncontradoException;

    Evento insertEvento(Evento eventoDividido);

    void updateHorasEvento(Evento evento);

    void updateHorasEventoDetalle(EventoDetalle eventoDetalle);

    void updateDiaYHoraEvento(Evento evento);

    EventoDetalle insertEventoDetalle(EventoDetalle eventoDetalle)
            throws EventoDetalleSinEventoException;

    long eventosDelMismoGrupo(Evento evento);

    void deleteEventoDetalle(EventoDetalle detalle);

    List<Evento> getGruposComunesAEvento(Long eventoId);

    void deleteDetallesDeEvento(Evento evento);

    Evento updateEvento(Evento evento);

    Evento modificaDetallesGrupoAsignatura(Evento evento);
}
