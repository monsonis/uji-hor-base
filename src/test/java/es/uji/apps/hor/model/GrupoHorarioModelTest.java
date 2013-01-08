package es.uji.apps.hor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.RangoHorarioFueradeLimites;
import es.uji.apps.hor.builders.AsignaturaBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.GrupoHorarioBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;

public class GrupoHorarioModelTest
{
    private Estudio estudio;
    private Semestre semestre;

    private SimpleDateFormat formatter;

    public GrupoHorarioModelTest()
    {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @Test
    public void modificaRangoHorarioTest() throws ParseException, RangoHorarioFueradeLimites,
            DuracionEventoIncorrectaException
    {
        GrupoHorario grupoHorario = buildGrupoHorario("07/01/2013 8:00", "07/01/2013 15:00");

        List<Evento> eventos = new ArrayList<Evento>();
        eventos.add(buildEvento("07/01/2013 9:00", "07/01/2013 11:00"));
        eventos.add(buildEvento("07/01/2013 12:00", "07/01/2013 14:00"));

        grupoHorario.actualizaRangoHorario(formatter.parse("07/01/2013 9:00"),
                formatter.parse("07/01/2013 14:00"));
        grupoHorario.compruebaValidezRangoHorario(eventos);

        assertThat(grupoHorario.getHoraInicio(), is(equalTo(formatter.parse("07/01/2013 9:00"))));
    }

    @Test(expected = RangoHorarioFueradeLimites.class)
    public void modificaRangoHorarioConEventosFueraDelRangoTest() throws ParseException,
            RangoHorarioFueradeLimites, DuracionEventoIncorrectaException
    {
        GrupoHorario grupoHorario = buildGrupoHorario("07/01/2013 8:00", "07/01/2013 15:00");

        List<Evento> eventos = new ArrayList<Evento>();
        eventos.add(buildEvento("07/01/2013 9:00", "07/01/2013 11:00"));
        eventos.add(buildEvento("07/01/2013 12:00", "07/01/2013 14:00"));

        grupoHorario.actualizaRangoHorario(formatter.parse("07/01/2013 10:00"),
                formatter.parse("07/01/2013 14:00"));
        grupoHorario.compruebaValidezRangoHorario(eventos);
    }

    private GrupoHorario buildGrupoHorario(String horaInicio, String horaFin) throws ParseException
    {
        estudio = new EstudioBuilder().withNombre("Grau en Psicologia").withTipoEstudio("Grau")
                .withTipoEstudioId("G").build();

        semestre = new SemestreBuilder().withSemestre(new Long(1)).withNombre("Primer semestre")
                .build();

        return new GrupoHorarioBuilder().withCursoId(new Long(1)).withEstudioId(estudio.getId())
                .withGrupoId("A").withHoraFin(formatter.parse(horaFin))
                .withHoraInicio(formatter.parse(horaInicio)).withSemestreId(semestre.getSemestre())
                .build();
    }

    private Evento buildEvento(String fechaInicio, String fechaFin) throws ParseException,
            DuracionEventoIncorrectaException
    {
        Asignatura asignatura = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(new Long(3)).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Long calendarioPRId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendario = new CalendarioBuilder().withId(calendarioPRId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPRId)).build();

        return new EventoBuilder().withTitulo("Evento de prueba").withAsignatura(asignatura)
                .withInicioYFin(formatter.parse(fechaInicio), formatter.parse(fechaFin))
                .withSemestre(semestre).withCalendario(calendario).withDetalleManual(false).build();
    }
}
