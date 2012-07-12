package es.uji.apps.hor.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.services.EstudiosService;
import es.uji.commons.rest.UIEntity;

@Path("estudio")
public class EstudioResource
{
    @InjectParam
    private EstudiosService consultaEstudios;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEstudios()
    {
        List<Estudio> estudios = consultaEstudios.getEstudios();
        
        return UIEntity.toUI(estudios);
    }
}