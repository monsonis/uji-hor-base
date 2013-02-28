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
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Circuito;
import es.uji.apps.hor.services.CircuitoService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("circuito")
public class CircuitoResource extends CoreBaseService
{
    @InjectParam
    private CircuitoService circuitoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCircuitos(@QueryParam("estudioId") String estudioId,
            @QueryParam("grupoId") String grupoId, @QueryParam("semestreId") String semestreId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        ParamUtils.checkNotNull(estudioId, semestreId, grupoId);

        List<Circuito> circuitos = circuitoService.getCircuitosByEstudioIdAndSemestreIdAndGrupoId(
                ParamUtils.parseLong(estudioId), ParamUtils.parseLong(semestreId), grupoId,
                connectedUserId);

        List<UIEntity> listaResultado = new ArrayList<UIEntity>();

        for (Circuito circuito : circuitos)
        {
            UIEntity entity = UIEntity.toUI(circuito);
            entity.put("semestre", circuito.getSemestre().getSemestre());
            listaResultado.add(entity);
        }
        return listaResultado;

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> insertaCircuito(UIEntity entity) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        String estudioId = entity.get("estudioId");
        String semestreId = entity.get("semestreId");
        String grupoId = entity.get("grupoId");
        String nombre = entity.get("nombre");
        String plazas = entity.get("plazas");

        ParamUtils.checkNotNull(estudioId, semestreId, grupoId, nombre, plazas);

        Circuito circuito = circuitoService.insertaCircuito(ParamUtils.parseLong(estudioId),
                ParamUtils.parseLong(semestreId), grupoId, nombre, ParamUtils.parseLong(plazas), connectedUserId);

        return Collections.singletonList(UIEntity.toUI(circuito));
    }

    @DELETE
    @Path("estudio/{id}")
    public void deleteAulaAsignadaToEstudio(@PathParam("id") String circuitoId, @PathParam("estudioId") String estudioId) throws RegistroNoEncontradoException, RegistroConHijosException, UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);
        
        ParamUtils.checkNotNull(circuitoId, estudioId);
        circuitoService.borraCircuito(ParamUtils.parseLong(circuitoId), ParamUtils.parseLong(estudioId), connectedUserId);

    }

}