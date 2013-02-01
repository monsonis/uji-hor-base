package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Grupo;
import es.uji.commons.db.BaseDAO;

public interface GruposDAO extends BaseDAO
{
    List<Grupo> getGrupos(Long semestreId, Long cursoId, Long estudioId);
}
