package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Grupo;
import es.uji.apps.hor.services.ConsultaGruposService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;

@Path("grupo")
public class GrupoResource
{
    @InjectParam
    private ConsultaGruposService consultaGrupos;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getGrupos(@QueryParam("semestreId") String semestreId,
            @QueryParam("cursoId") String cursoId, @QueryParam("estudioId") String estudioId)
    {
        ParamUtils.checkNotNull(cursoId, estudioId, semestreId);

        List<Grupo> grupos = consultaGrupos.getGrupos(ParamUtils.parseLong(semestreId),
                ParamUtils.parseLong(cursoId), ParamUtils.parseLong(estudioId));

        return UIEntity.toUI(grupos);
    }
}