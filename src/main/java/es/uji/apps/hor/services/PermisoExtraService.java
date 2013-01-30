package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.PermisoExtraDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.PermisoExtra;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class PermisoExtraService
{
    @Autowired
    private PermisoExtraDAO permisoExtraDAO;

    @Autowired
    private PersonaDAO personaDAO;

    @Role({ "ADMIN", "USUARIO" })
    public List<PermisoExtra> getPermisosExtra(Long connectedUserId)
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            return permisoExtraDAO.getPermisosExtra();
        }
        else
        {
            return permisoExtraDAO.getPermisosExtraByPersonaId(connectedUserId);
        }
    }

    @Role({ "ADMIN", "USUARIO" })
    public PermisoExtra addPermisosExtra(Long estudioId, Long personaId, Long tipoCargoId,
            Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaById(connectedUserId);
            persona.compruebaAsignacionPermisoEstudioId(estudioId, tipoCargoId);
        }
        
        return permisoExtraDAO.addPermisoExtra(estudioId, personaId, tipoCargoId, connectedUserId);
    }
}
