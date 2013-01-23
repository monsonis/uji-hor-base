package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CursosDAO;
import es.uji.apps.hor.dao.UsuarioDAO;
import es.uji.apps.hor.model.Curso;
import es.uji.apps.hor.model.Usuario;
import es.uji.commons.rest.Role;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class CursosService
{
    @Autowired
    private UsuarioDAO usuarioDAO;

    private final CursosDAO cursosDAO;

    @Autowired
    public CursosService(CursosDAO cursosDAO)
    {
        this.cursosDAO = cursosDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Curso> getCursos(Long estudioId, Long connectedUserId)
            throws UnauthorizedUserException
    {
        Usuario usuario = usuarioDAO.getUsuarioById(connectedUserId);
        usuario.compruebaAccesoAEstudio(estudioId);

        return cursosDAO.getCursos(estudioId);
    }
}
