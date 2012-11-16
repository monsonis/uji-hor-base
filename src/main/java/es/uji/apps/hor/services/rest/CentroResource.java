package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.services.CentrosService;
import es.uji.commons.rest.UIEntity;

@Path("centro")
public class CentroResource
{
    @InjectParam
    private CentrosService consultaCentros;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCentros()
    {
        List<Centro> centros = consultaCentros.getCentros();
        
        return UIEntity.toUI(centros);
    }
}