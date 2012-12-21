package es.uji.apps.hor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.text.SimpleDateFormat;

import org.junit.Test;

import es.uji.apps.hor.builders.GrupoAsignaturaBuilder;

public class GrupoAsignaturaModelTest
{
    private SimpleDateFormat formatter;

    public GrupoAsignaturaModelTest()
    {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @Test
    public void planificaGrupoAsignaturaSinAsignar()
    {
        GrupoAsignatura grupoAsignatura = buildGrupoAsignatura();

        grupoAsignatura.planificaGrupoAsignaturaSinAsignar();

        assertThat(grupoAsignatura.getInicio(), notNullValue());
        assertThat(grupoAsignatura.getFin(), notNullValue());
        assertThat(grupoAsignatura.getDiaSemana(), notNullValue());
    }

    private GrupoAsignatura buildGrupoAsignatura()
    {
        GrupoAsignatura grupoAsignatura = new GrupoAsignaturaBuilder().withTitulo(
                "Grupo Asignatura Prueba").build();

        return grupoAsignatura;
    }
}
