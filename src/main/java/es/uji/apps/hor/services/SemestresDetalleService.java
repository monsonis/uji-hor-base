package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.dao.SemestresDetalleDAO;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.commons.rest.Role;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class SemestresDetalleService
{

    private final SemestresDetalleDAO semestresDetalleDAO;

    @Autowired
    private PersonaDAO personaDAO;

    @Autowired
    public SemestresDetalleService(SemestresDetalleDAO semestresDetalleDAO)
    {
        this.semestresDetalleDAO = semestresDetalleDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<SemestreDetalle> getSemestresDetallesTodos(Long connectedUserId)
    {
        return semestresDetalleDAO.getSemestresDetalleTodos();
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<SemestreDetalle> getSemestresDetallesPorEstudioIdYSemestreId(Long estudioId,
            Long semestreId, Long connectedUserId) throws UnauthorizedUserException
    {
        Persona persona = personaDAO.getPersonaById(connectedUserId);
        persona.compruebaAccesoAEstudio(estudioId);

        return semestresDetalleDAO.getSemestresDetallesPorEstudioIdYSemestreId(estudioId,
                semestreId);
    }

}
