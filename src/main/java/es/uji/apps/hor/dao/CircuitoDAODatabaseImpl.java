package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.CircuitoDTO;
import es.uji.apps.hor.db.QCircuitoDTO;
import es.uji.apps.hor.db.QCircuitoEstudioDTO;
import es.uji.apps.hor.model.Circuito;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Semestre;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroConHijosException;

@Repository
public class CircuitoDAODatabaseImpl extends BaseDAODatabaseImpl implements CircuitoDAO
{

    @Override
    public List<Circuito> getCircuitosByEstudioIdAndSemestreIdAndGrupoId(Long estudioId, Long semestreId, String grupoId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QCircuitoDTO qCircuito = QCircuitoDTO.circuitoDTO;
        QCircuitoEstudioDTO qCircuitoEstudio = QCircuitoEstudioDTO.circuitoEstudioDTO;
        

        query.from(qCircuito).innerJoin(qCircuito.circuitosEstudios, qCircuitoEstudio).where(qCircuitoEstudio.estudio.id.eq(estudioId).and(qCircuito.semestre.id.eq(semestreId).and(qCircuito.grupoId.eq(grupoId))));
        
        List<Circuito> listaCircuitos = new ArrayList<Circuito>();
        
        for (CircuitoDTO circuitoDTO: query.list(qCircuito)) {
            listaCircuitos.add(creaCircuitoDesdeCircuitoDTO(circuitoDTO, estudioId));
        }
        
        return listaCircuitos;
    }
    
    private Circuito creaCircuitoDesdeCircuitoDTO(CircuitoDTO circuitoDTO, Long estudioId)
    {
        Circuito circuito = new Circuito();
        circuito.setId(circuitoDTO.getId());
        circuito.setNombre(circuitoDTO.getNombre());
        circuito.setGrupo(circuitoDTO.getGrupoId());
        
        Semestre semestre = new Semestre();
        semestre.setSemestre(circuitoDTO.getSemestre().getId());
        circuito.setSemestre(semestre);
        
        Estudio estudio = new Estudio();
        estudio.setId(estudioId);
        circuito.setEstudio(estudio);

        return circuito;
    }

    @Override
    @Transactional
    public void deleteCircuitoById(Long circuitoId) throws RegistroConHijosException
    {
        try
        {
            delete(Circuito.class, circuitoId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new RegistroConHijosException(
                    "No es pot borrar el circuit perque té events assignats");
        }
    }

    @Override
    public Circuito addCircuito(Circuito circuito)
    {
        // TODO Auto-generated method stub
        return null;
    }

}