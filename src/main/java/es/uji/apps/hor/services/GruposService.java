package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.GruposDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Grupo;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class GruposService
{
    @Autowired
    private PersonaDAO personaDAO;

    private final GruposDAO gruposDAO;

    @Autowired
    public GruposService(GruposDAO gruposDAO)
    {
        this.gruposDAO = gruposDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Grupo> getGrupos(Long semestreId, Long cursoId, Long estudioId, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return gruposDAO.getGrupos(semestreId, cursoId, estudioId);
    }
}
