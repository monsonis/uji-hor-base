package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EdificiosDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.commons.rest.auth.Role;
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
    public List<Edificio> getEdificiosByCentroId(Long centroId, Long semestreId,
            Long connectedUserId) throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            return edificiosDAO.getEdificiosVisiblesPorUsuarioByCentroId(centroId, semestreId,
                    connectedUserId);
        }

        return edificiosDAO.getEdificiosByCentroId(centroId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<PlantaEdificio> getPlantasEdificioByCentroAndSemestreAndEdificio(Long centroId,
            Long semestreId, String edificio, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            return edificiosDAO.getPlantasEdificioVisiblesPorUsuarioByCentroAndSemestreAndEdificio(
                    centroId, semestreId, edificio, connectedUserId);
        }

        return edificiosDAO.getPlantasEdificioByCentroAndEdificio(centroId, edificio);
    }
}
