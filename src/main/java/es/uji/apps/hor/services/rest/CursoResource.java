package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Curso;
import es.uji.apps.hor.services.ConsultaCursosService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;

@Path("curso")
public class CursoResource
{
    @InjectParam
    private ConsultaCursosService consultaCursos;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCursos(@QueryParam("estudioId") String estudioId)
    {
        ParamUtils.checkNotNull(estudioId);
        
        List<Curso> cursos = consultaCursos.getCursos(ParamUtils.parseLong(estudioId));
        
        return UIEntity.toUI(cursos);
    }
}