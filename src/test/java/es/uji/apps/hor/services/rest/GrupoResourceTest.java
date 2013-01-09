package es.uji.apps.hor.services.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.text.ParseException;
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
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.rest.UIEntity;

public class GrupoResourceTest extends AbstractRestTest
{
    private Long estudioId;
    private final Long cursoId = new Long(1);
    private final Long semestreId = new Long(1);

    @Autowired
    private EventosDAO eventosDao;

    @Autowired
    private EstudiosDAO estudiosDao;

    @Before
    @Transactional
    public void creaEventosIniciales() throws Exception
    {
        Estudio estudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Psicologia")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();
        estudioId = estudio.getId();

        Estudio otroEstudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Informática")
                .withTipoEstudio("Grau").withTipoEstudioId("G").build();

        Asignatura asignaturaFicticia1 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Asignatura asignaturaFicticia2 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1027")
                .withNombre("Asignatura de Psicologia").withEstudio(estudio).build();

        Asignatura asignaturaOtraTitulacion = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("I001")
                .withNombre("Asignatura de Informatica").withEstudio(otroEstudio).build();

        Semestre semestre = new SemestreBuilder().withSemestre(semestreId)
                .withNombre("Primer semestre").build();

        Long calendarioPracticasId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendarioPR = new CalendarioBuilder().withId(calendarioPracticasId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPracticasId)).build();

        Long calendarioTeoriaId = TipoSubgrupo.TE.getCalendarioAsociado();
        Calendario calendarioTE = new CalendarioBuilder().withId(calendarioTeoriaId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioTeoriaId)).build();

        new EventoBuilder(eventosDao).withTitulo("Evento de prueba 1 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 09:00", "10/10/2012 11:00").withGrupoId("A")
                .withSubgrupoId(new Long(1)).withSemestre(semestre).withCalendario(calendarioPR)
                .withDetalleManual(false).build();

        new EventoBuilder(eventosDao).withTitulo("Evento de prueba 2 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 10:00", "10/10/2012 12:00").withGrupoId("A")
                .withSubgrupoId(new Long(1)).withSemestre(semestre).withCalendario(calendarioTE)
                .withDetalleManual(false).build();

        new EventoBuilder(eventosDao).withTitulo("Evento de prueba 1 de asignatura 2")
                .withAsignatura(asignaturaFicticia2)
                .withInicioYFinFechaString("11/10/2012 10:00", "11/10/2012 12:00").withGrupoId("B")
                .withSubgrupoId(new Long(1)).withSemestre(semestre).withCalendario(calendarioTE)
                .withDetalleManual(false).build();

        new EventoBuilder(eventosDao).withTitulo("Evento de prueba 3")
                .withAsignatura(asignaturaOtraTitulacion)
                .withInicioYFinFechaString("12/10/2012 13:00", "12/10/2012 14:00").withGrupoId("C")
                .withSubgrupoId(new Long(1)).withSemestre(semestre).withCalendario(calendarioTE)
                .withDetalleManual(false).build();
    }

    @Test
    @Transactional
    public void devuelveLosGrupos() throws ParseException
    {

        List<UIEntity> listaGrupos = getEventosDetalladosEnRangoDeFechas();

        assertThat(listaGrupos, hasSize(2));
        assertThat(contieneGrupo(listaGrupos, "A"), is(true));
        assertThat(contieneGrupo(listaGrupos, "B"), is(true));

    }

    private List<UIEntity> getEventosDetalladosEnRangoDeFechas()
    {
        ClientResponse response = resource.path("grupo").queryParams(getDefaulQueryParams())
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    private Boolean contieneGrupo(List<UIEntity> listaGrupos, String grupoBuscar)
    {
        for (UIEntity entity : listaGrupos)
        {
            String grupo = entity.get("grupo").replace("\"", "");
            if (grupo.equals(grupoBuscar))
            {
                return true;
            }
        }
        return false;
    }

    protected MultivaluedMap<String, String> getDefaulQueryParams()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("estudioId", String.valueOf(estudioId));
        params.putSingle("cursoId", String.valueOf(cursoId));
        params.putSingle("semestreId", String.valueOf(semestreId));
        return params;
    }

}
