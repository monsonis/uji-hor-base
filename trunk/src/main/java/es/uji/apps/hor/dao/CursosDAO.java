package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Curso;
import es.uji.commons.db.BaseDAO;

public interface CursosDAO extends BaseDAO
{
    List<Curso> getCursos(Long estudioId);
}
