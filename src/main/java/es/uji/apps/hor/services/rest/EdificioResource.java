package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.services.EdificiosService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;

@Path("edificio")
public class EdificioResource extends CoreBaseService
{
    @InjectParam
    private EdificiosService edificiosService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEdificios(@QueryParam("centroId") String centroId)
    {
        ParamUtils.checkNotNull(centroId);

        List<Edificio> edificios = edificiosService.getEdificiosByCentroId(ParamUtils
                .parseLong(centroId));

        return UIEntity.toUI(edificios);
    }
}
