package es.uji.apps.hor.services.rest;

import java.text.ParseException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.CalendarioAcademico;
import es.uji.apps.hor.services.CalendarioAcademicoService;
import es.uji.commons.rest.CoreBaseService;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.AccessManager;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Path("calendarioacademico")
public class CalendarioAcademicoResource extends CoreBaseService
{
    @InjectParam
    private CalendarioAcademicoService calendarioAcademico;

    @GET
    @Path("/festivos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCalendarioAcademicoFestivos(
            @QueryParam("fechaInicio") String fechaInicio, @QueryParam("fechaFin") String fechaFin)
            throws UnauthorizedUserException, RegistroNoEncontradoException, ParseException
    {
        Long connectedUserId = AccessManager.getConnectedUserId(request);

        List<CalendarioAcademico> listaCalendarioAcademico = calendarioAcademico
                .getCalendarioAcademicoFestivos(fechaInicio, fechaFin, connectedUserId);

        return UIEntity.toUI(listaCalendarioAcademico);
    }
}