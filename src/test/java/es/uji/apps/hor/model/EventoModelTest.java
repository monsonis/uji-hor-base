package es.uji.apps.hor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.SimpleDateFormat;

import org.junit.Test;

import es.uji.apps.hor.builders.AsignaturaBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;

public class EventoModelTest
{

    private SimpleDateFormat formatter;

    public EventoModelTest()
    {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @Test
    public void dividirUnEvento() throws Exception
    {
        Evento eventoOriginal = buildEvento("30/07/2012 09:00", "30/07/2012 10:00");

        Evento eventoNuevo = eventoOriginal.divide();

        assertThat(eventoNuevo.getTitulo(), is(equalTo(eventoOriginal.getTitulo())));
        assertThat(formatter.format(eventoOriginal.getInicio()), is(equalTo("30/07/2012 09:00")));
        assertThat(formatter.format(eventoOriginal.getFin()), is(equalTo("30/07/2012 09:30")));
        assertThat(formatter.format(eventoNuevo.getInicio()), equalTo("30/07/2012 09:30"));
        assertThat(formatter.format(eventoNuevo.getFin()), equalTo("30/07/2012 10:00"));

    }

    private Evento buildEvento(String fechaInicial, String fechaFinal) throws Exception
    {
        Estudio estudio = new EstudioBuilder().withNombre("Grau en Psicologia")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();

        Asignatura asignatura = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(new Long(3)).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Semestre semestre = new SemestreBuilder().withSemestre(new Long(1))
                .withNombre("Primer semestre").build();

        Long calendarioPRId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendario = new CalendarioBuilder().withId(calendarioPRId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPRId)).build();

        return new EventoBuilder().withTitulo("Evento de prueba").withAsignatura(asignatura)
                .withInicio(formatter.parse(fechaInicial)).withFin(formatter.parse(fechaFinal))
                .withSemestre(semestre).withCalendario(calendario).withDetalleManual(false).build();
    }
}
