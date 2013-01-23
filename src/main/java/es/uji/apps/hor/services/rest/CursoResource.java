package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Curso;
import es.uji.apps.hor.services.CursosService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("curso")
public class CursoResource extends CoreBaseService
{
    @InjectParam
    private CursosService consultaCursos;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCursos(@QueryParam("estudioId") String estudioId)
            throws UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        ParamUtils.checkNotNull(estudioId);

        List<Curso> cursos = consultaCursos.getCursos(ParamUtils.parseLong(estudioId),
                connectedUserId);

        return UIEntity.toUI(cursos);
    }
}