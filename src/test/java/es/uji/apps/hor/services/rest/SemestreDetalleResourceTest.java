package es.uji.apps.hor.services.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import es.uji.commons.rest.UIEntity;

public class SemestreDetalleResourceTest extends AbstractRestTest
{

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
}
