package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.QTuple;

import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.model.Estudio;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class EstudiosDAODatabaseImpl extends BaseDAODatabaseImpl implements EstudiosDAO
{
    @Override
    public List<Estudio> getEstudios()
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<Tuple> listaEstudiosTuples = query.from(item).orderBy(item.estudio.asc())
                .listDistinct(new QTuple(item.estudio, item.horEstudio.id));

        List<Estudio> estudios = new ArrayList<Estudio>();

        for (Tuple tuple : listaEstudiosTuples)
        {
            estudios.add(creaEstudioDesde(tuple, item));
        }

        return estudios;
    }

    private Estudio creaEstudioDesde(Tuple tuple, QItemDTO item)
    {
        Estudio estudio = new Estudio(tuple.get(item.horEstudio.id), tuple.get(item.estudio));

        return estudio;
    }
}