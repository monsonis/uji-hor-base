package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.builders.AsignaturaBuilder;
import es.uji.apps.hor.builders.CalendarioBuilder;
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.EventoBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.apps.hor.model.TipoSubgrupo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = false)
public class EventosDAOTest
{
    @Autowired
    private CentroDAO centroDAO;

    @Autowired
    private EventosDAO eventosDAO;

    @Autowired
    protected EstudiosDAO estudiosDao;
    
    @Autowired
    protected TipoEstudioDAO tipoEstudioDAO;


    private Long estudioId;
    private List<Long> listaCalendariosId = new ArrayList<Long>();

    protected final Long cursoId = new Long(1);
    protected final Long semestreId = new Long(1);
    protected final String grupoId = "A";

    @Before
    @Transactional
    public void creaDatosIniciales() throws Exception
    {

        Centro centro = new CentroBuilder(centroDAO).withNombre("Centro de prueba").build();
        TipoEstudio tipoEstudio = new TipoEstudioBuilder(tipoEstudioDAO).withId("PR").withNombre("Pruebas")
                .build();
        Estudio estudio = new EstudioBuilder(estudiosDao).withNombre("Estudio de prueba")
                .withTipoEstudio(tipoEstudio).withCentro(centro).withOficial(true).build();
        estudioId = estudio.getId();

        Semestre semestre = new SemestreBuilder().withSemestre(semestreId)
                .withNombre("Primer semestre").build();

        Long calendarioPracticasId = TipoSubgrupo.PR.getCalendarioAsociado();
        Calendario calendarioPR = new CalendarioBuilder().withId(calendarioPracticasId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioPracticasId)).build();

        Long calendarioTeoriaId = TipoSubgrupo.TE.getCalendarioAsociado();
        Calendario calendarioTE = new CalendarioBuilder().withId(calendarioTeoriaId)
                .withNombre(TipoSubgrupo.getTipoSubgrupo(calendarioTeoriaId)).build();

        listaCalendariosId.add(calendarioPracticasId);
        listaCalendariosId.add(calendarioTeoriaId);
        
        Asignatura asignaturaFicticia1 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1026")
                .withNombre("Intervenció Psicosocial").withEstudio(estudio).build();

        Asignatura asignaturaFicticia2 = new AsignaturaBuilder().withCaracter("Obligatoria")
                .withCaracterId("OB").withComun(false).withCursoId(cursoId).withId("PS1027")
                .withNombre("Asignatura de Psicologia").withEstudio(estudio).build();

        new EventoBuilder(eventosDAO).withTitulo("Evento de prueba 1 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 09:00", "10/10/2012 11:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withCalendario(calendarioPR).withDetalleManual(false).build();

        new EventoBuilder(eventosDAO).withTitulo("Evento de prueba 2 de asignatura 1")
                .withAsignatura(asignaturaFicticia1)
                .withInicioYFinFechaString("10/10/2012 10:00", "10/10/2012 12:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withCalendario(calendarioTE).withDetalleManual(false).build();

        new EventoBuilder(eventosDAO).withTitulo("Evento de prueba 1 de asignatura 2")
                .withAsignatura(asignaturaFicticia2)
                .withInicioYFinFechaString("11/10/2012 10:00", "11/10/2012 12:00")
                .withGrupoId(grupoId).withSubgrupoId(new Long(1)).withSemestre(semestre)
                .withGrupoId(grupoId).withCalendario(calendarioTE).withDetalleManual(false).build();

    }

    @Test
    @Transactional
    public void getEventosSemanaGenericaTest()
    {
        List<Evento> listaEventos = eventosDAO.getEventosSemanaGenerica(estudioId, cursoId, semestreId, grupoId, listaCalendariosId);

        Assert.assertTrue(listaEventos.size() > 0);
    }

    @Test
    @Ignore
    public void getEventosDeUnCursoTest()
    {
    }

}
