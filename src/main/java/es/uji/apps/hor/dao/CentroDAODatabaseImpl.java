package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.QCentroDTO;
import es.uji.apps.hor.model.Centro;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class CentroDAODatabaseImpl extends BaseDAODatabaseImpl implements CentroDAO
{
    @Override
    public List<Centro> getCentros()
    {
        JPAQuery query = new JPAQuery(entityManager);

        QCentroDTO qCentro = QCentroDTO.centroDTO;

        query.from(qCentro);

        List<Centro> listaCentros = new ArrayList<Centro>();

        for (CentroDTO centroDTO : query.list(qCentro))
        {
            listaCentros.add(creaCentroDesdeCentroDTO(centroDTO));
        }

        return listaCentros;
    }

    private Centro creaCentroDesdeCentroDTO(CentroDTO centroDTO)
    {
        Centro centro = new Centro(centroDTO.getId(), centroDTO.getNombre());
        return centro;
    }

    @Override
    @Transactional
    public Centro insertCentro(Centro centro)
    {
        // Creamos un nuevo centro
        CentroDTO centroDTO = new CentroDTO();
        centroDTO.setNombre(centro.getNombre());
        centroDTO = insert(centroDTO);

        return this.creaCentroDesdeCentroDTO(centroDTO);
    }

}