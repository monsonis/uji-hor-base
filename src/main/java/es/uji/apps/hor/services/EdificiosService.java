package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EdificiosDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class EdificiosService
{
    private final EdificiosDAO edificiosDAO;

    @Autowired
    private PersonaDAO personaDAO;

    @Autowired
    public EdificiosService(EdificiosDAO edificiosDAO)
    {
        this.edificiosDAO = edificiosDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Edificio> getEdificiosByCentroId(Long centroId, Long connectedUserId) throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaById(connectedUserId);
            persona.compruebaAccesoACentro(centroId);
        }

        return edificiosDAO.getEdificiosByCentroId(centroId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<PlantaEdificio> getPlantasEdificioByCentroAndEdificio(Long centroId, String edificio, Long connectedUserId) throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaById(connectedUserId);
            persona.compruebaAccesoACentro(centroId);
        }

        return edificiosDAO.getPlantasEdificioByCentroAndEdificio(centroId, edificio);
    }
}
