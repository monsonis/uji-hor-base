package es.uji.apps.hor.services.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class SemestreResourceTest extends AbstractRestTest
{
    protected Long estudioId;
    protected Long otroEstudioId;
    protected final Long cursoId = new Long(1);
    protected final Long semestreId = new Long(1);
    protected final String grupoId = "A";
    protected final String calendariosIds = "1;2;3;4;5;6";

    @Autowired
    protected EventosDAO eventosDao;

    @Autowired
    protected EstudiosDAO estudiosDao;

    @Autowired
    protected PersonaDAO personaDAO;

    @Autowired
    protected CentroDAO centroDAO;

    @Autowired
    protected DepartamentoDAO departamentoDAO;

    protected Asignatura asignaturaFicticia1;

    // @Autowired
    // private ApaDAO apaDAO;
    //
    // private void rellenaApa() {
    // new ApaAplicacioneBuilder(apaDAO).withId(new Long(46)).withNombre("Horarios").build();
    // ApaRole roleAdmin = new ApaRoleBuilder(apaDAO).withId(new
    // Long(1)).withNombre("ADMINISTRADOR").build();
    // ApaRole roleUsuario = new ApaRoleBuilder(apaDAO).withId(new
    // Long(2)).withNombre("USUARIO").build();
    // new ApaAplicacionesExtraBuilder(apaDAO).withPersonaId(new
    // Long(831)).withRole(roleUsuario).build();
    // }

    @Before
    @Transactional
    public void creaDatosIniciales() throws Exception
    {
        TipoEstudio tipoEstudio = new TipoEstudioBuilder().withId("G").withNombre("Grau").build();

        Centro centro = new CentroBuilder(centroDAO).withNombre("Centro 1").withId(new Long(1)).build();
        Departamento departamento = new DepartamentoBuilder(departamentoDAO).withNombre("Departamento1")
                .withCentro(centro).build();

        Estudio estudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Psicologia")
                .withTipoEstudio(tipoEstudio).build();
        estudioId = estudio.getId();

        Persona persona = new PersonaBuilder(personaDAO).withId(new Long(1))
                .withNombre("Persona 1").withEmail("persona@uji.es").withActividadId("HOLA")
                .withDepartamento(departamento).withCentroAutorizado(centro).withEstudioAutorizado(estudio).build();

        Estudio otroEstudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Informática")
                .withTipoEstudio(tipoEstudio).build();

        asignaturaFicticia1 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Asignatura asignaturaFicticia2 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1027")
                .withNombre("Asignatura de Psicologia").withEstudio(estudio).build();

        Asignatura asignaturaOtraTitulacion = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("I001")
                .withNombre("Asignatura de Informatica").withEstudio(otroEstudio).build();

        Semestre semestre1 = new SemestreBuilder().withSemestre((long) 1)
                .withNombre("Primer semestre").build();
        Semestre semestre2 = new SemestreBuilder().withSemestre((long) 2)
                .withNombre("Segón semestre").build();

        Long calendarioPracticasId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendarioPR = new CalendarioBuilder().withId(calendarioPracticasId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPracticasId)).build();

        Long calendarioTeoriaId = TipoSubgrupo.TE.getCalendarioAsociado();
        Calendario calendarioTE = new CalendarioBuilder().withId(calendarioTeoriaId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioTeoriaId)).build();

        Evento evento1DeAsignatura1 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 09:00", "10/10/2012 11:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre1)
                .withCalendario(calendarioPR).withDetalleManual(false).build();

        Evento evento2DeAsignatura1 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 2 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 10:00", "10/10/2012 12:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre1)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

        Evento evento1DeAsignatura2 = new EventoBuilder(eventosDao)
                .withTitulo("Evento de prueba 1 de asignatura 2")
                .withAsignatura(asignaturaFicticia2)
                .withInicioYFinFechaString("11/10/2012 10:00", "11/10/2012 12:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre2)
                .withGrupoId(grupoId).withCalendario(calendarioTE).withDetalleManual(false).build();
    }

    @Test
    @Transactional
    public void elServicioDevuelveLosSemestres()
    {
        List<UIEntity> listaSemestres = getListadoSemestres();

        assertThat(listaSemestres, hasSize(2));
    }

    @Test
    @Transactional
    public void elServicioNoDevuelveDatosRepetidos()
    {
        List<UIEntity> listaSemestres = getListadoSemestres();

        assertThat(tieneDatosDuplicados(listaSemestres), is(false));
    }

    private Boolean tieneDatosDuplicados(List<UIEntity> listaSemestres)
    {
        Set<String> ids_de_entidades = new HashSet<String>();
        for (UIEntity entidad : listaSemestres)
        {
            String entidad_id = entidad.get("semestre");
            if (ids_de_entidades.contains(entidad_id))
            {
                return true;
            }
            else
            {
                ids_de_entidades.add(entidad_id);
            }
        }
        return false;
    }

    private List<UIEntity> getListadoSemestres()
    {
        ClientResponse response = resource.path("semestre/").queryParams(getDefaulQueryParams())
                .accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

        return response.getEntity(new GenericType<List<UIEntity>>()
        {
        });
    }

    protected MultivaluedMap<String, String> getDefaulQueryParams()
    {
        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("estudioId", String.valueOf(estudioId));
        params.putSingle("cursoId", String.valueOf(cursoId));
        return params;
    }
}
