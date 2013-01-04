package es.uji.apps.hor.services.rest;

import static es.uji.apps.hor.matchers.UIEntityHasTitle.hasTitle;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
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
import es.uji.apps.hor.builders.AulaBuilder;
import es.uji.apps.hor.builders.AulaPlanificacionBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.EventoDetalleBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.rest.UIEntity;

public class CalendarResourceTest extends AbstractRestTest
{

    static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Long estudioId;
    private Long otroEstudioId;
    private final Long cursoId = new Long(1);
    private final Long semestreId = new Long(1);
    private final String grupoId = "A";
    private final String calendariosIds = "1;2;3;4;5;6";
    private Long aulaPlanificacionId;
    private Long aulaPlanificacionSinEstudioId;

    @Autowired
    private EventosDAO eventosDao;

    @Autowired
    private EstudiosDAO estudiosDao;

    @Autowired
    private CentroDAO centroDao;

    @Autowired
    private AulaDAO aulaDao;

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
                .withGrupoId(grupoId).withSubgrupoId(new Long(1))
                .withFinFechaString("10/10/2012 11:00").withSemestre(semestre)
                .withCalendario(calendarioPR).withDetalleManual(false).build();

        Evento evento2DeAsignatura1 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 2 de asignatura 1")
                .withAsignatura(asignaturaFicticia1).withInicioFechaString("10/10/2012 10:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1))
                .withFinFechaString("10/10/2012 12:00").withSemestre(semestre)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

        Evento evento1DeAsignatura2 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 2")
                .withAsignatura(asignaturaFicticia2).withInicioFechaString("11/10/2012 10:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1))
                .withFinFechaString("11/10/2012 12:00").withSemestre(semestre).withGrupoId(grupoId)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

        Evento eventoOtraTitulacion = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 3").withAsignatura(asignaturaOtraTitulacion)
                .withInicioFechaString("12/10/2012 13:00").withFinFechaString("12/10/2012 14:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

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

        creaAulasParaAsignarAEventos();
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

    private boolean existeDuplicadoDeEventoGenerico() throws Exception
    {
        String id_original = "1";
        String titulo_original = "PS1026 PR1";
        int hora_inicio_esperada = 10;
        int minuto_inicio_esperado = 0;
        int segundo_inicio_esperado = 0;
        int dia_esperado = Calendar.WEDNESDAY;

        Calendar cal = Calendar.getInstance();

        for (UIEntity entity : getListaEventosGenericos())
        {
            String entity_id = entity.get("id");
            String entity_title = entity.get("title").replace("\"", "");
            String entity_start_str = entity.get("start");

            Date entity_start_date = UIEntityDateFormat.parse(entity_start_str);
            cal.setTime(entity_start_date);

            if (entity_title.equals(titulo_original)
                    && cal.get(Calendar.HOUR_OF_DAY) == hora_inicio_esperada
                    && cal.get(Calendar.MINUTE) == minuto_inicio_esperado
                    && cal.get(Calendar.SECOND) == segundo_inicio_esperado
                    && cal.get(Calendar.DAY_OF_WEEK) == dia_esperado
                    && !entity_id.equals(id_original))
            {
                return true;
            }
        }

        return false;
    }

    @Transactional
    private void creaAulasParaAsignarAEventos()
    {
        Estudio otroEstudio = new EstudioBuilder(estudiosDao).withNombre("Estudio de Prueba")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();
        otroEstudioId = otroEstudio.getId();

        Centro centro = new CentroBuilder(centroDao).withNombre("Centro de Prueba").build();

        Aula aula1 = new AulaBuilder(aulaDao).withArea("Área 1").withCentro(centro)
                .withCodigo("AUL1").withEdificio("Edificio 1").withNombre("Aula 1").withPlanta("1")
                .withPlazas(new Long(100)).withTipo("1").build();

        Aula aula2 = new AulaBuilder(aulaDao).withArea("Área 2").withCentro(centro)
                .withCodigo("AUL2").withEdificio("Edificio 1").withNombre("Aula 2").withPlanta("1")
                .withPlazas(new Long(100)).withTipo("2").build();

        AulaPlanificacion aulaPlanificacion = new AulaPlanificacionBuilder(aulaDao)
                .withAulaId(aula1.getId()).withEstudioId(estudioId).withSemestreId(semestreId)
                .build();

        aulaPlanificacionId = aulaPlanificacion.getId();

        AulaPlanificacion aulaPlanificacionSinEstudio = new AulaPlanificacionBuilder(aulaDao)
                .withAulaId(aula2.getId()).withEstudioId(otroEstudioId).withSemestreId(semestreId)
                .build();

        aulaPlanificacionSinEstudioId = aulaPlanificacionSinEstudio.getId();
    }

    @Test
    @Transactional
    public void asignaUnAulaAUnEvento()
    {
        this.getListaEventosGenericos();
        String eventoId = this.getListaEventosGenericos().get(0).get("id");

        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("aulaId", String.valueOf(aulaPlanificacionId));
        params.putSingle("tipoAccion", "F");

        resource.path("calendario/eventos/aula/evento/" + eventoId)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        assertThat(tieneAulaAsignada(eventoId), is(true));
    }

    @Test
    @Transactional
    public void asignaUnAulaAUnEventoConDistintoEstudio()
    {
        this.getListaEventosGenericos();
        String eventoId = this.getListaEventosGenericos().get(1).get("id");

        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("aulaId", String.valueOf(aulaPlanificacionSinEstudioId));
        params.putSingle("tipoAccion", "F");

        ClientResponse response = resource.path("calendario/eventos/aula/evento/" + eventoId)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        String message = response.getEntity(String.class);

        assertThat(message,
                containsString("L'aula seleccinada no està assignada a la titulació del event"));
    }

    @Test
    @Transactional
    public void asignaAulaAUnEventoYPropagaAGruposDivididos() throws Exception
    {
        String evento_id = "1";

        resource.path("calendario/eventos/generica/divide/" + evento_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);

        assertThat(existeDuplicadoDeEventoGenerico(), is(true));

        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("aulaId", String.valueOf(aulaPlanificacionId));
        params.putSingle("tipoAccion", "T");

        resource.path("calendario/eventos/aula/evento/" + evento_id)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        UIEntity duplicado = getEventoDuplicado();

        assertThat(coincidenAulasEventoDividido(evento_id, duplicado.get("id")), is(true));
    }

    private boolean tieneAulaAsignada(String eventoId)
    {
        UIEntity entity = getDatosEventoGenerico(eventoId);

        return entity.get("aula_planificacion_id") != null;
    }

    private boolean coincidenAulasEventoDividido(String originalId, String duplicadoId)
    {
        UIEntity entityO = getDatosEventoGenerico(originalId);
        UIEntity entityD = getDatosEventoGenerico(duplicadoId);

        return entityD.get("aula_planificacion_id").equals(entityO.get("aula_planificacion_id"));
    }

    private UIEntity getEventoDuplicado() throws ParseException
    {
        String id_original = "1";
        String titulo_original = "PS1026 PR1";
        int hora_inicio_esperada = 10;
        int minuto_inicio_esperado = 0;
        int segundo_inicio_esperado = 0;
        int dia_esperado = Calendar.WEDNESDAY;

        Calendar cal = Calendar.getInstance();

        for (UIEntity entity : getListaEventosGenericos())
        {
            String entity_id = entity.get("id").trim();
            String entity_title = entity.get("title").replace("\"", "").trim();
            String entity_start_str = entity.get("start").trim();

            Date entity_start_date = UIEntityDateFormat.parse(entity_start_str);
            cal.setTime(entity_start_date);

            if (entity_title.equals(titulo_original)
                    && cal.get(Calendar.HOUR_OF_DAY) == hora_inicio_esperada
                    && cal.get(Calendar.MINUTE) == minuto_inicio_esperado
                    && cal.get(Calendar.SECOND) == segundo_inicio_esperado
                    && cal.get(Calendar.DAY_OF_WEEK) == dia_esperado
                    && !entity_id.equals(id_original))
            {
                return entity;
            }
        }

        return null;
    }
}
