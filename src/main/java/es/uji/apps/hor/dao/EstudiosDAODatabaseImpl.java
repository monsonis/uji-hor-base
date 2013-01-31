package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.QCargoPersonaDTO;
import es.uji.apps.hor.db.QEstudioDTO;
import es.uji.apps.hor.db.TipoEstudioDTO;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class EstudiosDAODatabaseImpl extends BaseDAODatabaseImpl implements EstudiosDAO
{
    @Override
    public List<Estudio> getEstudios()
    {
        JPAQuery query = new JPAQuery(entityManager);
        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;

        List<EstudioDTO> listaEstudios = query.from(qEstudio).orderBy(qEstudio.nombre.asc())
                .list(qEstudio);

        List<Estudio> estudios = new ArrayList<Estudio>();

        for (EstudioDTO estudioDTO : listaEstudios)
        {
            estudios.add(creaEstudioDesdeEstudioDTO(estudioDTO));
        }

        return estudios;
    }

    @Override
    public List<Estudio> getEstudiosVisiblesPorUsuario(Long userId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;
        QCargoPersonaDTO qCargo = QCargoPersonaDTO.cargoPersonaDTO;

        List<EstudioDTO> listaEstudios = query.from(qEstudio)
                .innerJoin(qEstudio.cargosPersona, qCargo).where(qCargo.persona.id.eq(userId))
                .orderBy(qEstudio.nombre.asc()).list(qEstudio);

        List<Estudio> estudios = new ArrayList<Estudio>();

        for (EstudioDTO estudioDTO : listaEstudios)
        {
            estudios.add(creaEstudioDesdeEstudioDTO(estudioDTO));
        }

        return estudios;
    }

    @Override
    public List<Estudio> getEstudiosByCentroId(Long centroId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;

        query.from(qEstudio).where(qEstudio.centro.id.eq(centroId)).orderBy(qEstudio.nombre.asc());

        List<Estudio> listaEstudios = new ArrayList<Estudio>();

        for (EstudioDTO estudioDTO : query.list(qEstudio))
        {
            listaEstudios.add(creaEstudioDesdeEstudioDTO(estudioDTO));
        }

        return listaEstudios;
    }

    @Override
    public List<Estudio> getEstudiosByCentroIdVisiblesPorUsuario(Long centroId, Long userId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;
        QCargoPersonaDTO qCargo = QCargoPersonaDTO.cargoPersonaDTO;

        List<EstudioDTO> listaEstudios = query.from(qEstudio, qCargo)
                .join(qCargo.estudio, qEstudio)
                .where(qCargo.persona.id.eq(userId).and(qEstudio.centro.id.eq(centroId)))
                .orderBy(qEstudio.nombre.asc()).list(qEstudio);

        List<Estudio> estudios = new ArrayList<Estudio>();

        for (EstudioDTO estudioDTO : listaEstudios)
        {
            estudios.add(creaEstudioDesdeEstudioDTO(estudioDTO));
        }

        return estudios;
    }

    private Estudio creaEstudioDesdeEstudioDTO(EstudioDTO estudioDTO)
    {
        Estudio estudio = new Estudio(estudioDTO.getId(), estudioDTO.getNombre());
        TipoEstudio tipoEstudio = new TipoEstudio(estudioDTO.getTipoEstudio().getId(), estudioDTO
                .getTipoEstudio().getNombre());

        estudio.setTipoEstudio(tipoEstudio);
        estudio.setId(estudioDTO.getId());
        return estudio;
    }

    private EstudioDTO convierteEstudioAEstudioDTO(Estudio estudio)
    {
        TipoEstudioDTO tipoEstudioDTO = new TipoEstudioDTO();
        tipoEstudioDTO.setId(estudio.getTipoEstudio().getId());
        tipoEstudioDTO.setNombre(estudio.getTipoEstudio().getNombre());

        EstudioDTO estudioDTO = new EstudioDTO();
        estudioDTO.setId(estudio.getId());
        estudioDTO.setNombre(estudio.getNombre());
        estudioDTO.setTipoEstudio(tipoEstudioDTO);
        return estudioDTO;
    }

    @Override
    public Estudio insert(Estudio estudio)
    {
        EstudioDTO estudioDTO = convierteEstudioAEstudioDTO(estudio);
        EstudioDTO nuevoEstudioDTO = insert(estudioDTO);
        return creaEstudioDesdeEstudioDTO(nuevoEstudioDTO);
    }
}