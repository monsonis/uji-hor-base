package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class TipoAulaDatabaseImpl extends BaseDAODatabaseImpl implements TipoAulaDAO
{
    @Override
    public List<TipoAula> getTiposAulaByCentroAndEdificio(Long centroId, String edificio)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        List<String> tiposAula = query.from(qAula)
                .where(qAula.centro.id.eq(centroId).and(qAula.edificio.eq(edificio)))
                .orderBy(qAula.tipo.asc()).distinct().list(qAula.tipo);

        return creaListaTiposAulaDesde(tiposAula);
    }

    private List<TipoAula> creaListaTiposAulaDesde(List<String> tiposAulaStr)
    {
        List<TipoAula> tiposAula = new ArrayList<TipoAula>();

        for (String tipoAulaStr : tiposAulaStr)
        {
            TipoAula tipoAula = new TipoAula();
            tipoAula.setNombre(tipoAulaStr);
            tiposAula.add(tipoAula);
        }

        return tiposAula;
    }
}
