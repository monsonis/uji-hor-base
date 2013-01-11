package es.uji.apps.hor.services.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.MessageFormat;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AssertThrows;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.StringKeyStringValueIgnoreCaseMultivaluedMap;

import es.uji.apps.hor.builders.AreaEdificioBuilder;
import es.uji.apps.hor.builders.AulaBuilder;
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EdificioBuilder;
import es.uji.apps.hor.builders.PlantaEdificioBuilder;
import es.uji.apps.hor.builders.TipoAulaBuilder;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.TipoAula;

public class CentroResourceTest extends AbstractRestTest
{
    private Long centroId;

    @Autowired
    private CentroDAO centroDAO;

    @Autowired
    private AulaDAO aulaDAO;

    @Before
    @Transactional
    public void rellenaDatos()
    {
        Centro centro = new CentroBuilder(centroDAO).withNombre("Centro de prueba").build();
        centroId = centro.getId();
        
        Edificio edificio = new EdificioBuilder().withNombre("Edificio 1").withCentro(centro).build();
        
        PlantaEdificio plantaEdificio = new PlantaEdificioBuilder().withNombre("Planta 1").withEdificio(edificio).build();
        PlantaEdificio plantaEdificio2 = new PlantaEdificioBuilder().withNombre("Planta 2").withEdificio(edificio).build();
        
        AreaEdificio areaEdificio = new AreaEdificioBuilder().withNombre("Area 1").withEdificio(edificio).build();
        AreaEdificio areaEdificio2 = new AreaEdificioBuilder().withNombre("Area 2").withEdificio(edificio).build();
        
        TipoAula tipoAula = new TipoAulaBuilder().withNombre("Tipo Aula 1").withEdificio(edificio).build();
        TipoAula tipoAula2 = new TipoAulaBuilder().withNombre("Tipo Aula 2").withEdificio(edificio).build();
        
        new AulaBuilder(aulaDAO).withNombre("Aula 1").withArea(areaEdificio).withTipo(tipoAula).withPlanta(plantaEdificio).withEdificio(edificio).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 2").withArea(areaEdificio2).withTipo(tipoAula).withPlanta(plantaEdificio2).withEdificio(edificio).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 3").withArea(areaEdificio).withTipo(tipoAula2).withPlanta(plantaEdificio).withEdificio(edificio).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 4").withArea(areaEdificio).withTipo(tipoAula).withPlanta(plantaEdificio).withEdificio(edificio).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 5").withArea(areaEdificio).withTipo(tipoAula).withPlanta(plantaEdificio2).withEdificio(edificio).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 6").withArea(areaEdificio2).withTipo(tipoAula2).withPlanta(plantaEdificio).withEdificio(edificio).build();  
    }

    @Test
    @Transactional
    public void recuperaUnaEstructuraDeCentroEnModoTreeRowSet()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();

        ClientResponse response = resource.path(MessageFormat.format("centro/{0,number,#}/tree", centroId))
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        assertThat(response.getStatus(), is(200));
    }

}
