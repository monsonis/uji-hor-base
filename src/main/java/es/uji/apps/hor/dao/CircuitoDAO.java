package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Circuito;
import es.uji.commons.db.BaseDAO;

public interface CircuitoDAO extends BaseDAO
{

    List<Circuito> getCircuitosByEstudioId(Long estudioId);

}
