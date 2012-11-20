package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.db.QAulaPlanificacionDTO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.Centro;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class AulaDAODatabaseImpl extends BaseDAODatabaseImpl implements AulaDAO
{
    @Override
    public List<Aula> getAulas()
    {
        JPAQuery query = new JPAQuery(entityManager);

        QAulaDTO qAula = QAulaDTO.aulaDTO;

        query.from(qAula);

        List<Aula> listaAulas = new ArrayList<Aula>();
        
        for (AulaDTO aulaDTO: query.list(qAula)) {
            listaAulas.add(creaAulaDesdeAulaDTO(aulaDTO));
        }
        
        return listaAulas;
    }

    private Aula creaAulaDesdeAulaDTO(AulaDTO aulaDTO)
    {
        Aula aula = new Aula(aulaDTO.getId());
        Centro centro = new Centro(aulaDTO.getCentro().getId(), aulaDTO.getCentro().getNombre());
        
        aula.setNombre(aulaDTO.getNombre());
        aula.setCentro(centro);
        aula.setTipo(aulaDTO.getTipo());
        aula.setPlanta(aulaDTO.getPlanta());
        aula.setCodigo(aulaDTO.getCodigo());
        aula.setArea(aulaDTO.getArea());
        aula.setEdificio(aulaDTO.getEdificio());
        aula.setPlazas(aulaDTO.getPlazas());
        return aula;
    }

    @Override
    public List<Aula> getAulasByCentroId(Long centroId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QAulaDTO qAula = QAulaDTO.aulaDTO;

        query.from(qAula).where(qAula.centro.id.eq(centroId));

        List<Aula> listaAulas = new ArrayList<Aula>();
        
        for (AulaDTO aulaDTO: query.list(qAula)) {
            listaAulas.add(creaAulaDesdeAulaDTO(aulaDTO));
        }
        
        return listaAulas;        
    }

    @Override
    public List<Aula> getAulasByCentroIdAndestudioId(Long centroId, Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QAulaDTO qAula = QAulaDTO.aulaDTO;
        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        query.from(qAula, qAulaPlanificacion).join(qAulaPlanificacion.aula, qAula).where(qAula.centro.id.eq(centroId).and(qAulaPlanificacion.estudioId.eq(estudioId)));

        List<Aula> listaAulas = new ArrayList<Aula>();
        
        for (AulaDTO aulaDTO: query.list(qAula)) {
            listaAulas.add(creaAulaDesdeAulaDTO(aulaDTO));
        }
        
        return listaAulas;        
    }

}