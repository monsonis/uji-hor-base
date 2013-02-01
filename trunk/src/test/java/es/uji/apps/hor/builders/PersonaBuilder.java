package es.uji.apps.hor.builders;

import java.util.List;

import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Departamento;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;



public class PersonaBuilder
{
    private Persona persona;
    private PersonaDAO personaDAO;

    public PersonaBuilder(PersonaDAO personaDAO)
    {
        this.personaDAO = personaDAO;
        persona = new Persona();
    }

    public PersonaBuilder()
    {
        this(null);
    }

    public PersonaBuilder withNombre(String nombre)
    {
        persona.setNombre(nombre);
        return this;
    }

    public PersonaBuilder withEmail(String email)
    {
        persona.setEmail(email);
        return this;
    }

    public PersonaBuilder withActividadId(String actividadId)
    {
        persona.setActividadId(actividadId);
        return this;
    }

    public PersonaBuilder withId(Long id)
    {
        persona.setId(id);
        return this;
    }

    public PersonaBuilder withDepartamento(Departamento departamento)
    {
        persona.setDepartamento(departamento);
        return this;
    }
    
    public PersonaBuilder withCentroAutorizado(Centro centro)
    {
        persona.setCentroAutorizado(centro);
        return this;
    }
    
    public PersonaBuilder withEstudiosAutorizados(List<Estudio> listaEstudiosAutorizados)
    {
        persona.setEstudiosAutorizados(listaEstudiosAutorizados);
        return this;
    }

    public PersonaBuilder withEstudioAutorizado(Estudio estudio)
    {
        persona.getEstudiosAutorizados().add(estudio);
        return this;
    }

    public Persona build()
    {
        if (personaDAO != null)
        {
            persona = personaDAO.insertPersona(persona);
            personaDAO.insertaCargos(persona);
        }

        return persona;
    }

}
