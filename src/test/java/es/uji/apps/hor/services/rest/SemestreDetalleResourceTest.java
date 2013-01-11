package es.uji.apps.hor.services.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.builders.SemestreDetalleBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;
import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.SemestresDetalleDAO;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.rest.UIEntity;

public class SemestreDetalleResourceTest extends AbstractRestTest
{
    @Autowired
    private SemestresDetalleDAO semestresDetalleDAO;
    
    @Autowired
    private EstudiosDAO estudiosDAO;
    
    private Long estudioId;
    private Long semestreId;

    @Before
    public void creaDatosIniciales()
    {
        TipoEstudio tipoEstudio = new TipoEstudioBuilder().withNombre("Tipo Estudio Prueba 1")
                .withOrden(new Integer(1)).build();

        Estudio estudio = new EstudioBuilder(estudiosDAO).withNombre("Estudio 1").withTipoEstudio("Grau")
                .withTipoEstudioId("G").build();
        estudioId = estudio.getId();

        Semestre semestre1 = new SemestreBuilder().withNombre("Semestre 1").build();
        semestreId = semestre1.getSemestre();
        
        Semestre semestre2 = new SemestreBuilder().withNombre("Semestre 1").build();

        SemestreDetalle semestreDetalle1 = new SemestreDetalleBuilder(semestresDetalleDAO)
                .withSemestre(semestre1).withTipoEstudio(tipoEstudio).withFechaInicio(new Date())
                .withFechaFin(new Date()).withNumeroSemanas(new Long(1)).build();

        SemestreDetalle semestreDetalle2 = new SemestreDetalleBuilder(semestresDetalleDAO)
                .withSemestre(semestre2).withTipoEstudio(tipoEstudio).withFechaInicio(new Date())
                .withFechaFin(new Date()).withNumeroSemanas(new Long(2)).build();

        SemestreDetalle semestreDetalle3 = new SemestreDetalleBuilder(semestresDetalleDAO)
                .withSemestre(semestre1).withTipoEstudio(tipoEstudio).withFechaInicio(new Date())
                .withFechaFin(new Date()).withNumeroSemanas(new Long(3)).build();
    }

    @Test
    public void elServicioDevuelveDatos()
    {
        List<UIEntity> listaSemestres = getListadoTodosSemestres();

        assertThat(listaSemestres, hasSize(greaterThan(0)));
    }

    @Test
    public void elServicioNoDevuelveDatosRepetidos()
    {
        List<UIEntity> listaSemestres = getListadoTodosSemestres();

        assertThat(tieneDatosDuplicados(listaSemestres), is(false));
    }
    
    public void elServicioDevuelveLosDetallesDeLosSemestresPorHorarioYSemestre()
    {
        List<UIEntity> listaSemestres = getDetallesSemestrePorEstudio(estudioId, semestreId);
        
        assertThat(listaSemestres, hasSize(2));
    }

    private Boolean tieneDatosDuplicados(List<UIEntity> listaSemestres)
    {
        Set<String> ids_de_entidades = new HashSet<String>();
        for (UIEntity entidad : listaSemestres)
        {
            String entidad_id = entidad.get("id");
            if (ids_de_entidades.contains(entidad_id))
            {
                return true;
            }
            else
            {
                ids_de_entidades.add(entidad_id);
            }
        }
        return false;
    }

    private List<UIEntity> getListadoTodosSemestres()
    {
        ClientResponse response = resource.path("semestredetalle/")
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private List<UIEntity> getDetallesSemestrePorEstudio(Long estudioId, Long semestreId)
    {

        ClientResponse response = resource
                .path("semestredetalle/estudio/" + estudioId + "/semestre/" + semestreId)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }
}
