package es.uji.apps.hor.services.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.StringKeyStringValueIgnoreCaseMultivaluedMap;

import es.uji.commons.rest.UIEntity;

public class RangoHorarioResourceTest extends AbstractRestTest
{

    @Test
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
        params.putSingle("estudioId", String.valueOf(1));
        params.putSingle("cursoId", String.valueOf(1));
        params.putSingle("semestreId", String.valueOf(1));
        params.putSingle("grupoId", "A");
        return params;
    }
}
