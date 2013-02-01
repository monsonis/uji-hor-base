package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Semestre;
import es.uji.commons.db.BaseDAO;

public interface SemestresDAO extends BaseDAO
{
    List<Semestre> getSemestres(Long curso, Long estudioId);
    
    Semestre insert(Semestre semestre);
}
