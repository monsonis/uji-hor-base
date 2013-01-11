package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.db.BaseDAO;

public interface TipoEstudioDAO extends BaseDAO
{

    TipoEstudio insert(TipoEstudio tipoEstudio);
}
