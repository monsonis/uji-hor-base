package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class AulaService
{
    @Autowired
    private PersonaDAO personaDAO;

    private final AulaDAO aulaDAO;

    @Autowired
    public AulaService(AulaDAO aulaDAO)
    {
        this.aulaDAO = aulaDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<AulaPlanificacion> getAulasAsignadasToEstudio(Long estudioId, Long semestreId,
            Long connectedUserId) throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            if (persona.getCentroAutorizado() != null)
            {
                persona.compruebaAccesoAEstudioDesdeCentro(estudioId);
            }
            else
            {
                persona.compruebaAccesoAEstudio(estudioId);
            }
        }

        return aulaDAO.getAulasAsignadasToEstudio(estudioId, semestreId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId,
            Long connectedUserId) throws RegistroNoEncontradoException,
            AulaYaAsignadaAEstudioException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return aulaDAO.asignaAulaToEstudio(estudioId, aulaId, semestreId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId, Long connectedUserId)
            throws RegistroConHijosException, UnauthorizedUserException,
            RegistroNoEncontradoException
    {
        AulaPlanificacion aula = aulaDAO.getAulaById(aulaPlanificacionId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(aula.getEstudioId());
        }

        aulaDAO.deleteAulaAsignadaToEstudio(aulaPlanificacionId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<TipoAula> getTiposAulaByCentroAndEdificio(Long centroId, String edificio,
            Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            return aulaDAO.getTiposAulaVisiblesPorUsuarioByCentroAndEdificio(centroId, edificio,
                    connectedUserId);
        }

        return aulaDAO.getTiposAulaByCentroAndEdificio(centroId, edificio);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Aula> getAulasFiltradasPor(Long centroId, String edificio, String tipoAula,
            String planta, Long connectedUserId) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            return aulaDAO.getAulasVisiblesPorUsuarioFiltradasPor(centroId, edificio, tipoAula,
                    planta, connectedUserId);
        }

        return aulaDAO.getAulasFiltradasPor(centroId, edificio, tipoAula, planta);
    }

}
