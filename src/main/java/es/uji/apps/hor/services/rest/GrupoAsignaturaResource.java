package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.apps.hor.services.GruposAsignaturasService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("grupoAsignatura")
public class GrupoAsignaturaResource extends CoreBaseService
{
    @InjectParam
    private GruposAsignaturasService gruposAsignaturasService;

    @GET
    @Path("sinAsignar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getGruposAsignaturasSinAsignar(@QueryParam("estudioId") String estudioId,
            @QueryParam("cursoId") String cursoId, @QueryParam("semestreId") String semestreId,
            @QueryParam("gruposId") String gruposIds,
            @QueryParam("calendariosIds") String calendariosIds) throws UnauthorizedUserException,
            RegistroNoEncontradoException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        ParamUtils.checkNotNull(estudioId, cursoId, semestreId, gruposIds);

        String[] calendarios = calendariosIds.split(";");
        List<Long> calendariosList = new ArrayList<Long>();

        for (String calendario : calendarios)
        {
            calendario = calendario.trim();
            if (!calendario.equals(""))
            {
                calendariosList.add(ParamUtils.parseLong(calendario));
            }
        }

        String[] grupos = gruposIds.split(";");
        List<String> gruposList = new ArrayList<String>();

        for (String grupo : grupos)
        {
            grupo = grupo.trim();
            if (!grupo.equals(""))
            {
                gruposList.add(grupo);
            }
        }

        List<UIEntity> list = new ArrayList<UIEntity>();

        if (calendariosList.size() != 0)
        {
            List<GrupoAsignatura> gruposAsignaturas = gruposAsignaturasService
                    .getGruposAsignaturasSinAsignar(ParamUtils.parseLong(estudioId),
                            ParamUtils.parseLong(cursoId), ParamUtils.parseLong(semestreId),
                            gruposList, calendariosList, connectedUserId);

            list = UIEntity.toUI(gruposAsignaturas);
        }

        return list;
    }

    @PUT
    @Path("sinAsignar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> planificaGruposAsignaturasSinAsignar(@PathParam("id") String id,
            @QueryParam("estudioId") String estudioId) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        GrupoAsignatura grupoAsignatura = gruposAsignaturasService
                .planificaGrupoAsignaturaSinAsignar(ParamUtils.parseLong(id),
                        ParamUtils.parseLong(estudioId), connectedUserId);
        return Collections.singletonList(UIEntity.toUI(grupoAsignatura));
    }

}
