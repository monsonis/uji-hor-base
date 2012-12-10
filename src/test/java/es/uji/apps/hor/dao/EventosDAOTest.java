package es.uji.apps.hor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.db.AsignaturaComunDTO;
import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.db.TipoEstudioDTO;
import es.uji.apps.hor.model.Evento;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class EventosDAOTest
{
    @Autowired
    private EventosDAO eventosDAO;

    private ItemDTO item;

    private CentroDTO centro;
    private TipoEstudioDTO tipoEstudio;
    private EstudioDTO estudio;
    private AulaDTO aula;
    private AulaPlanificacionDTO aulaPlanificacion;
    private AsignaturaComunDTO asignaturaComun1;
    private AsignaturaComunDTO asignaturaComun2;

    @Before
    public void rellenaItem() throws ParseException
    {
        String horaInicioStr = "30/07/2012 9:00";
        String horaFinStr = "30/07/2012 11:00";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date horaInicio = sdf.parse(horaInicioStr);
        Date horaFin = sdf.parse(horaFinStr);

        item = new ItemDTO();
        item.setAsignaturaId("PR1001");
        item.setAsignatura("Prueba");
        item.setCaracter("Troncal");
        item.setCaracterId("TR");
        item.setComun(new Long(0));
        item.setCursoId(new Long(1));
        item.setDiaSemana((DiaSemanaDTO) eventosDAO.get(DiaSemanaDTO.class, new Long(1)).get(0));
        item.setEstudio((EstudioDTO) eventosDAO.get(EstudioDTO.class, new Long(1)).get(0));
        item.setEstudioDesc("Estudio para prueba");
        item.setGrupoId("A");
        item.setHoraFin(horaFin);
        item.setHoraInicio(horaInicio);
        item.setDetalleManual(false);
        item.setSemestre((SemestreDTO) eventosDAO.get(SemestreDTO.class, new Long(1)).get(0));
        item.setSubgrupoId(new Long(1));
        item.setTipoSubgrupoId("TU");

    }

    @Test
    public void getEventosSemanaGenericaTest()
    {
        eventosDAO.insert(item);

        List<Long> calendarios = new ArrayList<Long>();
        calendarios.add(new Long(1));
        calendarios.add(new Long(2));
        calendarios.add(new Long(3));
        calendarios.add(new Long(4));
        calendarios.add(new Long(5));

        List<Evento> listaEventos = eventosDAO.getEventosSemanaGenerica(new Long(1), new Long(1),
                new Long(1), "A", calendarios);
        Assert.assertTrue(listaEventos.size() > 0);
    }

    @Test
    public void modificaDiaYHoraGrupoAsignaturaTest() throws ParseException
    {
        eventosDAO.insert(item);

        Calendar calendarIni = Calendar.getInstance();
        calendarIni.set(Calendar.HOUR, 10);
        calendarIni.set(Calendar.MINUTE, 0);

        Calendar calendarFin = Calendar.getInstance();
        calendarFin.set(Calendar.HOUR, 12);
        calendarFin.set(Calendar.MINUTE, 0);

        Evento evento = eventosDAO.modificaDiaYHoraGrupoAsignatura(item.getId(),
                calendarIni.getTime(), calendarFin.getTime());
        Assert.assertEquals(calendarIni.getTime(), evento.getInicio());
    }

    @Test
    public void getEventosDeUnCursoTest()
    {
        eventosDAO.insert(item);

        List<Evento> listaEventos = eventosDAO.getEventosDeUnCurso(new Long(1), new Long(1),
                new Long(1), "A");
        Assert.assertTrue(listaEventos.size() > 0);
    }

    @Test
    @Ignore
    public void desasignaEventoSemanaGenericaTest() throws RegistroNoEncontradoException
    {
        eventosDAO.insert(item);

        eventosDAO.deleteEventoSemanaGenerica(item.getId());

        List<ItemDTO> items = eventosDAO.get(ItemDTO.class, item.getId());
        Assert.assertTrue(items.size() > 0);
        Assert.assertNull(items.get(0).getDiaSemana());
    }

    @Test
    public void divideClaseDeMasDeUnaHoraTest() throws RegistroNoEncontradoException,
            EventoNoDivisibleException
    {
        eventosDAO.insert(item);
        Long tiempoClase = item.getHoraFin().getTime() - item.getHoraInicio().getTime();

        eventosDAO.divideEventoSemanaGenerica(item.getId());

        List<Evento> eventos = eventosDAO.getEventosDeUnCurso(new Long(1), new Long(1),
                new Long(1), "A");

        for (Evento evento : eventos)
        {
            if (evento.getId().equals(item.getId())
                    || evento.getTitulo().startsWith(item.getAsignaturaId()))
            {
                Long tiempoClaseDividida = evento.getFin().getTime() - evento.getInicio().getTime();
                Assert.assertEquals((Long) (tiempoClase / 2), tiempoClaseDividida);
            }
        }
    }

    @Test
    public void eliminaEventoDuplicadoEnSemanaGenericaTest() throws RegistroNoEncontradoException,
            EventoNoDivisibleException
    {
        eventosDAO.insert(item);
        eventosDAO.divideEventoSemanaGenerica(item.getId());

        List<Evento> eventos = eventosDAO.getEventosDeUnCurso(new Long(1), new Long(1),
                new Long(1), "A");

        Evento eventoDividido = null;

        // Buscamos el evento dividido
        for (Evento evento : eventos)
        {
            if (evento.getTitulo().startsWith(item.getAsignaturaId())
                    && !evento.getId().equals(item.getId()))
            {
                eventoDividido = evento;
                break;
            }
        }

        if (eventoDividido != null)
        {
            // Eliminamos el evento
            eventosDAO.deleteEventoSemanaGenerica(eventoDividido.getId());
            Assert.assertEquals(0, eventosDAO.get(ItemDTO.class, eventoDividido.getId()).size());
        }
    }

    @Test(expected = EventoNoDivisibleException.class)
    public void divideClaseDeMenosDeUnaHoraTest() throws ParseException,
            RegistroNoEncontradoException, EventoNoDivisibleException
    {
        String horaInicioStr = "30/07/2012 9:00";
        String horaFinStr = "30/07/2012 9:30";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date horaInicio = sdf.parse(horaInicioStr);
        Date horaFin = sdf.parse(horaFinStr);

        item.setHoraInicio(horaInicio);
        item.setHoraFin(horaFin);

        eventosDAO.insert(item);

        eventosDAO.divideEventoSemanaGenerica(item.getId());
    }

    public void rellenaDatosTestsConAulas()
    {
        centro = new CentroDTO();
        centro.setNombre("Centro de prueba");
        eventosDAO.insert(centro);

        tipoEstudio = new TipoEstudioDTO();
        tipoEstudio.setId("PR");
        tipoEstudio.setNombre("Pruebas");
        eventosDAO.insert(tipoEstudio);

        estudio = new EstudioDTO();
        estudio.setNombre("Estudio de prueba");
        estudio.setTipoEstudio(tipoEstudio);
        estudio.setCentro(centro);
        estudio.setOficial(new Long(1));
        eventosDAO.insert(estudio);

        aula = new AulaDTO();
        aula.setNombre("Aula1000");
        aula.setCentro(centro);
        eventosDAO.insert(aula);

        aulaPlanificacion = new AulaPlanificacionDTO();
        aulaPlanificacion.setNombre("Aula1000-1");
        aulaPlanificacion.setAula(aula);
        aulaPlanificacion.setEstudio(estudio);
        eventosDAO.insert(aulaPlanificacion);

        asignaturaComun1 = new AsignaturaComunDTO();
        asignaturaComun1.setAsignaturaId(item.getAsignaturaId());
        asignaturaComun1.setCursoAcademicoId(new Long(2012));
        asignaturaComun1.setGrupoComunId(new Long(1));
        asignaturaComun1.setNombre(item.getAsignatura());
        eventosDAO.insert(asignaturaComun1);

        asignaturaComun2 = new AsignaturaComunDTO();
        asignaturaComun2.setAsignaturaId("Comun");
        asignaturaComun2.setCursoAcademicoId(new Long(2012));
        asignaturaComun2.setGrupoComunId(new Long(1));
        asignaturaComun2.setNombre("Prueba Comun");
        eventosDAO.insert(asignaturaComun2);
    }

    @Test
    public void actualizaAulaPlanificacionEventoConAsignaturasComunesTest()
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        rellenaDatosTestsConAulas();

        item.setEstudio(estudio);
        eventosDAO.insert(item);

        ItemDTO comun = new ItemDTO();
        comun.setAsignaturaId("Comun");
        comun.setAsignatura("Prueba Comun");
        comun.setCaracter("Troncal");
        comun.setCaracterId("TR");
        comun.setComun(new Long(0));
        comun.setCursoId(new Long(1));
        comun.setDiaSemana((DiaSemanaDTO) eventosDAO.get(DiaSemanaDTO.class, new Long(1)).get(0));
        comun.setEstudio(estudio);
        comun.setEstudioDesc(estudio.getNombre());
        comun.setGrupoId("A");
        comun.setHoraFin(item.getHoraFin());
        comun.setHoraInicio(item.getHoraInicio());
        comun.setDetalleManual(false);
        comun.setSemestre(item.getSemestre());
        comun.setSubgrupoId(new Long(1));
        comun.setTipoSubgrupoId("TU");
        eventosDAO.insert(comun);

        eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), aulaPlanificacion.getId(), true);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertEquals(aulaPlanificacion, item.getAulasPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertEquals(item.getAulasPlanificacion(), comun.getAulasPlanificacion());
    }

    @Test
    public void desasignaAulaPlanificacionEventoConAsignaturasComunesTest()
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        rellenaDatosTestsConAulas();

        item.setEstudio(estudio);
        eventosDAO.insert(item);

        ItemDTO comun = new ItemDTO();
        comun.setAsignaturaId("Comun");
        comun.setAsignatura("Prueba Comun");
        comun.setCaracter("Troncal");
        comun.setCaracterId("TR");
        comun.setComun(new Long(0));
        comun.setCursoId(new Long(1));
        comun.setDiaSemana((DiaSemanaDTO) eventosDAO.get(DiaSemanaDTO.class, new Long(1)).get(0));
        comun.setEstudio(estudio);
        comun.setEstudioDesc(estudio.getNombre());
        comun.setGrupoId("A");
        comun.setHoraFin(item.getHoraFin());
        comun.setHoraInicio(item.getHoraInicio());
        comun.setDetalleManual(false);
        comun.setSemestre(item.getSemestre());
        comun.setSubgrupoId(new Long(1));
        comun.setTipoSubgrupoId("TU");
        eventosDAO.insert(comun);

        eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), null, true);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertNull(item.getAulasPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);
        Assert.assertEquals(item.getAulasPlanificacion(), comun.getAulasPlanificacion());
    }

    @Test
    public void actualizaAulaPlanificacionEventoTest() throws RegistroNoEncontradoException,
            AulaNoAsignadaAEstudioDelEventoException
    {
        rellenaDatosTestsConAulas();

        item.setEstudio(estudio);
        eventosDAO.insert(item);

        ItemDTO comun = new ItemDTO();
        comun.setAsignaturaId("Comun");
        comun.setAsignatura("Prueba Comun");
        comun.setCaracter("Troncal");
        comun.setCaracterId("TR");
        comun.setComun(new Long(0));
        comun.setCursoId(new Long(1));
        comun.setDiaSemana((DiaSemanaDTO) eventosDAO.get(DiaSemanaDTO.class, new Long(1)).get(0));
        comun.setEstudio(estudio);
        comun.setEstudioDesc(estudio.getNombre());
        comun.setGrupoId("A");
        comun.setHoraFin(item.getHoraFin());
        comun.setHoraInicio(item.getHoraInicio());
        comun.setDetalleManual(false);
        comun.setSemestre(item.getSemestre());
        comun.setSubgrupoId(new Long(1));
        comun.setTipoSubgrupoId("TU");
        eventosDAO.insert(comun);

        eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), aulaPlanificacion.getId(), false);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertEquals(aulaPlanificacion, item.getAulasPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertNull(comun.getAulasPlanificacion());
    }

}
