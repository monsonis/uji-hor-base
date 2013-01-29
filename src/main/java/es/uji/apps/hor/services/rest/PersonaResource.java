package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.services.PersonaService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("persona")
public class PersonaResource extends CoreBaseService
{
    @InjectParam
    private PersonaService personaService;

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<UIEntity> getPermisos() throws UnauthorizedUserException
//    {
//        Long connectedUserId = AccessManager.getConnectedUserId(request);
//
//        List<PermisoExtra> listaPermisos = consultaPermisos.getPermisos(connectedUserId);
//
//        return UIEntity.toUI(listaPermisos);
//    }
    
    @GET
    @Path("cargos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCargos() throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        Persona persona = personaService.getPersonaById(connectedUserId);

        return UIEntity.toUI(persona.getCargos());
    }
}