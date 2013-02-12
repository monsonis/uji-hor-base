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
        Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
        if (!personaDAO.esAdmin(connectedUserId) && !persona.esGestorDeCentro())
        {
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return aulaDAO.getAulasAsignadasToEstudio(estudioId, semestreId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId,
            Long connectedUserId) throws RegistroNoEncontradoException,
            AulaYaAsignadaAEstudioException, UnauthorizedUserException
    {
        Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
        if (personaDAO.esAdmin(connectedUserId) || persona.esGestorDeCentro())
        {
            return aulaDAO.asignaAulaToEstudio(estudioId, aulaId, semestreId);
        }
        else
        {
            throw new UnauthorizedUserException();
        }

    }

    @Role({ "ADMIN", "USUARIO" })
    public void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId, Long connectedUserId)
            throws RegistroConHijosException, UnauthorizedUserException,
            RegistroNoEncontradoException
    {

        if (personaDAO.esAdmin(connectedUserId))
        {
            AulaPlanificacion aulaPlanificacion = aulaDAO
                    .getAulaPlanificacionById(aulaPlanificacionId);
            Aula aula = aulaDAO.getAulaById(aulaPlanificacion.getAula().getId());
            if (aula.sePuedeDesplanificar())
            {
                aulaDAO.deleteAulaAsignadaToEstudio(aulaPlanificacionId);
            }
            else
            {
                throw new RegistroConHijosException();
            }
        }
        else
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);

            AulaPlanificacion aulaPlanificacion = aulaDAO
                    .getAulaPlanificacionById(aulaPlanificacionId);
            Centro centro = aulaPlanificacion.getAula().getCentro();

            if (persona.esGestorDeCentro(centro.getId()))
            {
                Aula aula = aulaDAO.getAulaConEventosById(aulaPlanificacion.getAula().getId());
                if (aula.sePuedeDesplanificar())
                {
                    aulaDAO.deleteAulaAsignadaToEstudio(aulaPlanificacionId);
                }
                else
                {
                    throw new RegistroConHijosException();
                }
            }
            else
            {
                throw new UnauthorizedUserException();
            }
        }
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
