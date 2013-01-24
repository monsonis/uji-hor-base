package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.GrupoAsignaturaDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class GruposAsignaturasService
{
    @Autowired
    private PersonaDAO personaDAO;

    private final GrupoAsignaturaDAO grupoAsignaturaDAO;

    @Autowired
    public GruposAsignaturasService(GrupoAsignaturaDAO grupoAsignaturaDAO)
    {
        this.grupoAsignaturaDAO = grupoAsignaturaDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<GrupoAsignatura> getGruposAsignaturasSinAsignar(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds, Long connectedUserId)
            throws UnauthorizedUserException
    {
        Persona persona = personaDAO.getPersonaById(connectedUserId);
        persona.compruebaAccesoAEstudio(estudioId);

        return grupoAsignaturaDAO.getGruposAsignaturasSinAsignar(estudioId, cursoId, semestreId,
                grupoId, calendariosIds);
    }

    @Role({ "ADMIN", "USUARIO" })
    public GrupoAsignatura planificaGrupoAsignaturaSinAsignar(Long grupoAsignaturaId,
            Long estudioId, Long connectedUserId) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        Persona persona = personaDAO.getPersonaById(connectedUserId);
        persona.compruebaAccesoAEstudio(estudioId);

        GrupoAsignatura grupoAsignatura = grupoAsignaturaDAO.getGrupoAsignaturaById(
                grupoAsignaturaId, estudioId);
        grupoAsignatura.planificaGrupoAsignaturaSinAsignar();
        grupoAsignaturaDAO.updateGrupoAsignaturaPlanificado(grupoAsignatura);

        return grupoAsignatura;
    }
}
