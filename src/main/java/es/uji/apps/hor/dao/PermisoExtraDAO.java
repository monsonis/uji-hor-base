package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Centro;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface PermisoExtraDAO extends BaseDAO
{
    List<Centro> getPermisos(List<Long> listaEstudiosIds);
}
