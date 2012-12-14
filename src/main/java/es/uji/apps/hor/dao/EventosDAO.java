package es.uji.apps.hor.dao;

import java.util.Date;
import java.util.List;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.model.Evento;
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

    Evento modificaDetallesGrupoAsignatura(Long grupoAsignaturaId, Date inicio, Date fin,
            Date desdeElDia, Integer numeroIteraciones, Integer repetirCadaSemanas,
            Date hastaElDia, Boolean detalleManual);

    List<Evento> getEventosDetalleByEventoId(Long eventoId);

    List<EventoDocencia> getEventosDocenciaByEventoId(Long eventoId);

    Evento updateEventoConDetalleManual(Long eventoId, List<Date> fechas, Date inicio, Date fin)
            throws RegistroNoEncontradoException;

    boolean isDetalleManualYNoCambiaDiaSemana(Long eventoId, Date inicio)
            throws RegistroNoEncontradoException;

    Evento updateHorasEventoDetalleManual(Long eventoId, Date inicio, Date fin)
            throws RegistroNoEncontradoException;

    List<Evento> getEventosDetalle(Long estudioId, Long cursoId, Long semestreId, String grupoId,
            List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin);

    List<Evento> actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId, boolean propagar)
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException;
}
