package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.GruposDAO;
import es.uji.apps.hor.model.Grupo;
import es.uji.commons.rest.Role;

@Service
public class GruposService
{
    private final GruposDAO gruposDAO;

    @Autowired
    public GruposService(GruposDAO gruposDAO)
    {
        this.gruposDAO = gruposDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Grupo> getGrupos(Long semestreId, Long cursoId, Long estudioId, Long connectedUserId)
    {
        return gruposDAO.getGrupos(semestreId, cursoId, estudioId);
    }
}
