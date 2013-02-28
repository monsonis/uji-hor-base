package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.CircuitoDTO;
import es.uji.apps.hor.db.QCircuitoDTO;
import es.uji.apps.hor.db.QCircuitoEstudioDTO;
import es.uji.apps.hor.model.Circuito;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class CircuitoDAODatabaseImpl extends BaseDAODatabaseImpl implements CircuitoDAO
{

    @Override
    public List<Circuito> getCircuitosByEstudioId(Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QCircuitoDTO qCircuito = QCircuitoDTO.circuitoDTO;
        QCircuitoEstudioDTO qCircuitoEstudio = QCircuitoEstudioDTO.circuitoEstudioDTO;
        

        query.from(qCircuito).innerJoin(qCircuito.circuitosEstudios, qCircuitoEstudio).where(qCircuitoEstudio.estudio.id.eq(estudioId));
        
        List<Circuito> listaCircuitos = new ArrayList<Circuito>();
        
        for (CircuitoDTO circuitoDTO: query.list(qCircuito)) {
            listaCircuitos.add(creaCircuitoDesdeCircuitoDTO(circuitoDTO));
        }
        
        return listaCircuitos;
    }
    
    private Circuito creaCircuitoDesdeCircuitoDTO(CircuitoDTO circuitoDTO)
    {
        Circuito circuito = new Circuito();
        
        return circuito;
    }

}