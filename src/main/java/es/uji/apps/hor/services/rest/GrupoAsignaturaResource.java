package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.apps.hor.services.ConsultaGruposAsignaturasService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;

@Path("grupoAsignatura")
public class GrupoAsignaturaResource
{
    @InjectParam
    private ConsultaGruposAsignaturasService consultaGruposAsignaturas;

    @GET
    @Path("sinAsignar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getGruposAsignaturasSinAsignar(@QueryParam("estudioId") String estudioId,
            @QueryParam("cursoId") String cursoId, @QueryParam("semestreId") String semestreId,
            @QueryParam("grupoId") String grupoId)
    {
        List<UIEntity> list = new ArrayList<UIEntity>();

        if (ParamUtils.isNotNull(estudioId) && ParamUtils.isNotNull(cursoId)
                && ParamUtils.isNotNull(semestreId) && ParamUtils.isNotNull(grupoId))
        {
            List<GrupoAsignatura> gruposAsignaturas = consultaGruposAsignaturas
                    .gruposAsignaturasSinAsignar(ParamUtils.parseLong(estudioId),
                            ParamUtils.parseLong(cursoId), ParamUtils.parseLong(semestreId),
                            grupoId);

            list = UIEntity.toUI(gruposAsignaturas);
        }

        return list;
    }

}
