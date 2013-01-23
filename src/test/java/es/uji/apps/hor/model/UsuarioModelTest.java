package es.uji.apps.hor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.UsuarioBuilder;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

public class UsuarioModelTest
{

    @Test
    public void compruebaQueElUsuarioSiPuedeAccederAlCentro()
    {
        Centro centro = new CentroBuilder().withId(new Long(1)).withNombre("Centro de estudios")
                .build();
        Usuario usuario = new UsuarioBuilder().withNombre("Un nombre").withCentro(centro).build();

        assertThat(usuario.puedeAccederACentro(centro), is(true));

    }

    @Test(expected = UnauthorizedUserException.class)
    public void compruebaQueElUsuarioNoPuedeAccederAlCentroSiTieneOtroCentroAsignado()
            throws Exception
    {
        Centro centroDelUsuario = new CentroBuilder().withId(new Long(1))
                .withNombre("Centro de estudios").build();
        Centro otroCentro = new CentroBuilder().withId(new Long(2))
                .withNombre("Otro centro de estudios").build();

        Usuario usuario = new UsuarioBuilder().withNombre("Un nombre").withCentro(centroDelUsuario)
                .build();

        usuario.compruebaAccesoACentro(otroCentro);

    }

    @Test(expected = UnauthorizedUserException.class)
    public void compruebaQueElUsuarioNoPuedeAccederAlCentroSiNoTieneCentroAsignado()
            throws Exception
    {

        Centro otroCentro = new CentroBuilder().withId(new Long(2))
                .withNombre("Otro centro de estudios").build();

        Usuario usuario = new UsuarioBuilder().withNombre("Un nombre").build();

        usuario.compruebaAccesoACentro(otroCentro);

    }

    @Test
    public void compruebaQueElUsuarioSiPuedeAccederAlEstudio()
    {
        Estudio estudio = new EstudioBuilder().withId(new Long(1))
                .withNombre("Estudio del usuario").build();
        Estudio otroEstudioDelUsuario = new EstudioBuilder().withId(new Long(2))
                .withNombre("Otro estudio del usuario").build();

        Usuario usuario = new UsuarioBuilder().withNombre("Un nombre").withEstudio(estudio)
                .withEstudio(otroEstudioDelUsuario).build();

        assertThat(usuario.puedeAccederAEstudio(estudio), is(true));

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

        Usuario usuario = new UsuarioBuilder().withNombre("Un nombre").withEstudio(estudio)
                .withEstudio(otroEstudioDelUsuario).build();

        usuario.compruebaAccesoAEstudio(otroEstudio);

    }

}
