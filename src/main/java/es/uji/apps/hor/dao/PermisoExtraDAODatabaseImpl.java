package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.PermisoExtraDTO;
import es.uji.apps.hor.db.PersonaDTO;
import es.uji.apps.hor.db.QCargoPersonaDTO;
import es.uji.apps.hor.db.QEstudioDTO;
import es.uji.apps.hor.db.QPermisoExtraDTO;
import es.uji.apps.hor.db.QPersonaDTO;
import es.uji.apps.hor.db.QTipoCargoDTO;
import es.uji.apps.hor.db.TipoCargoDTO;
import es.uji.apps.hor.model.Cargo;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.PermisoExtra;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class PermisoExtraDAODatabaseImpl extends BaseDAODatabaseImpl implements PermisoExtraDAO
{

    @Override
    public List<PermisoExtra> getPermisosExtra()
    {
        JPAQuery query = new JPAQuery(entityManager);

        QPermisoExtraDTO qPermisoExtra = QPermisoExtraDTO.permisoExtraDTO;
        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;
        QPersonaDTO qPersona = QPersonaDTO.personaDTO;
        QTipoCargoDTO qTipoCargo = QTipoCargoDTO.tipoCargoDTO;

        query.from(qPermisoExtra, qEstudio, qPersona, qTipoCargo)
                .join(qPermisoExtra.estudio, qEstudio).fetch()
                .join(qPermisoExtra.persona, qPersona).fetch()
                .join(qPermisoExtra.tipoCargo, qTipoCargo).fetch();

        List<PermisoExtra> listaPermisosExtra = new ArrayList<PermisoExtra>();
        for (PermisoExtraDTO permisoExtraDTO : query.list(qPermisoExtra))
        {
            listaPermisosExtra.add(conviertePermisoExtraDTOAPermisoExtra(permisoExtraDTO));
        }

        return listaPermisosExtra;
    }

    private PermisoExtra conviertePermisoExtraDTOAPermisoExtra(PermisoExtraDTO permisoExtraDTO)
    {
        PermisoExtra permisoExtra = new PermisoExtra();
        permisoExtra.setId(permisoExtraDTO.getId());
        
        Estudio estudio = new Estudio();
        estudio.setNombre(permisoExtraDTO.getEstudio().getNombre());
        estudio.setId(permisoExtraDTO.getEstudio().getId());
        permisoExtra.setEstudio(estudio);

        Persona persona = new Persona();
        persona.setNombre(permisoExtraDTO.getPersona().getNombre());
        persona.setId(permisoExtraDTO.getPersona().getId());
        permisoExtra.setPersona(persona);

        Cargo cargo = new Cargo();
        cargo.setNombre(permisoExtraDTO.getTipoCargo().getNombre());
        cargo.setId(permisoExtraDTO.getTipoCargo().getId());
        permisoExtra.setCargo(cargo);

        return permisoExtra;
    }

    @Override
    public List<PermisoExtra> getPermisosExtraByPersonaId(Long connectedUserId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QPermisoExtraDTO qPermisoExtra = QPermisoExtraDTO.permisoExtraDTO;
        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;
        QPersonaDTO qPersona = QPersonaDTO.personaDTO;
        QTipoCargoDTO qTipoCargo = QTipoCargoDTO.tipoCargoDTO;
        QCargoPersonaDTO qCargoPersona = QCargoPersonaDTO.cargoPersonaDTO;

        query.from(qPermisoExtra, qCargoPersona).innerJoin(qPermisoExtra.estudio, qEstudio).fetch()
                .innerJoin(qPermisoExtra.persona, qPersona).fetch()
                .innerJoin(qPermisoExtra.tipoCargo, qTipoCargo).fetch()
                .where(qPermisoExtra.tipoCargo.id.eq(qCargoPersona.cargo.id).and(qPermisoExtra.estudio.id.eq(qCargoPersona.estudio.id).and(qCargoPersona.persona.id.eq(connectedUserId))));

        List<PermisoExtra> listaPermisosExtra = new ArrayList<PermisoExtra>();
        for (PermisoExtraDTO permisoExtraDTO : query.list(qPermisoExtra))
        {
            listaPermisosExtra.add(conviertePermisoExtraDTOAPermisoExtra(permisoExtraDTO));
        }

        return listaPermisosExtra;

    }

    @Override
    @Transactional
    public PermisoExtra addPermisoExtra(Long estudioId, Long personaId, Long tipoCargoId,
            Long connectedUserId)
    {
        PermisoExtraDTO permisoExtraDTO = new PermisoExtraDTO();
        PersonaDTO personaDTO = new PersonaDTO();
        EstudioDTO estudioDTO = new EstudioDTO();
        TipoCargoDTO tipoCargoDTO = new TipoCargoDTO();

        personaDTO.setId(personaId);
        estudioDTO.setId(estudioId);
        tipoCargoDTO.setId(tipoCargoId);

        permisoExtraDTO.setPersona(personaDTO);
        permisoExtraDTO.setEstudio(estudioDTO);
        permisoExtraDTO.setTipoCargo(tipoCargoDTO);

        permisoExtraDTO = insert(permisoExtraDTO);
        return conviertePermisoExtraDTOAPermisoExtra(permisoExtraDTO);
    }

    @Override
    public PermisoExtra getPermisoExtraById(Long permisoExtraId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);

        QPermisoExtraDTO qPermisoExtra = QPermisoExtraDTO.permisoExtraDTO;

        query.from(qPermisoExtra).where(qPermisoExtra.id.eq(permisoExtraId));

        if (query.list(qPermisoExtra).size() > 0) {
            return conviertePermisoExtraDTOAPermisoExtra(query.list(qPermisoExtra).get(0));
        } else {
            throw new RegistroNoEncontradoException();
        }
    }
}