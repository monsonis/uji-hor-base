package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.Persona;
import es.uji.commons.db.BaseDAO;

public interface PersonaDAO extends BaseDAO
{
    Persona insertPersona(Persona persona);
    
    Persona getPersonaById(Long personaId);

    boolean esAdmin(Long personaId);
    
    Persona insertaCargos(Persona persona);

}
