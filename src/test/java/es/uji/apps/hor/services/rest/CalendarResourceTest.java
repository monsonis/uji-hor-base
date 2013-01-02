package es.uji.apps.hor.services.rest;

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

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.StringKeyStringValueIgnoreCaseMultivaluedMap;

import es.uji.apps.hor.builders.AsignaturaBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.EventoDetalleBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.rest.UIEntity;

public class CalendarResourceTest extends AbstractRestTest
{

    static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Long estudioId;
    private final Long cursoId = new Long(1);
    private final Long semestreId = new Long(1);
    private final String grupoId = "A";
    private final String calendariosIds = "1;2;3;4;5;6";

    @Autowired
    private EventosDAO eventosDao;

    @Autowired
    private EstudiosDAO estudiosDao;

    @Before
    @Transactional
    public void creaEventosIniciales() throws Exception
    {
        Estudio estudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Psicologia")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();
        estudioId = estudio.getId();

        Estudio otroEstudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Informática")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();

        Asignatura asignaturaFicticia1 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Asignatura asignaturaFicticia2 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1027")
                .withNombre("Asignatura de Psicologia").withEstudio(estudio).build();

        Asignatura asignaturaOtraTitulacion = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("I001")
                .withNombre("Asignatura de Informatica").withEstudio(otroEstudio).build();

        Semestre semestre = new SemestreBuilder().withSemestre(semestreId)
                .withNombre("Primer semestre").build();

        Long calendarioPracticasId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendarioPR = new CalendarioBuilder().withId(calendarioPracticasId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPracticasId)).build();

        Long calendarioTeoriaId = TipoSubgrupo.TE.getCalendarioAsociado();
        Calendario calendarioTE = new CalendarioBuilder().withId(calendarioTeoriaId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioTeoriaId)).build();

        Evento evento1DeAsignatura1 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 1")
                .withAsignatura(asignaturaFicticia1).withInicioFechaString("10/10/2012 09:00")
                .withFinFechaString("10/10/2012 11:00").withSemestre(semestre).withGrupoId(grupoId)
                .withCalendario(calendarioPR).withDetalleManual(false).build();

        Evento evento2DeAsignatura1 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 2 de asignatura 1")
                .withAsignatura(asignaturaFicticia1).withInicioFechaString("10/10/2012 10:00")
                .withFinFechaString("10/10/2012 12:00").withSemestre(semestre).withGrupoId(grupoId)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

        Evento evento1DeAsignatura2 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 2")
                .withAsignatura(asignaturaFicticia2).withInicioFechaString("11/10/2012 10:00")
                .withFinFechaString("11/10/2012 12:00").withSemestre(semestre).withGrupoId(grupoId)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

        Evento eventoOtraTitulacion = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 3").withAsignatura(asignaturaOtraTitulacion)
                .withInicioFechaString("12/10/2012 13:00").withFinFechaString("12/10/2012 14:00")
                .withSemestre(semestre).withGrupoId(grupoId).withCalendario(calendarioTE)
                .withDetalleManual(false).build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento1DeAsignatura1)
                .withInicioFechaString("10/10/2012 09:00").withFinFechaString("10/10/2012 11:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento1DeAsignatura1)
                .withInicioFechaString("17/10/2012 09:00").withFinFechaString("17/10/2012 11:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento1DeAsignatura1)
                .withInicioFechaString("24/10/2012 09:00").withFinFechaString("24/10/2012 11:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento1DeAsignatura1)
                .withInicioFechaString("31/10/2012 09:00").withFinFechaString("31/10/2012 11:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento2DeAsignatura1)
                .withInicioFechaString("10/10/2012 10:00").withFinFechaString("10/10/2012 12:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento2DeAsignatura1)
                .withInicioFechaString("17/10/2012 10:00").withFinFechaString("17/10/2012 12:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento2DeAsignatura1)
                .withInicioFechaString("24/10/2012 10:00").withFinFechaString("24/10/2012 12:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento1DeAsignatura2)
                .withInicioFechaString("11/10/2012 10:00").withFinFechaString("11/10/2012 12:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento1DeAsignatura2)
                .withInicioFechaString("18/10/2012 10:00").withFinFechaString("18/10/2012 12:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(evento1DeAsignatura2)
                .withInicioFechaString("25/10/2012 10:00").withFinFechaString("25/10/2012 12:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(eventoOtraTitulacion)
                .withInicioFechaString("12/10/2012 13:00").withFinFechaString("12/10/2012 14:00")
                .build();

        new EventoDetalleBuilder(eventosDao).withEvento(eventoOtraTitulacion)
                .withInicioFechaString("19/10/2012 13:00").withFinFechaString("11/10/2012 14:00")
                .build();

    }

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
        String inicio_semana = "2012-10-22";
        String fin_semana = "2012-10-28";

        List<UIEntity> listaEventos = getEventosDetalladosEnRangoDeFechas(inicio_semana, fin_semana);

        assertThat(listaEventos, hasSize(3));
        assertThat(todosEventosEnRango(listaEventos, inicio_semana, fin_semana), is(true));

    }

    @Test
    @Transactional
    public void devuelveEventosDeCuatroSemanas() throws ParseException
    {
        String inicio_periodo = "2012-10-01";
        String fin_periodo = "2012-10-28";

        List<UIEntity> listaEventos = getEventosDetalladosEnRangoDeFechas(inicio_periodo,
                fin_periodo);

        assertThat(listaEventos, hasSize(9));
        assertThat(todosEventosEnRango(listaEventos, inicio_periodo, fin_periodo), is(true));

    }

    @Test
    @Transactional
    public void eliminarUnEventoGenerico() throws ParseException
    {
        String evento_id = "1";

        resource.path("calendario/eventos/generica/" + evento_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);

        List<UIEntity> listaEventos = getListaEventosGenericos();
        assertThat(listaEventos, hasSize(2));
        assertThat(existeEventoGenericoConId(evento_id), is(false));
    }

    @Test
    @Transactional
    public void divideEventoGenerico() throws ParseException
    {
        String evento_id = "1";

        resource.path("calendario/eventos/generica/divide/" + evento_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);

        assertThat(existeDuplicadoDeEventoGenerico(), is(true));

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

    private List<UIEntity> getEventosDetalladosEnRangoDeFechas(String fecha_inicio, String fecha_fin)
    {
        MultivaluedMap<String, String> params = getDefaulQueryParams();
        params.putSingle("startDate", fecha_inicio);
        params.putSingle("endDate", fecha_fin);

        ClientResponse response = resource.path("calendario/eventos/detalle").queryParams(params)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private MultivaluedMap<String, String> getDefaulQueryParams()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("estudioId", String.valueOf(estudioId));
        params.putSingle("cursoId", String.valueOf(cursoId));
        params.putSingle("semestreId", String.valueOf(semestreId));
        params.putSingle("grupoId", grupoId);
        params.putSingle("calendariosIds", calendariosIds);
        return params;
    }

    private List<UIEntity> getListaEventosGenericosWithParams(MultivaluedMap<String, String> params)
    {
        ClientResponse response = resource.path("calendario/eventos/generica").queryParams(params)
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private List<UIEntity> getListaEventosGenericos()
    {
        return getListaEventosGenericosWithParams(getDefaulQueryParams());
    }

    private UIEntity getDatosEventoGenerico(String evento_id)
    {

        List<UIEntity> listaEventos = getListaEventosGenericos();

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

    private boolean existeDuplicadoDeEventoGenerico()
    {
        String id_original = "1";
        String titulo_original = "Evento de prueba 1";
        String fecha_inicio_esperada = "2012-10-10T10:00:00";

        for (UIEntity entity : getListaEventosGenericos())
        {
            String entity_id = entity.get("id");
            String entity_title = entity.get("title");
            String entity_start_str = entity.get("start");

            if (entity_title.equals(titulo_original)
                    && entity_start_str.equals(fecha_inicio_esperada)
                    && !entity_id.equals(id_original))
            {
                return true;
            }
        }

        return false;
    }
}
