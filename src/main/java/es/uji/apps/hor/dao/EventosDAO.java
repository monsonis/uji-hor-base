package es.uji.apps.hor.dao;

import java.util.Date;
import java.util.List;

import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface EventosDAO extends BaseDAO
{
    List<Evento> getEventosSemanaGenerica(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds);

    List<Evento> getEventosDeUnCurso(Long estudioId, Long cursoId, Long semestreId, String grupoId);

    void deleteEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException;

    List<EventoDocencia> getDiasDocenciaDeUnEventoByEventoId(Long eventoId);

    List<EventoDetalle> getEventosDetalle(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin);

    void actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId)
            throws RegistroNoEncontradoException;

    Evento getEventoById(Long eventoId) throws RegistroNoEncontradoException;

    Evento insertEvento(Evento eventoDividido);

    void updateHorasEventoYSusDetalles(Evento evento);

    void updateHorasEventoDetalle(EventoDetalle eventoDetalle);

    EventoDetalle insertEventoDetalle(EventoDetalle eventoDetalle)
            throws EventoDetalleSinEventoException;

    long cantidadEventosDelMismoGrupo(Evento evento);

    void deleteEventoDetalle(EventoDetalle detalle);

    List<Evento> getGruposComunesAEvento(Long eventoId);

    void deleteDetallesDeEvento(Evento evento);

    Evento updateEvento(Evento evento);

    Evento modificaDetallesGrupoAsignatura(Evento evento);

    Asignatura creaAsignaturaDesdeItemDTOParaUnEstudio(ItemDTO itemDTO, Long estudioId);

}
