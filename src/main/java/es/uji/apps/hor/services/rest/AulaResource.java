package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
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
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.TipoAula;
import es.uji.apps.hor.services.AulaService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("aula")
public class AulaResource extends CoreBaseService
{
    @InjectParam
    private AulaService consultaAulas;

    @GET
    @Path("estudio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getAulasAsignadasToEstudio(@PathParam("id") String estudioId,
            @QueryParam("semestreId") String semestreId) throws NumberFormatException,
            UnauthorizedUserException, RegistroNoEncontradoException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        Long semestre = Long.parseLong(semestreId);

        List<AulaPlanificacion> aulasAsignadas = consultaAulas.getAulasAsignadasToEstudio(
                Long.parseLong(estudioId), semestre, connectedUserId);

        List<UIEntity> listaAulas = new ArrayList<UIEntity>();

        for (AulaPlanificacion aulaPlanificacion : aulasAsignadas)
        {
            listaAulas.add(aulaPlanificacionToUI(aulaPlanificacion));
        }

        return listaAulas;
    }

    private UIEntity aulaPlanificacionToUI(AulaPlanificacion aulaPlanificacion)
    {
        UIEntity entity = UIEntity.toUI(aulaPlanificacion);
        entity.put("nombre", aulaPlanificacion.getAula().getNombre());
        entity.put("edificio", aulaPlanificacion.getAula().getEdificio().getNombre());
        entity.put("tipo", aulaPlanificacion.getAula().getTipo().getNombre());
        entity.put("planta", aulaPlanificacion.getAula().getPlanta().getNombre());
        entity.put("semestreId", aulaPlanificacion.getSemestre().getNombre());

        return entity;
    }

    @POST
    @Path("estudio")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> asignaAulaToEstudio(UIEntity entity)
            throws RegistroNoEncontradoException, AulaYaAsignadaAEstudioException,
            NumberFormatException, UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        String estudioId = entity.get("estudioId");
        Long semestreId = ParamUtils.parseLong(entity.get("semestreId"));
        String aulaId = entity.get("aulaId");

        AulaPlanificacion aulaPlanificacion = consultaAulas.asignaAulaToEstudio(
                Long.parseLong(estudioId), Long.parseLong(aulaId), semestreId, connectedUserId);

        return Collections.singletonList(aulaPlanificacionToUI(aulaPlanificacion));
    }

    @DELETE
    @Path("estudio/{id}")
    public void deleteAulaAsignadaToEstudio(@PathParam("id") String aulaPlanificacionId)
            throws RegistroConHijosException, RegistroNoEncontradoException, NumberFormatException,
            UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);
        ParamUtils.checkNotNull(aulaPlanificacionId);

        consultaAulas.deleteAulaAsignadaToEstudio(Long.parseLong(aulaPlanificacionId), connectedUserId);
    }

    @GET
    @Path("tipo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getTiposAulaByCentroAndEdificio(@QueryParam("centroId") String centroId,
            @QueryParam("edificio") String edificio) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        ParamUtils.checkNotNull(centroId, edificio);

        Long connectedUserId = AccessManager.getConnectedUserId(request);

        List<TipoAula> tiposAula = consultaAulas.getTiposAulaByCentroAndEdificio(
                ParamUtils.parseLong(centroId), edificio, connectedUserId);

        return UIEntity.toUI(tiposAula);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getAulasFiltradasPor(@QueryParam("centroId") String centroId,
            @QueryParam("edificio") String edificio, @QueryParam("tipoAula") String tipoAula,
            @QueryParam("planta") String planta) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        ParamUtils.checkNotNull(centroId, edificio);

        Long connectedUserId = AccessManager.getConnectedUserId(request);

        tipoAula = (tipoAula == null || tipoAula.equals("")) ? null : tipoAula;
        planta = (planta == null || planta.equals("")) ? null : planta;

        List<Aula> aulas = consultaAulas.getAulasFiltradasPor(ParamUtils.parseLong(centroId),
                edificio, tipoAula, planta, connectedUserId);

        return UIEntity.toUI(aulas);
    }
}