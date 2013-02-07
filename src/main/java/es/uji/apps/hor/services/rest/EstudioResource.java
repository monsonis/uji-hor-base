package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.services.EstudiosService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("estudio")
public class EstudioResource extends CoreBaseService
{
    @InjectParam
    private EstudiosService consultaEstudios;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEstudiosPorCentro(@QueryParam("centroId") String centroId)
            throws NumberFormatException, UnauthorizedUserException, RegistroNoEncontradoException
    {
        List<Estudio> estudios = new ArrayList<Estudio>();
        ParamUtils.checkNotNull(centroId);
        
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        estudios = consultaEstudios
                .getEstudiosByCentroId(Long.parseLong(centroId), connectedUserId);

        return UIEntity.toUI(estudios);
    }

    @GET
    @Path("todos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getTodosLosEstudios() throws NumberFormatException,
            UnauthorizedUserException, RegistroNoEncontradoException
    {
        List<Estudio> estudios = new ArrayList<Estudio>();

        Long connectedUserId = AccessManager.getConnectedUserId(request);

        estudios = consultaEstudios.getTodosLosEstudios(connectedUserId);
        return UIEntity.toUI(estudios);
    }

}