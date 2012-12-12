package es.uji.apps.hor.services.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

public class CalendarResourceTest extends JerseyTest
{

    protected WebResource resource;
    static String packageName = "es.uji.apps.hor.services.rest";

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("\"yyyy-MM-dd'T'HH:mm:ss\"");
    static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    @Test
    public void getEventosDetalleUnDia() throws ParseException
    {
        // Given
        String day_str = "2012-12-11";
        String estudio_id = "210";
        String curso_id = "1";
        String semestre_id = "1";
        String grupo_id = "A";
        String calendarios_ids = "1%3B2%3B3%3B4%3B5%3B6";

        // When
        ClientResponse response = resource.path("calendario/eventos/detalle")
                .queryParam("estudioId", estudio_id).queryParam("cursoId", curso_id)
                .queryParam("semestreId", semestre_id).queryParam("grupoId", grupo_id)
                .queryParam("startDate", day_str).queryParam("endDate", day_str)
                .queryParam("calendariosIds", calendarios_ids)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        List<UIEntity> listaEventos = response.getEntity(new GenericType<List<UIEntity>>()
        {
        });

        // Then
        assertEquals("El servicio es accesible", Status.OK.getStatusCode(), response.getStatus());
        assertEquals("El servicio devuelve datos", true, listaEventos.size() > 0);

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
}
