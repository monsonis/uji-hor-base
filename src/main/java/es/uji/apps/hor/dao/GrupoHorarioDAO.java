package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.GrupoHorario;
import es.uji.commons.db.BaseDAO;

public interface GrupoHorarioDAO extends BaseDAO
{
    GrupoHorario getGrupoHorarioById(Long estudioId, Long cursoId, Long semestreId, String grupoId);
}
