package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.SemestresDetalleDAO;
import es.uji.apps.hor.dao.UsuarioDAO;
import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.apps.hor.model.Usuario;
import es.uji.commons.rest.Role;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class SemestresDetalleService
{

    private final SemestresDetalleDAO semestresDetalleDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

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
        Usuario usuario = usuarioDAO.getUsuarioById(connectedUserId);
        usuario.compruebaAccesoAEstudio(estudioId);

        return semestresDetalleDAO.getSemestresDetallesPorEstudioIdYSemestreId(estudioId,
                semestreId);
    }

}
