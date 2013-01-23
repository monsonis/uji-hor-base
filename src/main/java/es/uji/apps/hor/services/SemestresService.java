package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.SemestresDAO;
import es.uji.apps.hor.dao.UsuarioDAO;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.Usuario;
import es.uji.commons.rest.Role;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class SemestresService
{
    private final SemestresDAO semestresDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    public SemestresService(SemestresDAO semestresDAO)
    {
        this.semestresDAO = semestresDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Semestre> getSemestres(Long cursoId, Long estudioId, Long connectedUserId)
            throws UnauthorizedUserException
    {
        Usuario usuario = usuarioDAO.getUsuarioById(connectedUserId);
        usuario.compruebaAccesoAEstudio(estudioId);

        return semestresDAO.getSemestres(cursoId, estudioId);
    }
}
