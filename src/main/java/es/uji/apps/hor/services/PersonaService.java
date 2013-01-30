package es.uji.apps.hor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

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

}
