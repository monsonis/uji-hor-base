package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Circuito;
import es.uji.apps.hor.services.CircuitoService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("circuito")
public class CircuitoResource extends CoreBaseService
{
    @InjectParam
    private CircuitoService circuitoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCursos(@QueryParam("estudioId") String estudioId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        ParamUtils.checkNotNull(estudioId);

        List<Circuito> circuitos = circuitoService.getCircuitosByEstudioId(
                ParamUtils.parseLong(estudioId), connectedUserId);

        return UIEntity.toUI(circuitos);
    }
}