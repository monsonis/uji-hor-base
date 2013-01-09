package es.uji.apps.hor.services.rest;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.services.AulaService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Path("aula")
public class AulaResource
{
    @InjectParam
    private AulaService consultaAulas;

    @GET
    @Path("estudio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getAulasAsignadasToEstudio(@PathParam("id") String estudioId,
            @QueryParam("semestreId") String semestreId)
    {
        Long semestre = Long.parseLong(semestreId);

        List<AulaPlanificacion> aulasAsignadas = consultaAulas.getAulasAsignadasToEstudio(
                Long.parseLong(estudioId), semestre);

        return UIEntity.toUI(aulasAsignadas);
    }

    @POST
    @Path("estudio")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> asignaAulaToEstudio(UIEntity entity)
            throws RegistroNoEncontradoException, AulaYaAsignadaAEstudioException
    {
        String estudioId = entity.get("estudioId");
        Long semestreId = ParamUtils.parseLong(entity.get("semestreId"));
        String aulaId = entity.get("aulaId");

        AulaPlanificacion aulaPlanificacion = consultaAulas.asignaAulaToEstudio(
                Long.parseLong(estudioId), Long.parseLong(aulaId), semestreId);

        return Collections.singletonList(UIEntity.toUI(aulaPlanificacion));
    }

    @DELETE
    @Path("estudio/{id}")
    public void deleteAulaAsignadaToEstudio(@PathParam("id") String aulaPlanificacionId)
            throws RegistroConHijosException
    {
        consultaAulas.deleteAulaAsignadaToEstudio(Long.parseLong(aulaPlanificacionId));
    }
}