package es.uji.apps.hor.services.rest;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.util.Log4jConfigListener;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.UIEntityJSONMessageBodyReader;
import es.uji.commons.rest.UIEntityJSONMessageBodyWriter;
import es.uji.commons.rest.UIEntityListJSONMessageBodyReader;

public class SemestreDetalleResourceTest extends JerseyTest
{

    protected WebResource resource;
    static String packageName = "es.uji.apps.hor.services.rest";

    public SemestreDetalleResourceTest()
    {
        super(new WebAppDescriptor.Builder(packageName)
                .contextParam("contextConfigLocation", "classpath:applicationContext-test.xml")
                .contextParam("log4jConfigLocation", "src/main/webapp/WEB-INF/log4j.properties")
                .contextParam("webAppRootKey", packageName)
                .contextListenerClass(Log4jConfigListener.class)
                .contextListenerClass(ContextLoaderListener.class)
                .requestListenerClass(RequestContextListener.class)
                .servletClass(SpringServlet.class)
                .clientConfig(createClientConfig())
                .initParam("com.sun.jersey.config.property.packages",
                        "es.uji.commons.rest; " + packageName).build());

        this.resource = resource();
    }

    private static ClientConfig createClientConfig()
    {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(UIEntityJSONMessageBodyReader.class);
        config.getClasses().add(UIEntityListJSONMessageBodyReader.class);
        config.getClasses().add(UIEntityJSONMessageBodyWriter.class);

        return config;
    }

    @Test
    public void getTodosSemestreDetalle()
    {
        ClientResponse response = resource.path("semestredetalle/")
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        List<UIEntity> listaSemestres = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertEquals("El servicio devuelve datos", true, listaSemestres.size() > 0);

        Set<String> ids_de_entidades = new HashSet<String>();
        boolean tiene_ids_repetidos = false;
        for (UIEntity entidad : listaSemestres)
        {
            String entidad_id = entidad.get("id");
            if (ids_de_entidades.contains(entidad_id))
            {
                tiene_ids_repetidos = true;
                break;
            }
            else
            {
                ids_de_entidades.add(entidad_id);
            }
        }

        assertEquals("El servicio no devuelve entidades repetidas", false, tiene_ids_repetidos);

    }
}
