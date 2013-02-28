package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CircuitoDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Circuito;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.auth.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class CircuitoService
{
    @Autowired
    private CircuitoDAO circuitoDAO;

    @Autowired
    private PersonaDAO personaDAO;

    @Role({ "ADMIN", "USUARIO" })
    public List<Circuito> getCircuitosByEstudioIdAndSemestreIdAndGrupoId(Long estudioId, Long semestreId, String grupoId, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return circuitoDAO.getCircuitosByEstudioIdAndSemestreIdAndGrupoId(estudioId, semestreId, grupoId);
    }
}
