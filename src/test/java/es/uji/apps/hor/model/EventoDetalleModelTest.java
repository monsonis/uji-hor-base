package es.uji.apps.hor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import es.uji.apps.hor.builders.AsignaturaBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.EventoDetalleBuilder;

public class EventoDetalleModelTest
{

    @Test
    public void devuelveDescripcionConGrupoYComunesEsperada() throws Exception
    {
        Long calendarioPracticasId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendarioPR = new CalendarioBuilder().withId(calendarioPracticasId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPracticasId)).build();

        Asignatura asignaturaComun1 = new AsignaturaBuilder().withComun(true).withId("PS1026")
                .build();

        Asignatura asignaturaComun2 = new AsignaturaBuilder().withComun(true).withId("II2003")
                .build();

        Evento evento = new EventoBuilder().withAsignatura(asignaturaComun1)
                .withAsignatura(asignaturaComun2).withGrupoId("A").withSubgrupoId(new Long(1))
                .withCalendario(calendarioPR).build();

        EventoDetalle eventoDetalle = new EventoDetalleBuilder().withEvento(evento).build();

        assertThat(eventoDetalle.getDescripcionConGrupoYComunes(), is(" PS1026 II2003 A PR1"));
    }
}
