package es.uji.apps.hor.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.RangoHorarioFueradeLimites;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.dao.RangoHorarioDAO;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.RangoHorario;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Service
public class RangoHorarioService
{
    private final RangoHorarioDAO rangoHorarioDAO;
    private final EventosDAO eventosDAO;

    @Autowired
    public RangoHorarioService(RangoHorarioDAO rangoHorarioDAO, EventosDAO eventosDAO)
    {
        this.rangoHorarioDAO = rangoHorarioDAO;
        this.eventosDAO = eventosDAO;
    }

    public RangoHorario getHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId)
    {
        try
        {
            return rangoHorarioDAO.getRangoHorario(estudioId, cursoId, semestreId, grupoId);
        }
        catch (RegistroNoEncontradoException e)
        {
            return RangoHorario.getRangoHorarioPorDefecto(estudioId, cursoId, semestreId, grupoId);
        }

    }

    public RangoHorario guardaConfiguracionRangoHorario(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, Date horaInicio, Date horaFin)
            throws RangoHorarioFueradeLimites
    {
        RangoHorario rangoHorario;

        try
        {
            rangoHorario = rangoHorarioDAO.getRangoHorario(estudioId, cursoId, semestreId, grupoId);
            rangoHorario.actualizaRangoHorario(horaInicio, horaFin);
        }
        catch (RegistroNoEncontradoException e)
        {
            rangoHorario = RangoHorario.creaNuevoRangoHorario(estudioId, cursoId, semestreId,
                    grupoId, horaInicio, horaFin);
        }

        List<Evento> eventos = eventosDAO.getEventosDeUnCurso(estudioId, cursoId, semestreId,
                grupoId);
        rangoHorario.compruebaSiLosEventosEstanDentroDelRangoHorario(eventos);

        if (rangoHorario.getId() != null)
        {
            rangoHorario = rangoHorarioDAO.updateHorario(rangoHorario);
        }
        else
        {
            rangoHorario = rangoHorarioDAO.addHorario(rangoHorario);
        }

        return rangoHorario;
    }
}
