package es.uji.apps.hor.services.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
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

public abstract class AbstractCalendarResourceTest extends AbstractRestTest
{
    protected Long estudioId;
    protected Long otroEstudioId;
    protected final Long cursoId = new Long(1);
    protected final Long semestreId = new Long(1);
    protected final String grupoId = "A";
    protected final String calendariosIds = "1;2;3;4;5;6";

    @Autowired
    protected EventosDAO eventosDao;

    @Autowired
    protected EstudiosDAO estudiosDao;
    protected Asignatura asignaturaFicticia1;
    protected Semestre semestre;
    protected Calendario calendarioPR;

    @Before
    @Transactional
    public void creaDatosIniciales() throws Exception
    {
        creaEventosIniciales();
    }

    protected void creaEventosIniciales() throws Exception
    {
        Estudio estudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Psicologia")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();
        estudioId = estudio.getId();

        Estudio otroEstudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Informática")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();

        asignaturaFicticia1 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Asignatura asignaturaFicticia2 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1027")
                .withNombre("Asignatura de Psicologia").withEstudio(estudio).build();

        Asignatura asignaturaOtraTitulacion = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("I001")
                .withNombre("Asignatura de Informatica").withEstudio(otroEstudio).build();

        semestre = new SemestreBuilder().withSemestre(semestreId).withNombre("Primer semestre")
                .build();

        Long calendarioPracticasId = TipoSubgrupo.PR.getCalendarioAsociado();
        calendarioPR = new CalendarioBuilder().withId(calendarioPracticasId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPracticasId)).build();

        Long calendarioTeoriaId = TipoSubgrupo.TE.getCalendarioAsociado();
        Calendario calendarioTE = new CalendarioBuilder().withId(calendarioTeoriaId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioTeoriaId)).build();

        Evento evento1DeAsignatura1 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 09:00", "10/10/2012 11:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withCalendario(calendarioPR).withDetalleManual(false).build();

        Evento evento2DeAsignatura1 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 2 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 10:00", "10/10/2012 12:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

        Evento evento1DeAsignatura2 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 2")
                .withAsignatura(asignaturaFicticia2)
                .withInicioYFinFechaString("11/10/2012 10:00", "11/10/2012 12:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withGrupoId(grupoId).withCalendario(calendarioTE).withDetalleManual(false).build();

        Evento eventoOtraTitulacion = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 3").withAsignatura(asignaturaOtraTitulacion)
                .withInicioYFinFechaString("12/10/2012 13:00", "12/10/2012 14:00")
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
                .withInicioFechaString("19/10/2012 13:00").withFinFechaString("19/10/2012 14:00")
                .build();

    }

    protected List<UIEntity> getEventosDetalladosEnRangoDeFechas(String fecha_inicio,
            String fecha_fin)
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

    protected MultivaluedMap<String, String> getDefaulQueryParams()
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

    protected List<UIEntity> getListaEventosGenericos()
    {
        return getListaEventosGenericosWithParams(getDefaulQueryParams());
    }

    protected UIEntity getDatosEventoGenerico(String evento_id)
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

    protected boolean existeDuplicadoDeEventoGenerico() throws Exception
    {
        String id_original = "1";
        int hora_inicio_esperada = 10;
        int minuto_inicio_esperado = 0;
        int segundo_inicio_esperado = 0;
        int dia_esperado = Calendar.WEDNESDAY;

        Calendar cal = Calendar.getInstance();

        for (UIEntity entity : getListaEventosGenericos())
        {
            String entity_id = entity.get("id");
            String entity_start_str = entity.get("start");

            Date entity_start_date = UIEntityDateFormat.parse(entity_start_str);
            cal.setTime(entity_start_date);

            if (cal.get(Calendar.HOUR_OF_DAY) == hora_inicio_esperada
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

}
