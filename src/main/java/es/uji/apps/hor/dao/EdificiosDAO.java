package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Edificio;
import es.uji.commons.db.BaseDAO;

public interface EdificiosDAO extends BaseDAO
{
    public List<Edificio> getEdificiosByCentroId(Long centroId);
}
