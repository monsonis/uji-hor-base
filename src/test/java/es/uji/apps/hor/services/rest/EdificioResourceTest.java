package es.uji.apps.hor.services.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.StringKeyStringValueIgnoreCaseMultivaluedMap;

import es.uji.apps.hor.builders.AreaEdificioBuilder;
import es.uji.apps.hor.builders.AulaBuilder;
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.DepartamentoBuilder;
import es.uji.apps.hor.builders.EdificioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.PersonaBuilder;
import es.uji.apps.hor.builders.PlantaEdificioBuilder;
import es.uji.apps.hor.builders.TipoAulaBuilder;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.DepartamentoDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Departamento;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.rest.UIEntity;

public class EdificioResourceTest extends AbstractRestTest
{
    private Long centroId;
    private String edificio;

    @Autowired
    private AulaDAO aulaDAO;

    @Autowired
    private CentroDAO centroDAO;

    @Autowired
    protected PersonaDAO personaDAO;

    @Autowired
    protected DepartamentoDAO departamentoDAO;

    @Before
    @Transactional
    public void creaDatosIniciales()
    {
        Centro centro = new CentroBuilder(centroDAO).withNombre("Centro de prueba").build();
        centroId = centro.getId();

        Departamento departamento = new DepartamentoBuilder(departamentoDAO).withNombre("Departamento1")
                .withCentro(centro).build();

        Persona persona = new PersonaBuilder(personaDAO).withId(new Long(1))
                .withNombre("Persona 1").withEmail("persona@uji.es").withActividadId("Actividad 1")
                .withDepartamento(departamento).withCentroAutorizado(centro).build();

        Edificio edificio1 = new EdificioBuilder().withNombre("Edificio 1").withCentro(centro)
                .build();
        edificio = edificio1.getNombre();

        Edificio edificio2 = new EdificioBuilder().withNombre("Edificio 2").withCentro(centro)
                .build();

        PlantaEdificio plantaEdificio1 = new PlantaEdificioBuilder().withNombre("Planta 1")
                .withEdificio(edificio1).build();
        PlantaEdificio plantaEdificio2 = new PlantaEdificioBuilder().withNombre("Planta 2")
                .withEdificio(edificio2).build();

        AreaEdificio areaEdificio1 = new AreaEdificioBuilder().withNombre("Area 1")
                .withEdificio(edificio1).build();
        AreaEdificio areaEdificio2 = new AreaEdificioBuilder().withNombre("Area 2")
                .withEdificio(edificio2).build();

        TipoAula tipoAula = new TipoAulaBuilder().withNombre("Tipo Aula 1").withEdificio(edificio1)
                .build();
        TipoAula tipoAula2 = new TipoAulaBuilder().withNombre("Tipo Aula 2")
                .withEdificio(edificio2).build();

        new AulaBuilder(aulaDAO).withNombre("Aula 1").withArea(areaEdificio1).withTipo(tipoAula)
                .withPlanta(plantaEdificio1).withEdificio(edificio1).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 2").withArea(areaEdificio1).withTipo(tipoAula)
                .withPlanta(plantaEdificio1).withEdificio(edificio1).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 3").withArea(areaEdificio1).withTipo(tipoAula2)
                .withPlanta(plantaEdificio2).withEdificio(edificio1).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 4").withArea(areaEdificio2).withTipo(tipoAula)
                .withPlanta(plantaEdificio2).withEdificio(edificio2).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 5").withArea(areaEdificio2).withTipo(tipoAula)
                .withPlanta(plantaEdificio2).withEdificio(edificio2).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 6").withArea(areaEdificio2).withTipo(tipoAula2)
                .withPlanta(plantaEdificio2).withEdificio(edificio2).build();

    }

    @Test
    @Transactional
    public void recuperaEdificiosAsignadosAUnCentro()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("centroId", String.valueOf(centroId));

        ClientResponse response = resource.path("edificio").queryParams(params)
                .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        List<UIEntity> edificios = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        assertThat(edificios, hasSize(2));
    }

    @Test
    @Transactional
    public void recuperaPlantasEdificioPorCentroYEdificio()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("centroId", String.valueOf(centroId));
        params.putSingle("edificio", edificio);

        ClientResponse response = resource.path("edificio/planta").queryParams(params)
                .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        List<UIEntity> plantasEdificio = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        assertThat(plantasEdificio, hasSize(2));
    }
}
