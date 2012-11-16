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
import es.uji.commons.rest.UIEntity;

@Path("estudio")
public class EstudioResource
{
    @InjectParam
    private EstudiosService consultaEstudios;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEstudiosPorCentro(@QueryParam("centroId") String centroId)
    {
        List<Estudio> estudios = new ArrayList<Estudio>();

        if (centroId == null || centroId.isEmpty())
        {
            estudios = consultaEstudios.getEstudios();
        }
        else
        {
            estudios = consultaEstudios.getEstudiosByCentroId(Long.parseLong(centroId));
        }

        return UIEntity.toUI(estudios);
    }
}