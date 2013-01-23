package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.Usuario;
import es.uji.commons.db.BaseDAO;

public interface UsuarioDAO extends BaseDAO
{
    Usuario getUsuarioById(Long usuarioId);

}
