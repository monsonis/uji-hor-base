package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.QTuple;

import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.QItemsAsignaturaDTO;
import es.uji.apps.hor.model.Semestre;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class SemestresDAODatabaseImpl extends BaseDAODatabaseImpl implements SemestresDAO
{
    @Override
    public List<Semestre> getSemestres(Long curso, Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;
        QItemsAsignaturaDTO asignatura = QItemsAsignaturaDTO.itemsAsignaturaDTO;

        List<Tuple> listaSemestresTuples = query.from(asignatura).join(asignatura.item)
                .where(asignatura.estudioId.eq(estudioId).and(item.cursoId.eq(curso)))
                .orderBy(item.semestre.id.asc()).listDistinct(new QTuple(item.semestre.id));

        List<Semestre> semestres = new ArrayList<Semestre>();

        for (Tuple tuple : listaSemestresTuples)
        {
            Semestre semestre = new Semestre(tuple.get(item.semestre.id));
            semestre.setNombre(tuple.get(item.semestre.nombre));
            semestres.add(semestre);
        }

        return semestres;
    }
}