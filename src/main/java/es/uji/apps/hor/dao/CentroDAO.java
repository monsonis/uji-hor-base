package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Centro;
import es.uji.commons.db.BaseDAO;

public interface CentroDAO extends BaseDAO
{
    List<Centro> getCentros();
    Centro getCentroById(Long centroId);
    Centro insertCentro(Centro centro);
}
