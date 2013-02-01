package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.QTuple;

import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class CalendariosDAODatabaseImpl extends BaseDAODatabaseImpl implements CalendariosDAO
{
    @Override
    public List<Calendario> getCalendarios()
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<Tuple> listaSubgruposTuples = query.from(item).listDistinct(
                new QTuple(item.tipoSubgrupoId, item.tipoSubgrupo));

        List<Calendario> calendarios = new ArrayList<Calendario>();

        for (Tuple tuple : listaSubgruposTuples)
        {
            calendarios.add(creaCalendarioDesde(tuple, item));
        }

        return calendarios;
    }

    private Calendario creaCalendarioDesde(Tuple tuple, QItemDTO item)
    {
        return new Calendario(TipoSubgrupo.valueOf(tuple.get(item.tipoSubgrupoId))
                .getCalendarioAsociado(), tuple.get(item.tipoSubgrupo));
    }

}
