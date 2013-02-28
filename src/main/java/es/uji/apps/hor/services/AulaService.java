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
import es.uji.commons.rest.auth.Role;
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
            AulaPlanificacion nuevaPlanificacion = aulaDAO.asignaAulaToEstudio(estudioId, aulaId,
                    semestreId);

            List<Estudio> listaEstudiosCompartidos = aulaDAO
                    .getEstudiosComunesByEstudioId(estudioId);

            for (Estudio estudio : listaEstudiosCompartidos)
            {
                aulaDAO.asignaAulaToEstudio(estudio.getId(), aulaId, semestreId);
            }
            return nuevaPlanificacion;
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
        AulaPlanificacion aulaPlanificacion = aulaDAO.getAulaPlanificacionById(aulaPlanificacionId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);

            Centro centro = aulaPlanificacion.getAula().getCentro();

            if (!persona.esGestorDeCentro(centro.getId()))
            {
                throw new UnauthorizedUserException();
            }
        }

        Aula aula = aulaDAO.getAulaConEventosById(aulaPlanificacion.getAula().getId());
        List<Estudio> listaEstudiosCompartidos = aulaDAO
                .getEstudiosComunesByEstudioId(aulaPlanificacion.getEstudio().getId());

        if (sePuedeDesplanificarAulaDeTodosLosEstudios(aula, aulaPlanificacion.getEstudio(), listaEstudiosCompartidos))
        {
            aulaDAO.deleteAulaAsignadaToEstudio(aulaPlanificacionId);
            for (Estudio estudio : listaEstudiosCompartidos)
            {
                AulaPlanificacion aulaPlanificacionEstudioCompartido = aulaDAO
                        .getAulaPlanificacionByAulaEstudioSemestre(aulaPlanificacion.getAula()
                                .getId(), estudio.getId(), aulaPlanificacion.getSemestre()
                                .getSemestre());
                aulaDAO.deleteAulaAsignadaToEstudio(aulaPlanificacionEstudioCompartido.getId());
            }
        }
        else
        {
            throw new RegistroConHijosException();
        }
    }

    private Boolean sePuedeDesplanificarAulaDeTodosLosEstudios(Aula aula, Estudio estudio,
            List<Estudio> listaEstudiosCompartidos)
    {

        Boolean sePuedeDesplanificar = true;
        if (!aula.sePuedeDesplanificar(estudio.getId())) {
            sePuedeDesplanificar = false;
        }
        
        for (Estudio estudioCompartido : listaEstudiosCompartidos)
        {
            if (!aula.sePuedeDesplanificar(estudioCompartido.getId()))
            {
                sePuedeDesplanificar = false;
            }
        }

        return sePuedeDesplanificar;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<TipoAula> getTiposAulaByCentroAndSemestreAndEdificio(Long centroId,
            Long semestreId, String edificio, Long connectedUserId)
            throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            return aulaDAO.getTiposAulaVisiblesPorUsuarioByCentroAndSemestreAndEdificio(centroId,
                    semestreId, edificio, connectedUserId);
        }

        return aulaDAO.getTiposAulaByCentroAndEdificio(centroId, edificio);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Aula> getAulasFiltradasPor(Long centroId, Long semestreId, String edificio,
            String tipoAula, String planta, Long connectedUserId)
            throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            return aulaDAO.getAulasVisiblesPorUsuarioFiltradasPor(centroId, semestreId, edificio,
                    tipoAula, planta, connectedUserId);
        }

        return aulaDAO.getAulasFiltradasPor(centroId, edificio, tipoAula, planta);
    }

}
