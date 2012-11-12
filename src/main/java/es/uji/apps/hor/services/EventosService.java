package es.uji.apps.hor.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Service
public class EventosService
{
    private final EventosDAO eventosDAO;

    @Autowired
    public EventosService(EventosDAO eventosDAO)
    {
        this.eventosDAO = eventosDAO;
    }

    public List<Evento> eventosDeUnEstudio(Long estudioId, Long cursoId, Date rangoFechasInicio,
            Date rangoFechasFin)
    {
        return eventosDAO.getEventosByEstudioAndCurso(estudioId, cursoId, rangoFechasInicio,
                rangoFechasFin);
    }

    public List<Evento> eventosSemanaGenericaDeUnEstudio(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds)
    {
        return eventosDAO.getEventosSemanaGenerica(estudioId, cursoId, semestreId, grupoId,
                calendariosIds);
    }

    public Evento modificaDiaYHoraGrupoAsignatura(Long grupoAsignaturaId, Date inicio, Date fin)
            throws DuracionEventoIncorrectaException
    {
        Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();

        calInicio.setTime(inicio);
        calFin.setTime(fin);

        if (calInicio.get(Calendar.YEAR) == calFin.get(Calendar.YEAR)
                && calInicio.get(Calendar.MONTH) == calFin.get(Calendar.MONTH)
                && calInicio.get(Calendar.DAY_OF_MONTH) == calFin.get(Calendar.DAY_OF_MONTH)
                && calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            return eventosDAO.modificaDiaYHoraGrupoAsignatura(grupoAsignaturaId, inicio, fin);
        }
        else
        {
            throw new DuracionEventoIncorrectaException();
        }
    }

    public void deleteEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException
    {
        eventosDAO.deleteEventoSemanaGenerica(eventoId);
    }

    public void divideEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException,
            EventoNoDivisibleException
    {
        eventosDAO.divideEventoSemanaGenerica(eventoId);
    }

    public Evento modificaDetallesGrupoAsignatura(Long grupoAsignaturaId, Date inicio, Date fin,
            Date desdeElDia, Integer numeroIteraciones, Integer repetirCadaSemanas, Date hastaElDia)
            throws DuracionEventoIncorrectaException
    {
        Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();

        calInicio.setTime(inicio);
        calFin.setTime(fin);

        if (calInicio.get(Calendar.YEAR) == calFin.get(Calendar.YEAR)
                && calInicio.get(Calendar.MONTH) == calFin.get(Calendar.MONTH)
                && calInicio.get(Calendar.DAY_OF_MONTH) == calFin.get(Calendar.DAY_OF_MONTH)
                && calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            return eventosDAO.modificaDetallesGrupoAsignatura(grupoAsignaturaId, inicio, fin,
                    desdeElDia, numeroIteraciones, repetirCadaSemanas, hastaElDia);
        }
        else
        {
            throw new DuracionEventoIncorrectaException();
        }

    }

    public List<Evento> getEventosDetalleByEventoId(Long eventoId)
    {
        return eventosDAO.getEventosDetalleByEventoId(eventoId);
    }

    public List<EventoDocencia> getEventosDocenciaByEventoId(Long eventoId)
    {
        return eventosDAO.getEventosDocenciaByEventoId(eventoId);
    }

    public Evento updateEventoConDetalleManual(Long eventoId, List<Date> fechas, Date inicio,
            Date fin) throws RegistroNoEncontradoException
    {
        return eventosDAO.updateEventoConDetalleManual(eventoId, fechas, inicio, fin);
    }

    public boolean isDetalleManualYNoCambiaDiaSemana(Long eventoId, Date inicio)
            throws RegistroNoEncontradoException
    {
        return eventosDAO.isDetalleManualYNoCambiaDiaSemana(eventoId, inicio);
    }

    public Evento updateHorasEventoDetalleManual(Long eventoId, Date inicio, Date fin)
            throws RegistroNoEncontradoException
    {
        return eventosDAO.updateHorasEventoDetalleManual(eventoId, inicio, fin);
    }
}
