package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CircuitoDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Circuito;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.Semestre;
import es.uji.commons.rest.auth.Role;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class CircuitoService
{
    @Autowired
    private CircuitoDAO circuitoDAO;

    @Autowired
    private PersonaDAO personaDAO;

    @Role({ "ADMIN", "USUARIO" })
    public List<Circuito> getCircuitosByEstudioIdAndSemestreIdAndGrupoId(Long estudioId,
            Long semestreId, String grupoId, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return circuitoDAO.getCircuitosByEstudioIdAndSemestreIdAndGrupoId(estudioId, semestreId,
                grupoId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public Circuito insertaCircuito(Long estudioId, Long semestreId, String grupoId, String nombre,
            Long plazas, Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        Circuito circuito = new Circuito();
        circuito.setNombre(nombre);
        circuito.setGrupo(grupoId);
        circuito.setPlazas(plazas);

        Semestre semestre = new Semestre();
        semestre.setSemestre(semestreId);
        circuito.setSemestre(semestre);

        Estudio estudio = new Estudio();
        estudio.setId(estudioId);
        circuito.setEstudio(estudio);

        Circuito resultado = circuitoDAO.insertNuevoCircuitoEnEstudio(circuito);

        return resultado;
    }

    @Role({ "ADMIN", "USUARIO" })
    public void borraCircuito(Long circuitoId, Long estudioId, Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException, RegistroConHijosException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        Circuito circuito = circuitoDAO.getCircuitoById(circuitoId, estudioId);
        circuitoDAO.deleteCircuitoById(circuito.getId());
    }

    @Role({ "ADMIN", "USUARIO" })
    public Circuito updateCircuito(Long circuitoId, Long estudioId, Long semestreId, String nombre, Long plazas,
            Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        Circuito resultado = circuitoDAO.updateCircuito(circuitoId, estudioId, nombre, plazas);
        return resultado;
        
    }
}
