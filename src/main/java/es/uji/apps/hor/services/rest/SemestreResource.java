package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.services.SemestresService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.sso.AccessManager;

@Path("semestre")
public class SemestreResource extends CoreBaseService
{
    @InjectParam
    private SemestresService consultaSemestres;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getSemestres(@QueryParam("cursoId") String cursoId,
            @QueryParam("estudioId") String estudioId)
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        ParamUtils.checkNotNull(cursoId, estudioId);

        List<Semestre> semestres = consultaSemestres.getSemestres(ParamUtils.parseLong(cursoId),
                ParamUtils.parseLong(estudioId), connectedUserId);

        return UIEntity.toUI(semestres);
    }
}