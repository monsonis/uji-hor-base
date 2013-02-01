package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Cargo;
import es.uji.apps.hor.model.Persona;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.db.LookupDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface PersonaDAO extends BaseDAO, LookupDAO
{
    Persona insertPersona(Persona persona);

    Persona getPersonaConTitulacionesYCentrosById(Long personaId)
            throws RegistroNoEncontradoException;

    boolean esAdmin(Long personaId);

    Persona insertaCargos(Persona persona);

    List<Cargo> getTodosLosCargos();
}
