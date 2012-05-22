package es.uji.apps.hor.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.uji.commons.rest.UIEntity;

@Path("horario")
public class HorarioService
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCuentas()
    {
        return new ArrayList<UIEntity>();
    }
}