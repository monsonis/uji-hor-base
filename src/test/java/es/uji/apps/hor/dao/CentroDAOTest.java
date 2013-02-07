package es.uji.apps.hor.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.builders.AreaEdificioBuilder;
import es.uji.apps.hor.builders.AulaBuilder;
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EdificioBuilder;
import es.uji.apps.hor.builders.PlantaEdificioBuilder;
import es.uji.apps.hor.builders.TipoAulaBuilder;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = false)
public class CentroDAOTest
{
    @Autowired
    private CentroDAO centroDAO;

    @Autowired
    private AulaDAO aulaDAO;
    private Long centroId;
    
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
        
        new AulaBuilder(aulaDAO).withNombre("Aula 1").withArea(areaEdificio).withTipo(tipoAula).withPlanta(plantaEdificio).withEdificio(edificio).withCentro(centro).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 2").withArea(areaEdificio2).withTipo(tipoAula).withPlanta(plantaEdificio2).withEdificio(edificio).withCentro(centro).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 3").withArea(areaEdificio).withTipo(tipoAula2).withPlanta(plantaEdificio).withEdificio(edificio).withCentro(centro).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 4").withArea(areaEdificio).withTipo(tipoAula).withPlanta(plantaEdificio).withEdificio(edificio).withCentro(centro).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 5").withArea(areaEdificio).withTipo(tipoAula).withPlanta(plantaEdificio2).withEdificio(edificio).withCentro(centro).build();
        new AulaBuilder(aulaDAO).withNombre("Aula 6").withArea(areaEdificio2).withTipo(tipoAula2).withPlanta(plantaEdificio).withEdificio(edificio).withCentro(centro).build();  
    }

    @Test
    @Transactional
    public void getCentroByIdTest() throws RegistroNoEncontradoException
    {
        Centro centro = centroDAO.getCentroById(centroId);
        
        assertThat(numeroDeEdificiosEnElCentro(centro), is(1));
        assertThat(numeroDeAulasEnElCentro(centro), is(6));
    }

    private Integer numeroDeAulasEnElCentro(Centro centro)
    {
        Integer total = 0;
        
        for (Edificio edificio: centro.getEdificios())
        {
            for (TipoAula tipo: edificio.getTiposAulas()) {
                total += tipo.getAulas().size();
            }
        }
        
        return total;
    }

    private Integer numeroDeEdificiosEnElCentro(Centro centro)
    {
        return centro.getEdificios().size();
    }

}
