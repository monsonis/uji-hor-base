package es.uji.apps.hor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.QDiaSemanaDTO;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class DiaSemanaDAODatabaseImpl extends BaseDAODatabaseImpl implements DiaSemanaDAO
{

    @Override
    public DiaSemanaDTO getDiaSemanaByNombre(String nombre)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QDiaSemanaDTO qDiaSemana= QDiaSemanaDTO.diaSemanaDTO;
        
        query.from(qDiaSemana).where(qDiaSemana.nombre.eq(nombre));
        
        List<DiaSemanaDTO> result = query.list(qDiaSemana);
        
        if (result.size() == 1) {
            return result.get(0);
        } else {
            return new DiaSemanaDTO();
        }
        
    }
}
