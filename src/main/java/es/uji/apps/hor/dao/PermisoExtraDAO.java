package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.PermisoExtra;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface PermisoExtraDAO extends BaseDAO
{
    List<PermisoExtra> getPermisosExtra();
    List<PermisoExtra> getPermisosExtraByPersonaId(Long userId);
    PermisoExtra addPermisoExtra(Long estudioId, Long personaId, Long tipoCargoId,
            Long connectedUserId);
    PermisoExtra getPermisoExtraById(Long permisoExtraId) throws RegistroNoEncontradoException;
}
