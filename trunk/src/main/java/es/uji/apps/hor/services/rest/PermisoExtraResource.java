package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.PermisoExtra;
import es.uji.apps.hor.services.PermisoExtraService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("permisoExtra")
public class PermisoExtraResource extends CoreBaseService
{
    @InjectParam
    private PermisoExtraService permisoExtraService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getPermisosExtra() throws RegistroNoEncontradoException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        List<PermisoExtra> listaPermisosExtra = permisoExtraService
                .getPermisosExtra(connectedUserId);

        List<UIEntity> listaUIEntity = new ArrayList<UIEntity>();
        
        for (PermisoExtra permiso: listaPermisosExtra) {
            UIEntity entity = UIEntity.toUI(permiso);
            entity.put("persona", permiso.getPersona().getNombre());
            entity.put("tipoCargo", permiso.getCargo().getNombre());
            entity.put("estudio", permiso.getEstudio().getNombre());
            
            listaUIEntity.add(entity);
        }
        
        return listaUIEntity;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<UIEntity> getPermisosExtra(UIEntity entity) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);
        ParamUtils.checkNotNull(entity.get("estudioId"), entity.get("personaId"),
                entity.get("tipoCargoId"));

        Long estudioId = ParamUtils.parseLong(entity.get("estudioId"));
        Long personaId = ParamUtils.parseLong(entity.get("personaId"));
        Long tipoCargoId = ParamUtils.parseLong(entity.get("tipoCargoId"));

        PermisoExtra nuevoPermiso = permisoExtraService.addPermisosExtra(estudioId, personaId,
                tipoCargoId, connectedUserId);

        return Collections.singletonList(UIEntity.toUI(nuevoPermiso));
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletePermisosExtra(@PathParam("id") String id, UIEntity entity) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);
        ParamUtils.checkNotNull(id);

        Long permisoExtraId = ParamUtils.parseLong(id);
        
        PermisoExtra permisoExtra = permisoExtraService.getPermisoExtraById(permisoExtraId, connectedUserId);
        permisoExtraService.deletePermiso(permisoExtra, permisoExtra.getEstudio().getId(), connectedUserId);
        
        return;
    }


}