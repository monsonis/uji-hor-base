package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.db.BaseDAO;

public interface TipoAulaDAO extends BaseDAO
{
    List<TipoAula> getTiposAulaByCentroAndEdificio(Long centroId, String edificio);
}
