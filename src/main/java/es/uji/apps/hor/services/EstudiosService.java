package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class EstudiosService
{
    @Autowired
    private PersonaDAO personaDAO;

    private final EstudiosDAO estudiosDAO;

    @Autowired
    public EstudiosService(EstudiosDAO estudiosDAO)
    {
        this.estudiosDAO = estudiosDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Estudio> getEstudios(Long connectedUserId) throws RegistroNoEncontradoException
    {
        Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
        if (personaDAO.esAdmin(connectedUserId) || persona.esGestorDeCentro())
        {
            return estudiosDAO.getEstudios();
        }
        else
        {
            return estudiosDAO.getEstudiosVisiblesPorUsuario(connectedUserId);
        }
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Estudio> getEstudiosByCentroId(Long centroId, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            persona.compruebaAccesoACentro(centroId);
        }
        return estudiosDAO.getEstudiosByCentroId(centroId);
    }

}
