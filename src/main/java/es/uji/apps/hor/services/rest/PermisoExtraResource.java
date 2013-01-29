package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.PermisoExtra;
import es.uji.apps.hor.services.PermisoExtraService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.sso.AccessManager;

@Path("permisoExtra")
public class PermisoExtraResource extends CoreBaseService
{
    @InjectParam
    private PermisoExtraService permisoExtrsaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getPermisosExtra()
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        List<PermisoExtra> listaPermisosExtra = permisoExtrsaService.getPermisosExtra(connectedUserId);

        return UIEntity.toUI(listaPermisosExtra);
    }

}