package es.uji.apps.hor.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.GrupoHorarioDAO;
import es.uji.apps.hor.model.GrupoHorario;

@Service
public class GrupoHorarioService
{
    private final GrupoHorarioDAO grupoHorarioDAO;

    @Autowired
    public GrupoHorarioService(GrupoHorarioDAO grupoHorarioDAO)
    {
        this.grupoHorarioDAO = grupoHorarioDAO;
    }

    public GrupoHorario getHorarioById(Long estudioId, Long cursoId, Long semestreId, String grupoId)
    {
        return grupoHorarioDAO.getGrupoHorarioById(estudioId, cursoId, semestreId, grupoId);
    }

    public GrupoHorario addHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId,
            Date horaInicio, Date horaFin)
    {
        return grupoHorarioDAO.addHorario(estudioId, cursoId, semestreId, grupoId, horaInicio,
                horaFin);
    }

    public GrupoHorario updateHorario(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, Date horaInicio, Date horaFin)
    {
        return grupoHorarioDAO.updateHorario(estudioId, cursoId, semestreId, grupoId, horaInicio,
                horaFin);
    }
}
