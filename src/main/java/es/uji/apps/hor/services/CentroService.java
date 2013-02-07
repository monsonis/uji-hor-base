package es.uji.apps.hor.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Cargo;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class CentroService
{
    private final CentroDAO centroDAO;

    @Autowired
    private PersonaDAO personaDAO;

    @Autowired
    public CentroService(CentroDAO centroDAO)
    {
        this.centroDAO = centroDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Centro> getCentros(Long connectedUserId) throws RegistroNoEncontradoException
    {
        if (personaDAO.esAdmin(connectedUserId))
        {
            return centroDAO.getCentros();
        }
        else
        {
            return centroDAO.getCentrosVisiblesPorUsuario(connectedUserId);
        }
    }

    @Role({ "ADMIN", "USUARIO" })
    public Centro getCentroById(Long centroId, Long connectedUserId)
            throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoACentro(centroId);
        }

        Centro centro = centroDAO.getCentroById(centroId);

        return centro;
    }

    public List<Centro> getCentrosAGestionar(Long connectedUserId) throws RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            List<Long> listaCargosIds = new ArrayList<Long>();

            for (Cargo cargo : persona.getCargos())
            {
                if (cargo.getId().equals(Cargo.DIRECTOR_CENTRO)
                        || cargo.getId().equals(Cargo.PAS_CENTRO))
                {
                    listaCargosIds.add(cargo.getId());
                }
            }
            return Collections.singletonList(centroDAO.getCentroGestionablePorUsuario(connectedUserId, listaCargosIds));
        }
        else
        {
            return centroDAO.getCentros();
        }
    }
}
