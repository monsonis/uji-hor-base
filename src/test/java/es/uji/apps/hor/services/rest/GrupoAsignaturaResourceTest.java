package es.uji.apps.hor.services.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.text.ParseException;
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
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.DepartamentoBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.PersonaBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.DepartamentoDAO;
import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Departamento;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.rest.UIEntity;

public class GrupoAsignaturaResourceTest extends AbstractRestTest
{

    private Long estudioId;
    private final Long cursoId = new Long(1);
    private final Long semestreId = new Long(1);
    private final String grupoId = "A";
    private final String calendariosIds = "1;2;3;4;5;6";

    @Autowired
    private EventosDAO eventosDAO;

    @Autowired
    private EstudiosDAO estudiosDAO;

    @Autowired
    protected PersonaDAO personaDAO;

    @Autowired
    protected CentroDAO centroDAO;

    @Autowired
    protected DepartamentoDAO departamentoDAO;

    @Before
    @Transactional
    public void creaEventosIniciales() throws Exception
    {
        TipoEstudio tipoEstudio = new TipoEstudioBuilder().withId("G").withNombre("Grau").build();

        Estudio estudio = new EstudioBuilder(estudiosDAO).withNombre("Grau en Psicologia")
                .withTipoEstudio(tipoEstudio).build();
        estudioId = estudio.getId();

        Centro centro = new CentroBuilder(centroDAO).withNombre("Centro 1").withId(new Long(1))
                .build();
        Departamento departamento = new DepartamentoBuilder(departamentoDAO)
                .withNombre("Departamento1").withCentro(centro).build();

        Persona persona = new PersonaBuilder(personaDAO).withId(new Long(1))
                .withNombre("Persona 1").withEmail("persona@uji.es").withActividadId("Actividad 1")
                .withDepartamento(departamento).withCentroAutorizado(centro)
                .withEstudioAutorizado(estudio).build();

        Asignatura asignatura_ficticia1 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Asignatura asignatura_ficticia2 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1027")
                .withNombre("Asignatura de Psicologia").withEstudio(estudio).build();

        Semestre semestre = new SemestreBuilder().withSemestre(semestreId)
                .withNombre("Primer semestre").build();

        Long calendarioPracticasId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendarioPR = new CalendarioBuilder().withId(calendarioPracticasId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPracticasId)).build();

        Long calendarioTeoriaId = TipoSubgrupo.TE.getCalendarioAsociado();
        Calendario calendarioTE = new CalendarioBuilder().withId(calendarioTeoriaId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioTeoriaId)).build();

        buildEventoSinAsignar(asignatura_ficticia1, semestre, calendarioPR);
        buildEventoSinAsignar(asignatura_ficticia1, semestre, calendarioTE);
        buildEventoSinAsignar(asignatura_ficticia2, semestre, calendarioTE);
        buildEventoAsignado("30/07/2012 09:00", "30/07/2012 11:00", asignatura_ficticia1, semestre,
                calendarioTE);

    }

    @Test
    @Transactional
    public void elServicioDevuelveLasAsignaturasSinAsignar() throws Exception
    {

        List<UIEntity> listaEventosSinAsignar = getDefaultListaEventosSinAsignarDelServicio();

        assertThat(listaEventosSinAsignar, hasSize(3));
    }

    @Test
    @Transactional
    public void elServicioPlanificaUnEventoNoAsignado() throws Exception
    {
        String grupoNoAsignadoId = "1";

        planificaElGrupoNoAsignado(grupoNoAsignadoId, estudioId.toString());

        List<UIEntity> listaEventosSinAsignar = getDefaultListaEventosSinAsignarDelServicio();
        List<UIEntity> listaEventosAsignados = getDefaultListaEventosGenericos();

        assertThat(listaEventosSinAsignar, hasSize(2));
        assertThat(listaEventosAsignados, hasSize(2));
        assertThat(elEventoPlanificadoTieneLaHoraDeInicioCorrecta(grupoNoAsignadoId), is(true));

    }

    private Boolean elEventoPlanificadoTieneLaHoraDeInicioCorrecta(String grupoNoAsignadoId)
            throws Exception
    {
        List<UIEntity> listaEventosAsignados = getDefaultListaEventosGenericos();
        String fechaInicio = "";

        for (UIEntity eventoAsignado : listaEventosAsignados)
        {
            if (eventoAsignado.get("id").equals(grupoNoAsignadoId))
            {
                fechaInicio = eventoAsignado.get("start");
                return esLaHoraPlanificadaPorDefecto(fechaInicio);
            }
        }

        return false;

    }

    private Boolean esLaHoraPlanificadaPorDefecto(String fechaInicioString) throws ParseException
    {
        Date fechaInicio = UIEntityDateFormat.parse(fechaInicioString);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fechaInicio);

        return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                && (calendar.get(Calendar.HOUR_OF_DAY) == 8);
    }

    private List<UIEntity> getDefaultListaEventosSinAsignarDelServicio()
    {
        ClientResponse response = resource.path("grupoAsignatura/sinAsignar")
                .queryParams(buildDefaulQueryParams()).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private List<UIEntity> getDefaultListaEventosGenericos()
    {
        ClientResponse response = resource.path("calendario/eventos/generica")
                .queryParams(buildDefaulQueryParams()).accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private void planificaElGrupoNoAsignado(String idGrupoNoAsignado, String idEstudio)
    {

        resource.path("grupoAsignatura/sinAsignar/" + idGrupoNoAsignado)
                .queryParam("estudioId", idEstudio).accept(MediaType.APPLICATION_JSON_TYPE)
                .put(ClientResponse.class);
    }

    private MultivaluedMap<String, String> buildDefaulQueryParams()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("estudioId", String.valueOf(estudioId));
        params.putSingle("cursoId", String.valueOf(cursoId));
        params.putSingle("semestreId", String.valueOf(semestreId));
        params.putSingle("grupoId", grupoId);
        params.putSingle("calendariosIds", calendariosIds);
        return params;
    }

    private Evento buildEventoAsignado(String fechaInicial, String fechaFinal,
            Asignatura asignatura, Semestre semestre, Calendario calendario) throws Exception
    {

        return new EventoBuilder(eventosDAO).withTitulo("Evento de prueba")
                .withAsignatura(asignatura).withInicioYFinFechaString(fechaInicial, fechaFinal)
                .withSemestre(semestre).withGrupoId(grupoId).withCalendario(calendario)
                .withDetalleManual(false).build();
    }

    private Evento buildEventoSinAsignar(Asignatura asignatura, Semestre semestre,
            Calendario calendario) throws Exception
    {

        return new EventoBuilder(eventosDAO).withTitulo("Evento de prueba")
                .withAsignatura(asignatura).withSemestre(semestre).withCalendario(calendario)
                .withGrupoId(grupoId).withDetalleManual(false).build();
    }

}
