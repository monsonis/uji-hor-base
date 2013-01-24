package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.db.QAulaPlanificacionDTO;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class AulaDAODatabaseImpl extends BaseDAODatabaseImpl implements AulaDAO
{
    public Aula creaAulaDesdeAulaDTO(AulaDTO aulaDTO)
    {
        Aula aula = new Aula(aulaDTO.getId());

        TipoAula tipoAula = new TipoAula();
        tipoAula.setNombre(aulaDTO.getTipo());
        aula.setTipo(tipoAula);

        PlantaEdificio planta = new PlantaEdificio();
        planta.setNombre(aulaDTO.getPlanta());
        aula.setPlanta(planta);

        AreaEdificio area = new AreaEdificio();
        area.setNombre(aulaDTO.getArea());
        aula.setArea(area);

        Edificio edificio = new Edificio();
        edificio.setNombre(aulaDTO.getEdificio());
        Centro centro = new Centro(aulaDTO.getCentro().getId(), aulaDTO.getCentro().getNombre());
        edificio.setCentro(centro);
        aula.setEdificio(edificio);

        aula.setNombre(aulaDTO.getNombre());
        aula.setCodigo(aulaDTO.getCodigo());
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
        aulaPlanificacion.setCodigo(aulaPlanificacionDTO.getAula().getCodigo());

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
            return creaAulaPlanificacionDesde(get(AulaPlanificacionDTO.class, aulaId).get(0));
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    @Transactional
    public Aula insertAula(Aula aula)
    {
        // Creamos la nueva aula
        AulaDTO aulaDTO = new AulaDTO();

        CentroDTO centroDTO = new CentroDTO();
        centroDTO.setId(aula.getEdificio().getCentro().getId());
        centroDTO.setNombre(aula.getEdificio().getCentro().getNombre());

        aulaDTO.setArea(aula.getArea().getNombre());
        aulaDTO.setCentro(centroDTO);
        aulaDTO.setCodigo(aula.getCodigo());
        aulaDTO.setEdificio(aula.getEdificio().getNombre());
        aulaDTO.setNombre(aula.getNombre());
        aulaDTO.setPlanta(aula.getPlanta().getNombre());
        aulaDTO.setPlazas(aula.getPlazas());
        aulaDTO.setTipo(aula.getTipo().getNombre());
        aulaDTO = insert(aulaDTO);

        return this.creaAulaDesdeAulaDTO(aulaDTO);
    }

    @Override
    @Transactional
    public AulaPlanificacion insertAulaPlanificacion(AulaPlanificacion aulaPlanificacion)
    {
        // Creamos la nueva aula de planificación
        AulaPlanificacionDTO aulaPlanificacionDTO = new AulaPlanificacionDTO();

        EstudioDTO estudioDTO = new EstudioDTO();
        estudioDTO.setId(aulaPlanificacion.getEstudioId());

        AulaDTO aulaDTO = new AulaDTO();
        aulaDTO.setId(aulaPlanificacion.getAulaId());

        aulaPlanificacionDTO.setAula(aulaDTO);
        aulaPlanificacionDTO.setEstudio(estudioDTO);
        aulaPlanificacionDTO.setNombre(aulaPlanificacion.getNombre());
        aulaPlanificacionDTO = insert(aulaPlanificacionDTO);

        return this.creaAulaPlanificacionDesde(aulaPlanificacionDTO);
    }

    @Override
    public List<Aula> getAulasFiltradasPor(Long centroId, String edificio, String tipoAula,
            String planta)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        query.from(qAula).where(qAula.centro.id.eq(centroId).and(qAula.edificio.eq(edificio)));

        if (tipoAula != null)
        {
            query.where(qAula.tipo.eq(tipoAula));
        }

        if (planta != null)
        {
            query.where(qAula.planta.eq(planta));
        }

        List<AulaDTO> listaAulasDTO = query.distinct().orderBy(qAula.codigo.asc()).list(qAula);

        return creaListaAulasFiltradaDesde(listaAulasDTO);
    }

    private List<Aula> creaListaAulasFiltradaDesde(List<AulaDTO> listaAulasDTO)
    {
        List<Aula> listaAulas = new ArrayList<Aula>();

        for (AulaDTO aulaDTO : listaAulasDTO)
        {
            Aula aula = new Aula();
            aula.setId(aulaDTO.getId());
            aula.setCodigo(aulaDTO.getCodigo());

            listaAulas.add(aula);
        }

        return listaAulas;
    }

}