package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.UsuarioDAO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.TipoAula;
import es.uji.apps.hor.model.Usuario;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class AulaService
{
    @Autowired
    private UsuarioDAO usuarioDAO;

    private final AulaDAO aulaDAO;

    @Autowired
    public AulaService(AulaDAO aulaDAO)
    {
        this.aulaDAO = aulaDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<AulaPlanificacion> getAulasAsignadasToEstudio(Long estudioId, Long semestreId,
            Long connectedUserId) throws UnauthorizedUserException
    {
        if (!usuarioDAO.elUsuarioEsAdmin(connectedUserId))
        {
            Usuario usuario = usuarioDAO.getUsuarioById(connectedUserId);
            usuario.compruebaAccesoAEstudio(estudioId);
        }

        return aulaDAO.getAulasAsignadasToEstudio(estudioId, semestreId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId,
            Long connectedUserId) throws RegistroNoEncontradoException,
            AulaYaAsignadaAEstudioException, UnauthorizedUserException
    {
        if (!usuarioDAO.elUsuarioEsAdmin(connectedUserId))
        {
            Usuario usuario = usuarioDAO.getUsuarioById(connectedUserId);
            usuario.compruebaAccesoAEstudio(estudioId);
        }

        return aulaDAO.asignaAulaToEstudio(estudioId, aulaId, semestreId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId, Long connectedUserId)
            throws RegistroConHijosException, UnauthorizedUserException,
            RegistroNoEncontradoException
    {
        AulaPlanificacion aula = aulaDAO.getAulaById(aulaPlanificacionId);

        if (!usuarioDAO.elUsuarioEsAdmin(connectedUserId))
        {
            Usuario usuario = usuarioDAO.getUsuarioById(connectedUserId);
            usuario.compruebaAccesoAEstudio(aula.getEstudioId());
        }

        aulaDAO.deleteAulaAsignadaToEstudio(aulaPlanificacionId);
    }

    public List<TipoAula> getTiposAulaByCentroAndEdificio(Long centroId, String edificio)
    {
        // Falta control acceso
        return aulaDAO.getTiposAulaByCentroAndEdificio(centroId, edificio);
    }
    
    public List<Aula> getAulasFiltradasPor(Long centroId, String edificio, String tipoAula,
            String planta)
    {
        // Falta control acceso
        return aulaDAO.getAulasFiltradasPor(centroId, edificio, tipoAula, planta);
    }

}
