package es.uji.apps.hor.dao;

import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.commons.db.BaseDAO;

public interface DiaSemanaDAO extends BaseDAO
{
    DiaSemanaDTO getDiaSemanaByNombre(String nombre);
}
