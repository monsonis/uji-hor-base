package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.UsuarioDAO;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Usuario;
import es.uji.commons.rest.Role;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class EstudiosService
{
    @Autowired
    private UsuarioDAO usuarioDAO;

    private final EstudiosDAO estudiosDAO;

    @Autowired
    public EstudiosService(EstudiosDAO estudiosDAO)
    {
        this.estudiosDAO = estudiosDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Estudio> getEstudios(Long connectedUserId)
    {
        return estudiosDAO.getEstudios();
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Estudio> getEstudiosByCentroId(Long centroId, Long connectedUserId)
            throws UnauthorizedUserException
    {
        Usuario usuario = usuarioDAO.getUsuarioById(connectedUserId);
        usuario.compruebaAccesoACentro(centroId);
        return estudiosDAO.getEstudiosByCentroId(centroId);
    }

}
