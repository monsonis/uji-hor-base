package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.PermisoExtraDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.PermisoExtra;
import es.uji.commons.rest.Role;

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
        else {
            return permisoExtraDAO.getPermisosExtraByPersonaId(connectedUserId);
        }
    }
}
