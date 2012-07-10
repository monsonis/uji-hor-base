package es.uji.apps.hor.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.QTuple;

import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.model.Curso;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class CursosDAODatabaseImpl extends BaseDAODatabaseImpl implements CursosDAO
{
    @Override
    public List<Curso> getCursos(Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<Tuple> listaCursosTuples = query.from(item).where(item.estudio.id.eq(estudioId))
                .orderBy(item.cursoId.asc()).listDistinct(new QTuple(item.cursoId));

        List<Curso> cursos = new ArrayList<Curso>();

        for (Tuple tuple : listaCursosTuples)
        {
            BigDecimal idCurso = tuple.get(item.cursoId);

            cursos.add(new Curso(idCurso.longValue()));
        }

        return cursos;
    }
}