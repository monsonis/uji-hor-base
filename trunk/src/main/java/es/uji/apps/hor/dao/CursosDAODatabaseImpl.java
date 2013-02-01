package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.model.Curso;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class CursosDAODatabaseImpl extends BaseDAODatabaseImpl implements CursosDAO
{
    @Override
    public List<Curso> getCursos(Long estudioId)
    {
        EstudioDTO estudio = get(EstudioDTO.class, estudioId).get(0);

        List<Curso> cursos = new ArrayList<Curso>();
        for (long i = 1; i <= estudio.getNumeroCursos(); i++)
        {
            cursos.add(new Curso(i));
        }

        return cursos;
    }
}