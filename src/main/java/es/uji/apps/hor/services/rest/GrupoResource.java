package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Grupo;
import es.uji.apps.hor.services.GruposService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("grupo")
public class GrupoResource extends CoreBaseService
{
    @InjectParam
    private GruposService consultaGrupos;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getGrupos(@QueryParam("semestreId") String semestreId,
            @QueryParam("cursoId") String cursoId, @QueryParam("estudioId") String estudioId)
            throws UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        ParamUtils.checkNotNull(cursoId, estudioId, semestreId);

        List<Grupo> grupos = consultaGrupos.getGrupos(ParamUtils.parseLong(semestreId),
                ParamUtils.parseLong(cursoId), ParamUtils.parseLong(estudioId), connectedUserId);

        return UIEntity.toUI(grupos);
    }
}