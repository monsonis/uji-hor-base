package es.uji.apps.hor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.PersonaBuilder;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

public class PersonaModelTest
{

    @Test
    public void compruebaQueElUsuarioSiPuedeAccederAlCentro()
    {
        Centro centro = new CentroBuilder().withId(new Long(1)).withNombre("Centro de estudios")
                .build();
        Persona persona = new PersonaBuilder().withNombre("Un nombre").withCentroAutorizado(centro).build();

        assertThat(persona.puedeAccederACentro(centro), is(true));

    }

    @Test(expected = UnauthorizedUserException.class)
    public void compruebaQueElUsuarioNoPuedeAccederAlCentroSiTieneOtroCentroAsignado()
            throws Exception
    {
        Centro centroDelUsuario = new CentroBuilder().withId(new Long(1))
                .withNombre("Centro de estudios").build();
        Centro otroCentro = new CentroBuilder().withId(new Long(2))
                .withNombre("Otro centro de estudios").build();

        Persona persona = new PersonaBuilder().withNombre("Un nombre").withCentroAutorizado(centroDelUsuario)
                .build();

        persona.compruebaAccesoACentro(otroCentro);

    }

    @Test(expected = UnauthorizedUserException.class)
    public void compruebaQueElUsuarioNoPuedeAccederAlCentroSiNoTieneCentroAsignado()
            throws Exception
    {

        Centro otroCentro = new CentroBuilder().withId(new Long(2))
                .withNombre("Otro centro de estudios").build();

        Persona persona = new PersonaBuilder().withNombre("Un nombre").build();

        persona.compruebaAccesoACentro(otroCentro);

    }

    @Test
    public void compruebaQueElUsuarioSiPuedeAccederAlEstudio()
    {
        Estudio estudio = new EstudioBuilder().withId(new Long(1))
                .withNombre("Estudio del usuario").build();
        Estudio otroEstudioDelUsuario = new EstudioBuilder().withId(new Long(2))
                .withNombre("Otro estudio del usuario").build();

        Persona persona = new PersonaBuilder().withNombre("Un nombre").withEstudioAutorizado(estudio)
                .withEstudioAutorizado(otroEstudioDelUsuario).build();

        assertThat(persona.puedeAccederAEstudio(estudio), is(true));

    }

    @Test(expected = UnauthorizedUserException.class)
    public void compruebaQueElUsuarioNoPuedeAccederAlEstudioSiNoLoTieneAsignado() throws Exception
    {
        Estudio estudio = new EstudioBuilder().withId(new Long(1))
                .withNombre("Estudio del usuario").build();
        Estudio otroEstudioDelUsuario = new EstudioBuilder().withId(new Long(2))
                .withNombre("Otro estudio del usuario").build();

        Estudio otroEstudio = new EstudioBuilder().withId(new Long(3)).withNombre("Otro estudio")
                .build();

        Persona persona = new PersonaBuilder().withNombre("Un nombre").withEstudioAutorizado(estudio)
                .withEstudioAutorizado(otroEstudioDelUsuario).build();

        persona.compruebaAccesoAEstudio(otroEstudio);

    }

}
