package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.CircuitoDTO;
import es.uji.apps.hor.db.CircuitoEstudioDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.QCircuitoDTO;
import es.uji.apps.hor.db.QCircuitoEstudioDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.model.Circuito;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Semestre;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class CircuitoDAODatabaseImpl extends BaseDAODatabaseImpl implements CircuitoDAO
{

    @Override
    public List<Circuito> getCircuitosByEstudioIdAndSemestreIdAndGrupoId(Long estudioId,
            Long semestreId, String grupoId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QCircuitoDTO qCircuito = QCircuitoDTO.circuitoDTO;
        QCircuitoEstudioDTO qCircuitoEstudio = QCircuitoEstudioDTO.circuitoEstudioDTO;

        query.from(qCircuito)
                .innerJoin(qCircuito.circuitosEstudios, qCircuitoEstudio)
                .where(qCircuitoEstudio.estudio.id.eq(estudioId).and(
                        qCircuito.semestre.id.eq(semestreId).and(qCircuito.grupoId.eq(grupoId))));

        List<Circuito> listaCircuitos = new ArrayList<Circuito>();

        for (CircuitoDTO circuitoDTO : query.list(qCircuito))
        {
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

    @Override
    public Circuito getCircuitoById(Long circuitoId, Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QCircuitoDTO qCircuito = QCircuitoDTO.circuitoDTO;
        QCircuitoEstudioDTO qCircuitoEstudio = QCircuitoEstudioDTO.circuitoEstudioDTO;

        query.from(qCircuito).innerJoin(qCircuito.circuitosEstudios, qCircuitoEstudio)
                .where(qCircuitoEstudio.estudio.id.eq(estudioId).and(qCircuito.id.eq(circuitoId)));

        List<CircuitoDTO> listaCircuitos = query.list(qCircuito);

        if (listaCircuitos.size() == 1)
        {
            return creaCircuitoDesdeCircuitoDTO(listaCircuitos.get(0), estudioId);
        }
        else
        {
            return new Circuito();
        }
    }

    @Override
    public Circuito insertNuevoCircuitoEnEstudio(Circuito circuito)
    {
        CircuitoDTO circuitoDTO = new CircuitoDTO();
        circuitoDTO.setId(circuito.getId());
        circuitoDTO.setNombre(circuito.getNombre());
        circuitoDTO.setPlazas(circuito.getPlazas());
        circuitoDTO.setGrupoId(circuito.getGrupo());

        SemestreDTO semestreDTO = new SemestreDTO();
        semestreDTO.setId(circuito.getSemestre().getSemestre());
        circuitoDTO.setSemestre(semestreDTO);

        circuitoDTO = insert(circuitoDTO);

        insertaNuevoCircuitoEstudio(circuito.getId(), circuito.getEstudio().getId());

        return creaCircuitoDesdeCircuitoDTO(circuitoDTO, circuito.getEstudio().getId());
    }

    private void insertaNuevoCircuitoEstudio(Long circuitoId, Long estudioId)
    {
        CircuitoDTO circuitoDTO = new CircuitoDTO();
        circuitoDTO.setId(circuitoId);

        EstudioDTO estudioDTO = new EstudioDTO();
        estudioDTO.setId(estudioId);

        CircuitoEstudioDTO circuitoEstudioDTO = new CircuitoEstudioDTO();
        circuitoEstudioDTO.setCircuito(circuitoDTO);
        circuitoEstudioDTO.setEstudio(estudioDTO);
        insert(circuitoEstudioDTO);
    }

    @Override
    @Transactional
    public Circuito updateCircuito(Long circuitoId, Long estudioId, String nombre, Long plazas) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);
        QCircuitoDTO qCircuito = QCircuitoDTO.circuitoDTO;
        QCircuitoEstudioDTO qCircuitoEstudio = QCircuitoEstudioDTO.circuitoEstudioDTO;

        query.from(qCircuito).where(qCircuito.id.eq(circuitoId));

        List<CircuitoDTO> listaCircuitos = query.list(qCircuito);

        if (listaCircuitos.size() == 1)
        {
            CircuitoDTO circuitoDTO = listaCircuitos.get(0);
            circuitoDTO.setPlazas(plazas);
            circuitoDTO.setNombre(nombre);
            
            update(circuitoDTO);
            return creaCircuitoDesdeCircuitoDTO(circuitoDTO, estudioId);
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }
}