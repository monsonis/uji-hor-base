package es.uji.apps.hor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.builders.AsignaturaBuilder;
import es.uji.apps.hor.builders.AulaPlanificacionBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.EventoDetalleBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;

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

    @Test(expected = EventoNoDivisibleException.class)
    public void divideClaseDeMenosDeUnaHoraTest() throws Exception
    {
        Evento eventoOriginal = buildEvento("30/07/2012 09:00", "30/07/2012 09:30");
        eventoOriginal.divide();
    }

    @Test
    public void modificaDiaYHoraEventoTest() throws Exception
    {
        Evento evento = buildEvento("30/07/2012 09:00", "30/07/2012 10:00");

        Date inicio = formatter.parse("20/12/2012 10:00");
        Date fin = formatter.parse("20/12/2012 12:00");

        evento.setFechaInicioYFin(inicio, fin);

        assertThat(evento.getInicio(), equalTo(inicio));
        assertThat(evento.getFin(), equalTo(fin));
        assertThat(evento.getDia(), equalTo(4));
    }

    @Test(expected = DuracionEventoIncorrectaException.class)
    public void modificaFechasDiaFinDistinto() throws Exception
    {
        Evento evento = buildEvento("30/07/2012 09:00", "30/07/2012 10:00");

        Date fin = formatter.parse("31/07/2012 10:00");

        evento.setFechaInicioYFin(evento.getInicio(), fin);

    }

    @Test(expected = DuracionEventoIncorrectaException.class)
    public void modificaFechasDiaFinDeSemana() throws Exception
    {
        Evento evento = buildEvento("30/07/2012 09:00", "30/07/2012 10:00");

        Date inicio = formatter.parse("06/01/2013 10:00");
        Date fin = formatter.parse("06/01/2013 12:00");

        evento.setFechaInicioYFin(inicio, fin);

    }

    @Test
    public void creaDescripcionEventoConComunesYAula() throws Exception
    {
        Long idEstudio = (long) 1;
        String codigoAsignatura = "PS1026";
        Long subgrupo = (long) 2;
        String codigoAula = "AA203143";

        Estudio estudio1 = new EstudioBuilder().withId(idEstudio).build();

        Estudio estudio2 = new EstudioBuilder().withId(idEstudio + 1).build();

        Asignatura asignatura1 = new AsignaturaBuilder().withId(codigoAsignatura)
                .withEstudio(estudio1).build();
        Asignatura asignatura2 = new AsignaturaBuilder().withId("PS1023").withEstudio(estudio2)
                .build();

        Long calendarioPRId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendario = new CalendarioBuilder().withId(calendarioPRId).build();

        AulaPlanificacion aula = new AulaPlanificacionBuilder().withCodigo(codigoAula).build();

        Evento evento = new EventoBuilder().withTitulo("Evento de prueba")
                .withAsignatura(asignatura1).withAsignatura(asignatura2).withSubgrupoId(subgrupo)
                .withCalendario(calendario).withAulaPlanficacion(aula).build();

        String descripcion = evento.getDescripcionParaUnEstudio(estudio1.getId());

        String descripcionEsperada = codigoAsignatura + " PR" + String.valueOf(subgrupo) + " - C "
                + codigoAula;

        assertThat(descripcion, is(descripcionEsperada));

    }

    @Test
    public void progragaModificionesDeHorarioDeUnEventoASusDetalles() throws Exception
    {
        String fechaInicioInicial = "30/07/2012 09:00";
        String fechaFinInicial = "30/07/2012 10:00";

        String fechaInicioActualizada = "30/07/2012 13:00";
        String fechaFinActualizada = "30/07/2012 14:00";

        Evento evento = buildEvento(fechaInicioInicial, fechaFinInicial);

        new EventoDetalleBuilder().withInicioFechaString(fechaInicioInicial)
                .withFinFechaString(fechaInicioInicial).withEvento(evento).build();
        new EventoDetalleBuilder().withInicioFechaString(fechaInicioInicial)
                .withFinFechaString(fechaFinInicial).withEvento(evento).build();

        evento.setFechaInicioYFin(formatter.parse(fechaInicioActualizada),
                formatter.parse(fechaFinActualizada));

        assertThat(detallesTienenHoraActualizada(evento.getEventosDetalle()), is(true));

    }

    private Boolean detallesTienenHoraActualizada(List<EventoDetalle> eventosDetalle)
    {
        int horaInicioEsperada = 13;
        int minutoInicioEsperado = 0;
        int horaFinEsperada = 14;
        int minutoFinEsperado = 0;

        for (EventoDetalle detalle : eventosDetalle)
        {
            Calendar inicio = Calendar.getInstance();
            inicio.setTime(detalle.getInicio());

            if (inicio.get(Calendar.HOUR_OF_DAY) != horaInicioEsperada
                    || inicio.get(Calendar.MINUTE) != minutoInicioEsperado)
            {
                return false;
            }

            Calendar fin = Calendar.getInstance();
            fin.setTime(detalle.getFin());

            if (fin.get(Calendar.HOUR_OF_DAY) != horaFinEsperada
                    || fin.get(Calendar.MINUTE) != minutoFinEsperado)
            {
                return false;
            }

        }
        return true;
    }

    private Evento buildEvento(String fechaInicial, String fechaFinal) throws Exception
    {
        TipoEstudio tipoEstudio = new TipoEstudioBuilder().withId("G").withNombre("Grau").build();

        Estudio estudio = new EstudioBuilder().withNombre("Grau en Psicologia")
                .withTipoEstudio(tipoEstudio).build();

        Asignatura asignatura = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(new Long(3)).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Semestre semestre = new SemestreBuilder().withSemestre(new Long(1))
                .withNombre("Primer semestre").build();

        Long calendarioPRId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendario = new CalendarioBuilder().withId(calendarioPRId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPRId)).build();

        return new EventoBuilder().withTitulo("Evento de prueba").withAsignatura(asignatura)
                .withInicioYFinFechaString(fechaInicial, fechaFinal).withSemestre(semestre)
                .withCalendario(calendario).withDetalleManual(false).build();
    }
}
