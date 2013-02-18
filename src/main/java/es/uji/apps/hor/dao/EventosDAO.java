package es.uji.apps.hor.dao;

import java.util.Date;
import java.util.List;

import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.ItemsAsignaturaDTO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface EventosDAO extends BaseDAO
{
    List<Evento> getEventosSemanaGenerica(Long estudioId, Long cursoId, Long semestreId,
            List<String> gruposIds, List<Long> calendariosIds);

    List<Evento> getEventosDeUnCurso(Long estudioId, Long cursoId, Long semestreId, String grupoId);

    void deleteEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException;

    List<EventoDocencia> getDiasDocenciaDeUnEventoByEventoId(Long eventoId);

    List<EventoDetalle> getEventosDetalle(Long estudioId, Long cursoId, Long semestreId,
            List<String> gruposIds, List<Long> calendariosIds, Date rangoFechaInicio,
            Date rangoFechaFin);

    void actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId)
            throws RegistroNoEncontradoException;

    Evento getEventoById(Long eventoId) throws RegistroNoEncontradoException;

    Evento insertEvento(Evento eventoDividido);

    void updateHorasEventoYSusDetalles(Evento evento);

    void updateHorasEventoDetalle(EventoDetalle eventoDetalle);

    EventoDetalle insertEventoDetalle(EventoDetalle eventoDetalle)
            throws EventoDetalleSinEventoException;

    void deleteEventoDetalle(EventoDetalle detalle);

    void deleteDetallesDeEvento(Evento evento);

    Evento updateEvento(Evento evento);

    Evento modificaDetallesGrupoAsignatura(Evento evento);

    Asignatura creaAsignaturasDesdeItemAsignaturaDTO(ItemsAsignaturaDTO asig, ItemDTO itemDTO);

    void desasignaAula(Long eventoId) throws RegistroNoEncontradoException;

    void desplanificaEvento(Evento evento);

    List<EventoDetalle> getEventosDetallePorAula(Long aulaId, Long semestreId,
            List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin);

    List<Evento> getEventosSemanaGenericaPorAula(Long aulaId, Long semestreId,
            List<Long> calendariosIds);

    List<Evento> getEventosDelMismoGrupo(Evento evento);

    Evento getEventoByIdConDetalles(Long eventoId) throws RegistroNoEncontradoException;

    Evento getEventoByEventoDetalleId(Long eventoDetalleId) throws RegistroNoEncontradoException;
}
