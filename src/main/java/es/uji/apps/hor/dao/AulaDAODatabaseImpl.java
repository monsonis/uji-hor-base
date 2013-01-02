package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.db.QAulaPlanificacionDTO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Centro;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

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

        for (AulaDTO aulaDTO : query.list(qAula))
        {
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

        for (AulaDTO aulaDTO : query.list(qAula))
        {
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

        query.from(qAula, qAulaPlanificacion)
                .join(qAulaPlanificacion.aula, qAula)
                .where(qAula.centro.id.eq(centroId)
                        .and(qAulaPlanificacion.estudio.id.eq(estudioId)));

        List<Aula> listaAulas = new ArrayList<Aula>();

        for (AulaDTO aulaDTO : query.list(qAula))
        {
            listaAulas.add(creaAulaDesdeAulaDTO(aulaDTO));
        }

        return listaAulas;
    }

    @Override
    public List<AulaPlanificacion> getAulasAsignadasToEstudio(Long estudioId, Long semestreId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        query.from(qAulaPlanificacion)
                .join(qAulaPlanificacion.aula, qAula)
                .where(qAulaPlanificacion.estudio.id.eq(estudioId).and(
                        qAulaPlanificacion.semestreId.eq(semestreId)));

        List<AulaPlanificacion> listaAulasAsignadas = new ArrayList<AulaPlanificacion>();

        for (AulaPlanificacionDTO aulaPlanificacionDTO : query.list(qAulaPlanificacion))
        {
            listaAulasAsignadas.add(creaAulaPlanificacionDesde(aulaPlanificacionDTO));
        }

        return listaAulasAsignadas;
    }

    @Override
    public AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId)
            throws RegistroNoEncontradoException, AulaYaAsignadaAEstudioException
    {
        AulaDTO aula;
        EstudioDTO estudio;

        try
        {
            aula = get(AulaDTO.class, aulaId).get(0);
            estudio = get(EstudioDTO.class, estudioId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }

        JPAQuery query = new JPAQuery(entityManager);
        QAulaPlanificacionDTO qAulaPlan = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        query.from(qAulaPlan).where(
                qAulaPlan.estudio.id.eq(estudioId).and(qAulaPlan.aula.id.eq(aulaId))
                        .and(qAulaPlan.semestreId.eq(semestreId)));

        List<AulaPlanificacionDTO> aulasPlan = query.list(qAulaPlan);

        if (aulasPlan.size() > 0)
        {
            throw new AulaYaAsignadaAEstudioException();
        }

        AulaPlanificacionDTO aulaPlan = new AulaPlanificacionDTO();
        aulaPlan.setNombre(aula.getNombre());
        aulaPlan.setAula(aula);
        aulaPlan.setEstudio(estudio);
        aulaPlan.setSemestreId(semestreId);

        aulaPlan = insert(aulaPlan);

        return creaAulaPlanificacionDesde(aulaPlan);
    }

    private AulaPlanificacion creaAulaPlanificacionDesde(AulaPlanificacionDTO aulaPlanificacionDTO)
    {
        AulaPlanificacion aulaPlanificacion = new AulaPlanificacion();
        aulaPlanificacion.setId(aulaPlanificacionDTO.getId());
        aulaPlanificacion.setNombre(aulaPlanificacionDTO.getNombre());
        aulaPlanificacion.setEstudioId(aulaPlanificacionDTO.getEstudio().getId());
        aulaPlanificacion.setSemestreId(aulaPlanificacionDTO.getSemestreId());
        aulaPlanificacion.setEdificio(aulaPlanificacionDTO.getAula().getEdificio());
        aulaPlanificacion.setTipo(aulaPlanificacionDTO.getAula().getTipo());
        aulaPlanificacion.setPlanta(aulaPlanificacionDTO.getAula().getPlanta());

        return aulaPlanificacion;
    }

    @Override
    public void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId)
            throws RegistroConHijosException
    {
        try
        {
            delete(AulaPlanificacionDTO.class, aulaPlanificacionId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new RegistroConHijosException(
                    "No es pot borrar l'aula perque té classes assignades");
        }
    }

    @Override
    public AulaPlanificacion getAulaById(Long aulaId) throws RegistroNoEncontradoException
    {
        try
        {
            return get(AulaPlanificacion.class, aulaId).get(0);
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }
    }

}