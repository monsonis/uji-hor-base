package es.uji.apps.hor.services.rest;

import static es.uji.apps.hor.matchers.UIEntityHasTitle.hasTitle;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.core.util.StringKeyStringValueIgnoreCaseMultivaluedMap;

import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.model.Evento;
import es.uji.commons.rest.UIEntity;

public class CalendarResourceTest extends AbstractCalendarResourceTest
{

    static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    @Transactional
    public void devuelveLosEventosDeUnDia() throws Exception
    {
        String fecha = "2012-10-24";
        List<UIEntity> listaEventos = getEventosDetalladosEnRangoDeFechas(fecha, fecha);

        assertThat(listaEventos, hasSize(2));
        assertThat(todosEventosEnRango(listaEventos, fecha, fecha), is(true));
    }

    @Test
    @Transactional
    public void devuelveLosEventosDeUnaSemana() throws ParseException
    {
        // Given
        String inicioSemana = "2012-10-22";
        String finSemana = "2012-10-28";

        List<UIEntity> listaEventos = getEventosDetalladosEnRangoDeFechas(inicioSemana, finSemana);

        assertThat(listaEventos, hasSize(3));
        assertThat(todosEventosEnRango(listaEventos, inicioSemana, finSemana), is(true));

    }

    @Test
    @Transactional
    public void devuelveEventosDeCuatroSemanas() throws ParseException
    {
        String inicioPeriodo = "2012-10-01";
        String finPeriodo = "2012-10-28";

        List<UIEntity> listaEventos = getEventosDetalladosEnRangoDeFechas(inicioPeriodo, finPeriodo);

        assertThat(listaEventos, hasSize(9));
        assertThat(todosEventosEnRango(listaEventos, inicioPeriodo, finPeriodo), is(true));

    }

    @Test
    @Transactional
    public void eliminarElUltimoEventoDeUnGrupo() throws ParseException
    {
        String eventoId = "1";

        resource.path("calendario/eventos/generica/" + eventoId)
                .accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);

        List<UIEntity> listaEventos = getListaEventosGenericos();
        assertThat(listaEventos, hasSize(2));
        assertThat(existeEventoGenericoConId(eventoId), is(false));
        assertThat(elEventoEstaDesasignado(eventoId), is(true));
    }

    @Test
    @Transactional
    public void eliminarEventoDeUnGrupoConMasEventos() throws Exception
    {
        Evento eventoDeGrupoExistente = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("11/10/2012 09:00", "11/10/2012 11:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withCalendario(calendarioPR).withDetalleManual(false).build();

        String eventoId = eventoDeGrupoExistente.getId().toString();

        resource.path("calendario/eventos/generica/" + eventoId)
                .accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);

        List<UIEntity> listaEventos = getListaEventosGenericos();
        assertThat(listaEventos, hasSize(3));
        assertThat(existeEventoGenericoConId(eventoId), is(false));
        assertThat(elEventoEstaDesasignado(eventoId), is(false));

    }

    @Test
    @Transactional
    public void divideEventoGenerico() throws Exception
    {
        String evento_id = "1";

        resource.path("calendario/eventos/generica/divide/" + evento_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);

        assertThat(existeDuplicadoDeEventoGenerico(), is(true));

    }

    @Test
    @Transactional
    public void devuelveEventosConTitulo() throws Exception
    {

        List<UIEntity> lista_eventos = getListaEventosGenericos();

        assertThat(lista_eventos, everyItem(hasTitle()));

    }

    @Test
    @Transactional
    public void cambiaElDiaDeUnEvento() throws Exception
    {
        String eventoId = "1";
        String fechaInicio = "2012-10-26T09:00:00";
        String fechaFin = "2012-10-26T11:00:00";
        actualizaEventoGenericoConFechas(eventoId, fechaInicio, fechaFin);

        assertThat(diaDelEvento(eventoId), is(Calendar.FRIDAY));
    }

    @Test
    @Transactional
    public void cambiaDuracionDeUnEvento() throws Exception
    {
        String eventoId = "1";
        String fechaInicio = "2012-10-10T09:00:00";
        String fechaFin = "2012-10-10T12:00:00";
        actualizaEventoGenericoConFechas(eventoId, fechaInicio, fechaFin);

        assertThat(duracionHorasDelEvento(eventoId), is(3));
    }

    @Test
    @Transactional
    public void cambiaHoraInicioDeUnEvento() throws Exception
    {
        String eventoId = "1";
        String fechaInicio = "2012-10-10T10:00:00";
        String fechaFin = "2012-10-10T11:00:00";
        actualizaEventoGenericoConFechas(eventoId, fechaInicio, fechaFin);

        assertThat(horaInicioDelEvento(eventoId), is(10));
    }

    private int horaInicioDelEvento(String eventoId) throws ParseException
    {
        UIEntity entityActualizada = getDatosEventoGenerico(eventoId);
        String fechaInicioString = entityActualizada.get("start");
        Date fechaInicio = UIEntityDateFormat.parse(fechaInicioString);

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(fechaInicio);

        return calInicio.get(Calendar.HOUR_OF_DAY);
    }

    private int duracionHorasDelEvento(String eventoId) throws ParseException
    {
        UIEntity entityActualizada = getDatosEventoGenerico(eventoId);
        String fechaInicioString = entityActualizada.get("start");
        String fechaFinString = entityActualizada.get("end");
        Date fechaInicio = UIEntityDateFormat.parse(fechaInicioString);
        Date fechaFin = UIEntityDateFormat.parse(fechaFinString);

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(fechaInicio);
        Calendar calFin = Calendar.getInstance();
        calFin.setTime(fechaFin);
        return calFin.get(Calendar.HOUR_OF_DAY) - calInicio.get(Calendar.HOUR_OF_DAY);
    }

    private int diaDelEvento(String eventoId) throws ParseException
    {
        UIEntity entityActualizada = getDatosEventoGenerico(eventoId);
        String fechaInicioString = entityActualizada.get("start");
        Date fechaInicio = UIEntityDateFormat.parse(fechaInicioString);

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    private void actualizaEventoGenericoConFechas(String idEvento, String fechaInicio,
            String fechaFin) throws NumberFormatException, UniformInterfaceException, JSONException
    {

        JSONObject entity = new JSONObject();
        entity.put("id", idEvento);
        entity.put("posteo_detalle", "0");

        entity.put("start", fechaInicio);
        entity.put("end", fechaFin);
        resource.path("calendario/eventos/generica/" + idEvento).accept(MediaType.APPLICATION_JSON)
                .put(entity);
    }

    private Boolean todosEventosEnRango(List<UIEntity> listaEventos, String fecha_inicio,
            String fecha_fin) throws ParseException
    {
        Date start_date_range = shortDateFormat.parse(fecha_inicio);
        Calendar c = Calendar.getInstance();
        c.setTime(start_date_range);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date end_date_range = c.getTime();

        for (UIEntity entidad : listaEventos)
        {
            String start_date_str = entidad.get("start");
            Date start_date = UIEntityDateFormat.parse(start_date_str);
            String end_date_str = entidad.get("end");
            Date end_date = UIEntityDateFormat.parse(end_date_str);

            if (!start_date_range.before(start_date) && end_date_range.after(end_date))
            {
                return false;
            }

        }
        return true;
    }

    private boolean existeEventoGenericoConId(String eventoId)
    {
        return getDatosEventoGenerico(eventoId) != null;
    }

    private Boolean elEventoEstaDesasignado(String eventoId)
    {
        for (UIEntity entity : getListaEventosSinAsignar())
        {
            String id = entity.get("id");
            if (id.equals(eventoId))
            {
                return true;
            }
        }
        return false;
    }

    private List<UIEntity> getListaEventosSinAsignar()
    {
        ClientResponse response = resource.path("grupoAsignatura/sinAsignar")
                .queryParams(buildQueryParamsEventosSinAsignar())
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private MultivaluedMap<String, String> buildQueryParamsEventosSinAsignar()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("estudioId", String.valueOf(estudioId));
        params.putSingle("cursoId", String.valueOf(cursoId));
        params.putSingle("semestreId", String.valueOf(semestreId));
        params.putSingle("grupoId", grupoId);
        params.putSingle("calendariosIds", calendariosIds);
        return params;
    }

}
