package es.uji.apps.hor.services.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.builders.AsignaturaBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.GrupoHorarioBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.dao.GrupoHorarioDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.GrupoHorario;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.rest.UIEntity;

public class CalendarResourceGrupoHorarioTest extends AbstractRestTest
{
    private Estudio estudio;
    private Semestre semestre;
    private final String grupoId = "A";

    private SimpleDateFormat formatter;

    @Autowired
    private GrupoHorarioDAO grupoHorarioDAO;

    @Autowired
    private EventosDAO eventosDAO;

    @Autowired
    private EstudiosDAO estudiosDAO;

    public CalendarResourceGrupoHorarioTest()
    {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @Before
    @Transactional
    public void creaDatosIniciales() throws ParseException, DuracionEventoIncorrectaException
    {
        estudio = new EstudioBuilder(estudiosDAO).withNombre("Grau en Psicologia")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();

        semestre = new SemestreBuilder().withSemestre(new Long(1)).withNombre("Primer semestre")
                .build();

        Asignatura asignatura = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(new Long(1)).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Long calendarioPRId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendario = new CalendarioBuilder().withId(calendarioPRId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPRId)).build();

        GrupoHorario grupoHorario = new GrupoHorarioBuilder(grupoHorarioDAO)
                .withCursoId(new Long(1)).withEstudioId(estudio.getId()).withGrupoId("A")
                .withHoraFin(formatter.parse("07/01/2013 8:00"))
                .withHoraInicio(formatter.parse("07/01/2013 15:00"))
                .withSemestreId(semestre.getSemestre()).build();

        Evento evento1 = new EventoBuilder(eventosDAO)
                .withTitulo("Evento de prueba")
                .withAsignatura(asignatura)
                .withInicioYFin(formatter.parse("07/01/2013 9:00"),
                        formatter.parse("07/01/2013 11:00")).withSemestre(semestre)
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withCalendario(calendario)
                .withDetalleManual(false).build();

        Evento evento2 = new EventoBuilder(eventosDAO)
                .withTitulo("Evento de prueba")
                .withAsignatura(asignatura)
                .withInicioYFin(formatter.parse("07/01/2013 12:00"),
                        formatter.parse("07/01/2013 14:00")).withSemestre(semestre)
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withCalendario(calendario)
                .withDetalleManual(false).build();
    }

    @Test
    @Transactional
    public void elServicioModificaRangoHorario() throws JSONException
    {
        UIEntity entity = modificaRangoHorario(String.valueOf(estudio.getId()),
                String.valueOf(new Long(1)), String.valueOf(semestre.getSemestre()), grupoId,
                "09:00", "14:00");

        String horaInicio = entity.get("horaInicio").split(" ", 2)[1].substring(0, 5);
        assertThat(horaInicio, equalTo("09:00"));
    }

    @Test(expected = ClientHandlerException.class)
    @Transactional
    public void ekServicioIntentaModificarUnRangoHorarioFueraDeLimites() throws JSONException
    {
        UIEntity entity = modificaRangoHorario(String.valueOf(estudio.getId()),
                String.valueOf(new Long(1)), String.valueOf(semestre.getSemestre()), grupoId,
                "10:00", "14:00");

        String horaInicio = entity.get("horaInicio").split(" ", 2)[1].substring(0, 5);
        assertThat(horaInicio, equalTo("10:00"));
    }

    private UIEntity modificaRangoHorario(String estudioId, String cursoId, String semestreId,
            String grupoId, String horaInicio, String horaFin) throws JSONException
    {
        JSONObject entity = new JSONObject();
        entity.put("estudioId", String.valueOf(estudioId));
        entity.put("cursoId", String.valueOf(cursoId));
        entity.put("semestreId", String.valueOf(semestreId));
        entity.put("grupoId", grupoId);
        entity.put("horaInicio", horaInicio);
        entity.put("horaFin", horaFin);

        ClientResponse response = resource.path("calendario/config")
                .accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, entity);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        }).get(0);
    }
}