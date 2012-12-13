package es.uji.apps.hor.services.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

public class CalendarResourceTest extends JerseyTest
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

    public CalendarResourceTest()
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

    @Test
    public void getEventosDetalleUnDia() throws ParseException
    {
        // Given
        String day_str = "2012-12-11";

        MultivaluedMap<String, String> params = getDefaulQueryParams();
        params.putSingle("startDate", day_str);
        params.putSingle("endDate", day_str);

        // When
        ClientResponse response = resource.path("calendario/eventos/detalle").queryParams(params)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        List<UIEntity> listaEventos = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertTrue("El servicio devuelve datos", listaEventos.size() > 0);
        assertEquals("El servicio devuelve el número de datos correctos", 6, listaEventos.size());

        Date start_date_range = shortDateFormat.parse(day_str);
        Calendar c = Calendar.getInstance();
        c.setTime(start_date_range);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date end_date_range = c.getTime();

        for (UIEntity entidad : listaEventos)
        {
            String start_date_str = entidad.get("start");
            Date start_date = dateFormat.parse(start_date_str);
            String end_date_str = entidad.get("end");
            Date end_date = dateFormat.parse(end_date_str);

            assertTrue("El servicio devuelve clases en el día especificado",
                    start_date_range.before(start_date) && end_date_range.after(end_date));

        }

    }

    @Test
    public void getEventosDetalleUnaSemana() throws ParseException
    {
        // Given
        String day_start_str = "2012-12-10";
        String day_end_str = "2012-12-14";

        // When
        MultivaluedMap<String, String> params = getDefaulQueryParams();
        params.putSingle("startDate", day_start_str);
        params.putSingle("endDate", day_end_str);

        ClientResponse response = resource.path("calendario/eventos/detalle").queryParams(params)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        List<UIEntity> listaEventos = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertTrue("El servicio devuelve datos", listaEventos.size() > 0);
        assertEquals("El servicio devuelve el número de datos correctos", 49, listaEventos.size());

        Date start_date_range = shortDateFormat.parse(day_start_str);
        Date end_date_range = shortDateFormat.parse(day_end_str);
        Calendar c = Calendar.getInstance();
        c.setTime(end_date_range);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        end_date_range = c.getTime();

        for (UIEntity entidad : listaEventos)
        {
            String start_date_str = entidad.get("start");
            Date start_date = dateFormat.parse(start_date_str);
            String end_date_str = entidad.get("end");
            Date end_date = dateFormat.parse(end_date_str);

            assertTrue("El servicio devuelve clases en el día especificado",
                    start_date_range.before(start_date) && end_date_range.after(end_date));

        }

    }

    @Test
    public void getEventosDetalleCuatroSemanas() throws ParseException
    {
        // Given
        String day_start_str = "2012-11-12";
        String day_end_str = "2012-12-09";

        MultivaluedMap<String, String> params = getDefaulQueryParams();
        params.putSingle("startDate", day_start_str);
        params.putSingle("endDate", day_end_str);

        // When
        ClientResponse response = resource.path("calendario/eventos/detalle").queryParams(params)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        List<UIEntity> listaEventos = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertTrue("El servicio devuelve datos", listaEventos.size() > 0);
        assertEquals("El servicio devuelve el número de datos correctos", 193, listaEventos.size());

        Date start_date_range = shortDateFormat.parse(day_start_str);
        Date end_date_range = shortDateFormat.parse(day_end_str);
        Calendar c = Calendar.getInstance();
        c.setTime(end_date_range);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        end_date_range = c.getTime();

        for (UIEntity entidad : listaEventos)
        {
            String start_date_str = entidad.get("start");
            Date start_date = dateFormat.parse(start_date_str);
            String end_date_str = entidad.get("end");
            Date end_date = dateFormat.parse(end_date_str);

            assertTrue("El servicio devuelve clases en el día especificado",
                    start_date_range.before(start_date) && end_date_range.after(end_date));

        }

    }

    @Test
    public void deleteEventoGenerico() throws ParseException
    {
        // Given
        String evento_id = "6595";

        assertTrue("El evento genérico está", existeEventoGenericoConId(evento_id));

        // When
        ClientResponse response = resource.path("calendario/eventos/generica/" + evento_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertTrue("El evento genérico no está", !existeEventoGenericoConId(evento_id));

    }

    private boolean existeEventoGenericoConId(String evento_id)
    {
        return getDatosEventoGenerico(evento_id) != null;
    }

    @Test
    public void divideEventoGenerico() throws ParseException
    {
        // Given
        String evento_id = "6595";

        UIEntity evento_original = getDatosEventoGenerico(evento_id);

        // When
        ClientResponse response = resource.path("calendario/eventos/generica/divide/" + evento_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertTrue("Existe un duplicado", existeDuplicadoDeEventoGenerico(evento_original));

    }

    private boolean existeDuplicadoDeEventoGenerico(UIEntity evento_original)
    {
        String orig_id = evento_original.get("id");
        String orig_title = evento_original.get("title");
        String orig_start_str = evento_original.get("start");
        for (UIEntity entity : getDefaultListaEventosGenericos())
        {
            String entity_id = entity.get("id");
            String entity_title = entity.get("title");
            String entity_start_str = entity.get("start");

            if (entity_title.equals(orig_title) && entity_start_str.equals(orig_start_str)
                    && !entity_id.equals(orig_id))
            {
                return true;
            }
        }

        return false;
    }

}
