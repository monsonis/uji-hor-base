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

import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.DepartamentoBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.PersonaBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.DepartamentoDAO;
import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Departamento;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.rest.UIEntity;

public class RangoHorarioResourceTest extends AbstractRestTest
{

    @Autowired
    protected EstudiosDAO estudiosDAO;

    @Autowired
    protected PersonaDAO personaDAO;

    @Autowired
    protected CentroDAO centroDAO;

    @Autowired
    protected DepartamentoDAO departamentoDAO;

    protected Long estudioId;

    @Before
    @Transactional
    public void creaDatosIniciales() throws Exception
    {
        TipoEstudio tipoEstudio = new TipoEstudioBuilder().withId("G").withNombre("Grau").build();

        Centro centro = new CentroBuilder(centroDAO).withNombre("Centro 1").withId(new Long(1)).build();
        Departamento departamento = new DepartamentoBuilder(departamentoDAO).withNombre("Departamento1")
                .withCentro(centro).build();

        Estudio estudio = new EstudioBuilder(estudiosDAO).withNombre("Grau en Psicologia")
                .withTipoEstudio(tipoEstudio).build();
        estudioId = estudio.getId();

        Persona persona = new PersonaBuilder(personaDAO).withId(new Long(1))
                .withNombre("Persona 1").withEmail("persona@uji.es").withActividadId("Actividad 1")
                .withDepartamento(departamento).withCentroAutorizado(centro).withEstudioAutorizado(estudio).build();
    }
    
    @Test
    @Transactional
    public void elServicioDevuelveUnRangoPorDefecto()
    {
        List<UIEntity> listaSemestres = getListadoConfiguracion();

        assertThat(listaSemestres, hasSize(1));
    }

    private List<UIEntity> getListadoConfiguracion()
    {
        ClientResponse response = resource.path("calendario/config")
                .queryParams(getDefaulQueryParams()).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    protected MultivaluedMap<String, String> getDefaulQueryParams()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("estudioId", String.valueOf(estudioId));
        params.putSingle("cursoId", String.valueOf(1));
        params.putSingle("semestreId", String.valueOf(1));
        params.putSingle("grupoId", "A");
        return params;
    }
}
