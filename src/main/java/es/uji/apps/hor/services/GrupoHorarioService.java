package es.uji.apps.hor.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.RangoHorarioFueradeLimites;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.dao.GrupoHorarioDAO;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.GrupoHorario;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Service
public class GrupoHorarioService
{
    private final GrupoHorarioDAO grupoHorarioDAO;
    private final EventosDAO eventosDAO;

    @Autowired
    public GrupoHorarioService(GrupoHorarioDAO grupoHorarioDAO, EventosDAO eventosDAO)
    {
        this.grupoHorarioDAO = grupoHorarioDAO;
        this.eventosDAO = eventosDAO;
    }

    public GrupoHorario getHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId)
            throws RegistroNoEncontradoException
    {
        return grupoHorarioDAO.getGrupoHorario(estudioId, cursoId, semestreId, grupoId);
    }

    public GrupoHorario guardaConfiguracionGrupoHorario(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, Date horaInicio, Date horaFin)
            throws RangoHorarioFueradeLimites
    {
        GrupoHorario grupoHorario;

        try
        {
            grupoHorario = grupoHorarioDAO.getGrupoHorario(estudioId, cursoId, semestreId, grupoId);
            grupoHorario.actualizaRangoHorario(horaInicio, horaFin);
        }
        catch (RegistroNoEncontradoException e)
        {
            grupoHorario = GrupoHorario.creaNuevoRangoHorario(estudioId, cursoId, semestreId,
                    grupoId, horaInicio, horaFin);
        }

        List<Evento> eventos = eventosDAO.getEventosDeUnCurso(estudioId, cursoId, semestreId,
                grupoId);
        grupoHorario.compruebaSiLosEventosEstanDentroDelRangoHorario(eventos);

        if (grupoHorario.getId() != null)
        {
            grupoHorario = grupoHorarioDAO.updateHorario(grupoHorario);
        }
        else
        {
            grupoHorario = grupoHorarioDAO.addHorario(grupoHorario);
        }

        return grupoHorario;
    }
}
