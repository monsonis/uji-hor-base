package es.uji.apps.hor.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Evento;

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
}
