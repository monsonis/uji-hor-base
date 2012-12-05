package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.apps.hor.services.SemestresDetalleService;
import es.uji.commons.rest.UIEntity;

@Path("semestredetalle")
public class SemestreDetalleResource
{

    @InjectParam
    private SemestresDetalleService consultaSemestresDetalle;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getSemestresDetailTodos()
    {
        List<SemestreDetalle> semestresDetalles = consultaSemestresDetalle
                .getSemestresDetallesTodos();

        List<UIEntity> listaResultados = new ArrayList<UIEntity>();

        for (SemestreDetalle semestreDetalle : semestresDetalles)
        {
            UIEntity resultado = UIEntity.toUI(semestreDetalle);
            resultado.put("nombreSemestre", semestreDetalle.getSemestre().getNombre());
            resultado.put("nombreTipoEstudio", semestreDetalle.getTipo_estudio().getNombre());
            listaResultados.add(resultado);
        }

        return listaResultados;
    }
    
    @GET
    @Path("estudio/{estudioId}/semestre/{semestreId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getSemestresDetallePorTipoEstudio(@PathParam("estudioId") Long estudioId, @PathParam("semestreId") String semestreId)
    {
        List<SemestreDetalle> semestresDetalles = consultaSemestresDetalle.getSemestresDetallesPorEstudioIdYSemestreId(estudioId, Long.parseLong(semestreId));

        List<UIEntity> listaResultados = new ArrayList<UIEntity>();

        for (SemestreDetalle semestreDetalle : semestresDetalles)
        {
            UIEntity resultado = UIEntity.toUI(semestreDetalle);
            resultado.put("nombreSemestre", semestreDetalle.getSemestre().getNombre());
            resultado.put("nombreTipoEstudio", semestreDetalle.getTipo_estudio().getNombre());
            listaResultados.add(resultado);
        }

        return listaResultados;
    }

}
