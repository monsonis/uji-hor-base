package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.GrupoHorario;
import es.uji.commons.db.BaseDAO;

public interface GrupoHorarioDAO extends BaseDAO
{
    GrupoHorario getGrupoHorarioById(String grupoId);
}
