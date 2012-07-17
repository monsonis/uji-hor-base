package es.uji.apps.hor.services;

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

    public GrupoHorario getHorarioById(String grupoId)
    {
        return grupoHorarioDAO.getGrupoHorarioById(grupoId);
    }
}
