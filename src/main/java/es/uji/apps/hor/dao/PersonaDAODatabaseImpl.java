package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import es.uji.apps.hor.db.QPersonaDTO;
import es.uji.apps.hor.db.QTipoCargoDTO;
import es.uji.apps.hor.db.TipoCargoDTO;
import es.uji.apps.hor.model.Cargo;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.model.lookup.LookupItem;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
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
    public Persona getPersonaConTitulacionesYCentrosById(Long personaId)
            throws RegistroNoEncontradoException
    {
        Persona persona = null;

        JPAQuery query = new JPAQuery(entityManager);

        QCargoPersonaDTO qCargoPersona = QCargoPersonaDTO.cargoPersonaDTO;
        QPersonaDTO qPersona = QPersonaDTO.personaDTO;

        query.from(qPersona).innerJoin(qPersona.cargosPersona, qCargoPersona).fetch()
                .where(qPersona.id.eq(personaId));

        for (PersonaDTO personaDTO : query.list(qPersona))
        {
            persona = new Persona();

            persona.setNombre(personaDTO.getNombre());
            persona.setEmail(personaDTO.getEmail());
            persona.setActividadId(persona.getActividadId());

            Map<Long, String> tiposCargo = new HashMap<Long, String>();

            for (CargoPersonaDTO cargoPersonaDTO : personaDTO.getCargosPersona())
            {
                if (persona.getCentroAutorizado() == null)
                {
                    persona.setCentroAutorizado(creaCentroDeCargoDTO(cargoPersonaDTO));
                }

                if (cargoPersonaDTO.getEstudio() != null)
                {
                    persona.getEstudiosAutorizados().add(creaEstudioDeCargoDTO(cargoPersonaDTO));
                }
                if (!tiposCargo.values().contains(cargoPersonaDTO.getNombreCargo()))
                {
                    tiposCargo.put(cargoPersonaDTO.getCargo().getId(),
                            cargoPersonaDTO.getNombreCargo());
                }

            }

            for (Map.Entry<Long, String> entry : tiposCargo.entrySet())
            {
                Cargo cargo = new Cargo();
                cargo.setNombre(entry.getValue());
                cargo.setId(entry.getKey());

                persona.getCargos().add(cargo);
            }
            break;

        }

        if (persona == null)
        {
            throw new RegistroNoEncontradoException();
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

        if (persona.getEstudiosAutorizados().size() > 0)
        {
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
        }
        else
        {
            CargoPersonaDTO cargoPersonaDTO = new CargoPersonaDTO();
            cargoPersonaDTO.setPersona(personaDTO);
            cargoPersonaDTO.setCentro(centroDTO);
            insert(cargoPersonaDTO);

        }
        return persona;
    }

    @Override
    public List<LookupItem> search(String cadena)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QPersonaDTO qPersona = QPersonaDTO.personaDTO;

        List<LookupItem> result = new ArrayList<LookupItem>();
        if (cadena != null && !cadena.isEmpty())
        {
            query.from(qPersona).where(
                    qPersona.nombre.lower().like("%" + cadena.toLowerCase() + "%"));
            List<PersonaDTO> listaPersonas = query.list(qPersona);
            for (PersonaDTO personaDTO : listaPersonas)
            {
                LookupItem lookupItem = new LookupItem();
                lookupItem.setId(String.valueOf(personaDTO.getId()));
                lookupItem.setNombre(personaDTO.getNombre());
                result.add(lookupItem);
            }
        }
        return result;
    }

    @Override
    public List<Cargo> getTodosLosCargos()
    {
        JPAQuery query = new JPAQuery(entityManager);
        QTipoCargoDTO qTipoCargo = QTipoCargoDTO.tipoCargoDTO;
        
        query.from(qTipoCargo);
        
        return convierteCargoDTOaCargo(query.list(qTipoCargo));
    }

    private List<Cargo> convierteCargoDTOaCargo(List<TipoCargoDTO> listaCargosDTO)
    {
        List<Cargo> listaCargos = new ArrayList<Cargo>();
        
        for (TipoCargoDTO cargoDTO: listaCargosDTO) {
            Cargo cargo = new Cargo();
            cargo.setId(cargoDTO.getId());
            cargo.setNombre(cargoDTO.getNombre());
            listaCargos.add(cargo);
        }
        
        return listaCargos;
    }
}
