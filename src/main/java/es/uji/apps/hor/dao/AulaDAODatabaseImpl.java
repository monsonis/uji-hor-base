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
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.ItemsAsignaturaDTO;
import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.db.QAulaPersonaDTO;
import es.uji.apps.hor.db.QAulaPlanificacionDTO;
import es.uji.apps.hor.db.QEstudioDTO;
import es.uji.apps.hor.db.QEstudiosCompartidosDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.QItemsAsignaturaDTO;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.Semestre;
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

        if (aulaDTO.getCentro() != null)
        {
            Centro centro = new Centro(aulaDTO.getCentro().getId(), aulaDTO.getCentro().getNombre());
            edificio.setCentro(centro);
            aula.setCentro(centro);
        }

        aula.setEdificio(edificio);

        aula.setNombre(aulaDTO.getNombre());
        aula.setCodigo(aulaDTO.getCodigo());
        aula.setPlazas(aulaDTO.getPlazas());
        return aula;
    }

    private List<AulaPlanificacion> getInformacionAulaAsignada(AulaDTO aulaDTO)
    {

        JPAQuery query = new JPAQuery(entityManager);
        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        query.from(qAulaPlanificacion).where(qAulaPlanificacion.aula.id.eq(aulaDTO.getId()));

        List<AulaPlanificacion> listaAulaPlanificacion = new ArrayList<AulaPlanificacion>();

        for (AulaPlanificacionDTO aulaPlanificacionDTO : query.list(qAulaPlanificacion))
        {
            AulaPlanificacion aulaPlanificacion = new AulaPlanificacion();
            aulaPlanificacion.setId(aulaPlanificacionDTO.getId());

            Estudio estudio = new Estudio();
            estudio.setId(aulaPlanificacionDTO.getEstudio().getId());
            aulaPlanificacion.setEstudio(estudio);

            Semestre semestre = new Semestre();
            semestre.setSemestre(aulaPlanificacionDTO.getSemestre());
            aulaPlanificacion.setSemestre(semestre);

            Aula aula = new Aula();
            aula.setId(aulaDTO.getId());
            aulaPlanificacion.setAula(aula);

            listaAulaPlanificacion.add(aulaPlanificacion);
        }

        return listaAulaPlanificacion;
    }

    private List<Evento> getInformacionEventosAula(AulaDTO aulaDTO)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO qItem = QItemDTO.itemDTO;
        QItemsAsignaturaDTO qAsignatura = QItemsAsignaturaDTO.itemsAsignaturaDTO;

        query.from(qItem).innerJoin(qItem.itemsAsignaturas, qAsignatura).fetch()
                .where(qItem.aula.id.eq(aulaDTO.getId()));

        List<Evento> listaEventos = new ArrayList<Evento>();

        for (ItemDTO itemDTO : query.list(qItem))
        {
            Evento evento = new Evento();
            evento.setId(itemDTO.getId());

            List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();

            for (ItemsAsignaturaDTO asignaturaDTO : itemDTO.getAsignaturas())
            {
                Asignatura asignatura = new Asignatura(asignaturaDTO.getAsignaturaId());
                Estudio estudio = new Estudio(asignaturaDTO.getEstudioId(),
                        asignaturaDTO.getEstudio());
                asignatura.setEstudio(estudio);

                listaAsignaturas.add(asignatura);
            }

            evento.setAsignaturas(listaAsignaturas);

            listaEventos.add(evento);
        }
        return listaEventos;
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
                .fetch()
                .where(qAulaPlanificacion.estudio.id.eq(estudioId).and(
                        qAulaPlanificacion.semestre.eq(semestreId)));

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
                        .and(qAulaPlan.semestre.eq(semestreId)));

        List<AulaPlanificacionDTO> aulasPlan = query.list(qAulaPlan);

        if (aulasPlan.size() > 0)
        {
            throw new AulaYaAsignadaAEstudioException();
        }

        AulaPlanificacionDTO aulaPlan = new AulaPlanificacionDTO();
        aulaPlan.setAula(aula);
        aulaPlan.setEstudio(estudio);
        aulaPlan.setSemestre(semestreId);

        aulaPlan = insert(aulaPlan);

        return creaAulaPlanificacionDesde(aulaPlan);
    }

    private AulaPlanificacion creaAulaPlanificacionDesde(AulaPlanificacionDTO aulaPlanificacionDTO)
    {
        AulaPlanificacion aulaPlanificacion = new AulaPlanificacion();
        aulaPlanificacion.setId(aulaPlanificacionDTO.getId());

        Estudio estudio = new Estudio();
        estudio.setId(aulaPlanificacionDTO.getEstudio().getId());
        estudio.setNombre(aulaPlanificacionDTO.getEstudio().getNombre());
        aulaPlanificacion.setEstudio(estudio);

        aulaPlanificacion.setAula(creaAulaDesdeAulaDTO(aulaPlanificacionDTO.getAula()));

        Semestre semestre = new Semestre();
        semestre.setSemestre(aulaPlanificacionDTO.getSemestre());
        semestre.setNombre(aulaPlanificacionDTO.getSemestre().toString());
        aulaPlanificacion.setSemestre(semestre);

        return aulaPlanificacion;
    }

    @Override
    @Transactional
    public void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId)
            throws RegistroConHijosException, RegistroNoEncontradoException
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
    public Aula getAulaYPlanificacionesByAulaId(Long aulaId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        query.from(qAula).where(qAula.id.eq(aulaId));

        List<AulaDTO> res = query.list(qAula);

        if (res.size() > 0)
        {
            AulaDTO aulaDTO = res.get(0);
            Aula aula = creaAulaDesdeAulaDTO(aulaDTO);
            aula.setPlanificacion(getInformacionAulaAsignada(aulaDTO));
            return aula;
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    public Aula getAulaById(Long aulaId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        query.from(qAula).where(qAula.id.eq(aulaId));

        List<AulaDTO> res = query.list(qAula);

        if (res.size() > 0)
        {
            return creaAulaDesdeAulaDTO(res.get(0));
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    public Aula getAulaConEventosById(Long aulaId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        query.from(qAula).where(qAula.id.eq(aulaId));

        List<AulaDTO> res = query.list(qAula);

        if (res.size() > 0)
        {
            AulaDTO aulaDTO = res.get(0);
            Aula aula = creaAulaDesdeAulaDTO(aulaDTO);
            aula.setEventos(getInformacionEventosAula(aulaDTO));
            return aula;
        }
        else
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
        centroDTO.setId(aula.getCentro().getId());
        centroDTO.setNombre(aula.getCentro().getNombre());

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
        estudioDTO.setId(aulaPlanificacion.getEstudio().getId());

        AulaDTO aulaDTO = new AulaDTO();
        aulaDTO.setId(aulaPlanificacion.getAula().getId());

        aulaPlanificacionDTO.setAula(aulaDTO);
        aulaPlanificacionDTO.setEstudio(estudioDTO);
        aulaPlanificacionDTO.setSemestre(aulaPlanificacion.getSemestre().getSemestre());
        aulaPlanificacionDTO = insert(aulaPlanificacionDTO);

        return this.creaAulaPlanificacionDesde(aulaPlanificacionDTO);
    }

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
            tipoAula.setValor(tipoAulaStr);
            tiposAula.add(tipoAula);
        }

        return tiposAula;
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

    @Override
    public AulaPlanificacion getAulaPlanificacionByAulaEstudioSemestre(Long aulaId, Long estudioId,
            Long semestreId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaPlanificacionDTO qAulaPlanificacionDTO = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        query.from(qAulaPlanificacionDTO).where(
                qAulaPlanificacionDTO.aula.id.eq(aulaId).and(
                        qAulaPlanificacionDTO.semestre.eq(semestreId).and(
                                qAulaPlanificacionDTO.estudio.id.eq(estudioId))));

        if (query.list(qAulaPlanificacionDTO).size() > 0)
        {
            return creaAulaPlanificacionDesde(query.list(qAulaPlanificacionDTO).get(0));
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    public List<Aula> getAulasVisiblesPorUsuarioFiltradasPor(Long centroId, Long semestreId,
            String edificio, String tipoAula, String planta, Long connectedUserId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;
        QAulaPersonaDTO qAulaPersona = QAulaPersonaDTO.aulaPersonaDTO;
        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        query.from(qAula, qAulaPersona, qAulaPlanificacion).where(
                qAulaPersona.personaId.eq(connectedUserId).and(qAula.id.eq(qAulaPersona.aulaId))
                        .and(qAulaPlanificacion.aula.id.eq(qAula.id))
                        .and(qAula.centro.id.eq(centroId))
                        .and(qAulaPlanificacion.semestre.eq(semestreId))
                        .and(qAula.edificio.eq(edificio)));

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

    @Override
    public List<TipoAula> getTiposAulaVisiblesPorUsuarioByCentroAndSemestreAndEdificio(
            Long centroId, Long semestreId, String edificio, Long connectedUserId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;
        QAulaPersonaDTO qAulaPersona = QAulaPersonaDTO.aulaPersonaDTO;
        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        List<String> tiposAula = query
                .from(qAula, qAulaPersona, qAulaPlanificacion)
                .where(qAulaPersona.personaId.eq(connectedUserId)
                        .and(qAula.id.eq(qAulaPersona.aulaId))
                        .and(qAulaPlanificacion.aula.id.eq(qAula.id))
                        .and(qAula.centro.id.eq(centroId)).and(qAula.edificio.eq(edificio))
                        .and(qAulaPlanificacion.semestre.eq(semestreId))).orderBy(qAula.tipo.asc())
                .distinct().list(qAula.tipo);

        return creaListaTiposAulaDesde(tiposAula);
    }

    @Override
    public AulaPlanificacion getAulaPlanificacionById(Long aulaPlanificacionId)
            throws RegistroNoEncontradoException
    {
        try
        {
            return creaAulaPlanificacionDesde(get(AulaPlanificacionDTO.class, aulaPlanificacionId)
                    .get(0));
        }
        catch (Exception e)
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    public List<Estudio> getEstudiosComunesByEstudioId(Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;
        QEstudiosCompartidosDTO qEstudiosCompartidosDTO = QEstudiosCompartidosDTO.estudiosCompartidosDTO;

        query.from(qEstudio).innerJoin(qEstudio.estudiosCompartidos, qEstudiosCompartidosDTO).where(qEstudiosCompartidosDTO.estudio.id.eq(estudioId));
        
        List<Estudio> listaEstudiosCompartidos = new ArrayList<Estudio>();
        
        for (EstudioDTO estudioDTO: query.list(qEstudio)) {
            listaEstudiosCompartidos.add(creaEstudioDesdeEstudioDTO(estudioDTO));
        }
        return listaEstudiosCompartidos;
                
    }

    private Estudio creaEstudioDesdeEstudioDTO(EstudioDTO estudioDTO)
    {
        Estudio estudio = new Estudio();
        estudio.setId(estudioDTO.getId());
        estudio.setNombre(estudioDTO.getNombre());
        return estudio;
    }
}