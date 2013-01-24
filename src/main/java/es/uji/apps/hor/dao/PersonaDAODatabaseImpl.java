package es.uji.apps.hor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.CargoPersonaDTO;
import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.DepartamentoDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.PersonaDTO;
import es.uji.apps.hor.db.QCargoPersonaDTO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.sso.dao.ApaDAO;

@Repository
public class PersonaDAODatabaseImpl extends BaseDAODatabaseImpl implements PersonaDAO
{

    @Override
    @Transactional
    public Persona insertPersona(Persona persona)
    {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setId(persona.getId());

        DepartamentoDTO departamentoDTO = new DepartamentoDTO();
        departamentoDTO.setId(persona.getDepartamento().getId());
        personaDTO.setDepartamento(departamentoDTO);

        personaDTO.setNombre(persona.getNombre());
        personaDTO.setEmail(persona.getEmail());
        personaDTO.setActividadId(persona.getActividadId());
        update(personaDTO);

        return persona;
    }

    @Override
    public Persona getPersonaById(Long personaId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        Persona persona = new Persona();

        QCargoPersonaDTO qCargo = QCargoPersonaDTO.cargoPersonaDTO;

        query.from(qCargo).where(qCargo.persona.id.eq(personaId));

        for (CargoPersonaDTO cargoDTO : query.list(qCargo))
        {
            if (persona.getCentroAutorizado() == null)
            {
                persona.setCentroAutorizado(creaCentroDeCargoDTO(cargoDTO));
            }

            persona.getEstudiosAutorizados().add(creaEstudioDeCargoDTO(cargoDTO));
        }

        return persona;
    }

    private Estudio creaEstudioDeCargoDTO(CargoPersonaDTO cargoDTO)
    {
        Estudio estudioNuevo = new Estudio();
        estudioNuevo.setId(cargoDTO.getEstudio().getId());
        estudioNuevo.setNombre(cargoDTO.getEstudio().getNombre());
        return estudioNuevo;
    }

    private Centro creaCentroDeCargoDTO(CargoPersonaDTO cargoDTO)
    {
        Centro centroNuevo = new Centro();
        centroNuevo.setId(cargoDTO.getCentro().getId());
        centroNuevo.setNombre(cargoDTO.getCentro().getNombre());
        return centroNuevo;
    }

    @Autowired
    private ApaDAO apaDAO;

    @Override
    public boolean esAdmin(Long personaId)
    {
        return apaDAO.hasPerfil("HOR", "ADMIN", personaId);
    }

    @Override
    @Transactional
    public Persona insertaCargos(Persona persona)
    {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setId(persona.getId());
  
        CentroDTO centroDTO = new CentroDTO();
        centroDTO.setId(persona.getCentroAutorizado().getId());
        
        for (Estudio estudio : persona.getEstudiosAutorizados())
        {
            EstudioDTO estudioDTO = new EstudioDTO();
            estudioDTO.setId(estudio.getId());

            CargoPersonaDTO cargoPersonaDTO = new CargoPersonaDTO();
            cargoPersonaDTO.setPersona(personaDTO);
            cargoPersonaDTO.setEstudio(estudioDTO);
            cargoPersonaDTO.setCentro(centroDTO);
            insert(cargoPersonaDTO);
        }
        
        return persona;
    }

}
