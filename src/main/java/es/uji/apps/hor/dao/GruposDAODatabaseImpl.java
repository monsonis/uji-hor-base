package es.uji.apps.hor.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.QTuple;

import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.model.Grupo;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class GruposDAODatabaseImpl extends BaseDAODatabaseImpl implements GruposDAO
{
    @Override
    public List<Grupo> getGrupos(Long semestreId, Long cursoId, Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<Tuple> listaGruposTuples = query
                .from(item)
                .where(item.estudio.id.eq(estudioId).and(
                        item.cursoId.eq(new BigDecimal(cursoId)).and(
                                item.semestre.id.eq(semestreId)))).orderBy(item.grupoId.asc())
                .listDistinct(new QTuple(item.grupoId));

        List<Grupo> grupos = new ArrayList<Grupo>();

        for (Tuple tuple : listaGruposTuples)
        {
            grupos.add(new Grupo(tuple.get(item.grupoId)));
        }

        return grupos;
    }
}