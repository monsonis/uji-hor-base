package es.uji.apps.hor.services.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

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
import com.sun.jersey.core.util.StringKeyStringValueIgnoreCaseMultivaluedMap;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.UIEntityJSONMessageBodyReader;
import es.uji.commons.rest.UIEntityJSONMessageBodyWriter;
import es.uji.commons.rest.UIEntityListJSONMessageBodyReader;

public class GrupoAsignaturaResourceTest extends JerseyTest
{

    protected WebResource resource;
    static String packageName = "es.uji.apps.hor.services.rest";

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("\"yyyy-MM-dd'T'HH:mm:ss\"");
    static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String estudio_id = "210";
    String curso_id = "1";
    String semestre_id = "1";
    String grupo_id = "A";
    String calendarios_ids = "1;2;3;4;5;6";

    public GrupoAsignaturaResourceTest()
    {
        super(new WebAppDescriptor.Builder(packageName)
                .contextParam("contextConfigLocation", "classpath:applicationContext-test.xml")
                // .contextParam("contextConfigLocation", "classpath:applicationContext.xml")
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

    private MultivaluedMap<String, String> getDefaulQueryParams()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("estudioId", estudio_id);
        params.putSingle("cursoId", curso_id);
        params.putSingle("semestreId", semestre_id);
        params.putSingle("grupoId", grupo_id);
        params.putSingle("calendariosIds", calendarios_ids);
        return params;
    }

    private List<UIEntity> getDefaultListaEventosGenericosWithParams(
            MultivaluedMap<String, String> params)
    {
        ClientResponse response = resource.path("calendario/eventos/generica").queryParams(params)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private List<UIEntity> getDefaultListaEventosGenericos()
    {
        return getDefaultListaEventosGenericosWithParams(getDefaulQueryParams());
    }

    private UIEntity getDatosEventoGenerico(String evento_id)
    {

        List<UIEntity> listaEventos = getDefaultListaEventosGenericos();

        for (UIEntity entidad : listaEventos)
        {
            String id = entidad.get("id");
            if (id.equals(evento_id))
            {
                return entidad;
            }
        }

        return null;
    }

    private boolean existeEventoGenericoConId(String evento_id)
    {
        return getDatosEventoGenerico(evento_id) != null;
    }

    @Test
    public void getGruposAsignaturasSinAsignar() throws ParseException
    {
        // Given

        // When
        ClientResponse response = resource.path("grupoAsignatura/sinAsignar")
                .queryParams(getDefaulQueryParams()).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        List<UIEntity> listaGrupos = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertTrue("El servicio devuelve datos", listaGrupos.size() > 0);
        assertEquals("El servicio devuelve el número de datos correctos", 9, listaGrupos.size());

    }

    @Test
    public void updateGruposAsignaturasSinAsignar() throws ParseException
    {
        // Given
        String sin_asignar_id = "6571";
        assertTrue("El evento genérico NO está", !existeEventoGenericoConId(sin_asignar_id));

        // When
        ClientResponse response = resource.path("grupoAsignatura/sinAsignar/" + sin_asignar_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class);

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertTrue("El evento genérico está", existeEventoGenericoConId(sin_asignar_id));

    }

}
