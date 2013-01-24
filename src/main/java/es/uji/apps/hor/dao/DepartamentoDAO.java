package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.Departamento;
import es.uji.commons.db.BaseDAO;

public interface DepartamentoDAO extends BaseDAO
{
    Departamento insertDepartamento(Departamento departamento);
}
