package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Cargo;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class PersonaService
{
    @Autowired
    private PersonaDAO personaDAO;

    @Role({ "ADMIN", "USUARIO" })
    public Persona getPersonaById(Long connectedUserId) throws RegistroNoEncontradoException
    {
        return personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Cargo> getCargoByPersonaIdAndEstudioId(Long connectedUserId, Long estudioId) throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            persona.compruebaAccesoAEstudio(estudioId);
        }
        return persona.getCargos();
    }

}
