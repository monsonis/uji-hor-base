package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.TipoAula;
import es.uji.apps.hor.services.TipoAulaService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;

@Path("aula/tipo")
public class TipoAulaResource
{
    @InjectParam
    private TipoAulaService tipoAulaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getTiposAulaByCentroAndEdificio(@QueryParam("centroId") String centroId,
            @QueryParam("edificio") String edificio)
    {
        ParamUtils.checkNotNull(centroId, edificio);

        List<TipoAula> tiposAula = tipoAulaService.getTiposAulaByCentroAndEdificio(
                ParamUtils.parseLong(centroId), edificio);

        return UIEntity.toUI(tiposAula);
    }
}
