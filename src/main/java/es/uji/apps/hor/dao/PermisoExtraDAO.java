package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.PermisoExtra;
import es.uji.commons.db.BaseDAO;

public interface PermisoExtraDAO extends BaseDAO
{
    List<PermisoExtra> getPermisosExtra();
    List<PermisoExtra> getPermisosExtraByPersonaId(Long userId);
}
