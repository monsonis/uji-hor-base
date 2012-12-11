package es.uji.apps.hor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import es.uji.apps.hor.db.ItemDetalleDTO;
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
    private ItemDTO comun;

    private CentroDTO centro;
    private TipoEstudioDTO tipoEstudio;
    private EstudioDTO estudio;
    private DiaSemanaDTO diaSemana;
    private SemestreDTO semestre;
    private AulaDTO aula;
    private AulaPlanificacionDTO aulaPlanificacion;
    private AsignaturaComunDTO asignaturaComun1;
    private AsignaturaComunDTO asignaturaComun2;

    @Before
    public void rellenaItem() throws ParseException
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

        diaSemana = new DiaSemanaDTO();
        diaSemana.setNombre("Prueba");
        eventosDAO.insert(diaSemana);

        semestre = new SemestreDTO();
        semestre.setNombre("Semestre Prueba");
        eventosDAO.insert(semestre);

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
        item.setDiaSemana(diaSemana);
        item.setEstudio(estudio);
        item.setEstudioDesc(estudio.getNombre());
        item.setGrupoId("A");
        item.setHoraFin(horaFin);
        item.setHoraInicio(horaInicio);
        item.setDetalleManual(false);
        item.setSemestre(semestre);
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

        List<Evento> listaEventos = eventosDAO.getEventosSemanaGenerica(estudio.getId(),
                new Long(1), semestre.getId(), "A", calendarios);
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
    public void modificaDiaYHoraGrupoAsignaturaConAsignaturaComunTest() throws ParseException
    {
        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        Calendar calendarIni = Calendar.getInstance();
        calendarIni.set(Calendar.HOUR, 10);
        calendarIni.set(Calendar.MINUTE, 0);

        Calendar calendarFin = Calendar.getInstance();
        calendarFin.set(Calendar.HOUR, 12);
        calendarFin.set(Calendar.MINUTE, 0);

        eventosDAO.modificaDiaYHoraGrupoAsignatura(item.getId(), calendarIni.getTime(),
                calendarFin.getTime());

        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);
        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertEquals(item.getHoraInicio(), comun.getHoraInicio());
    }

    @Test
    public void getEventosDeUnCursoTest()
    {
        eventosDAO.insert(item);

        List<Evento> listaEventos = eventosDAO.getEventosDeUnCurso(estudio.getId(), new Long(1),
                semestre.getId(), "A");
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
        aula = new AulaDTO();
        aula.setNombre("Aula1000");
        aula.setCentro(centro);
        eventosDAO.insert(aula);

        aulaPlanificacion = new AulaPlanificacionDTO();
        aulaPlanificacion.setNombre("Aula1000-1");
        aulaPlanificacion.setAula(aula);
        aulaPlanificacion.setEstudio(estudio);
        eventosDAO.insert(aulaPlanificacion);
    }

    public void rellenaDatosItemComun()
    {
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

        comun = new ItemDTO();
        comun.setAsignaturaId(asignaturaComun2.getAsignaturaId());
        comun.setAsignatura(asignaturaComun2.getNombre());
        comun.setCaracter("Troncal");
        comun.setCaracterId("TR");
        comun.setComun(new Long(1));
        comun.setCursoId(new Long(1));
        comun.setDiaSemana(diaSemana);
        comun.setEstudio(estudio);
        comun.setEstudioDesc(estudio.getNombre());
        comun.setGrupoId("A");
        comun.setHoraFin(item.getHoraFin());
        comun.setHoraInicio(item.getHoraInicio());
        comun.setDetalleManual(false);
        comun.setSemestre(semestre);
        comun.setSubgrupoId(new Long(1));
        comun.setTipoSubgrupoId("TU");
    }

    @Test
    public void actualizaAulaPlanificacionEventoConAsignaturasComunesTest()
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        rellenaDatosTestsConAulas();

        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
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

        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
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

        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), aulaPlanificacion.getId(), false);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertEquals(aulaPlanificacion, item.getAulasPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertNull(comun.getAulasPlanificacion());
    }

    @Test
    public void modificaDetallesGrupoAsignaturaConComunesTest()
    {
        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        eventosDAO.modificaDetallesGrupoAsignatura(item.getId(), item.getHoraFin(),
                item.getHoraFin(), new Date(), new Integer(4), item.getRepetirCadaSemanas(),
                item.getHastaElDia(), item.getDetalleManual());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertEquals(new Integer(4), comun.getNumeroIteraciones());
    }

    @Test
    public void updateEventoConDetalleManualYAsignaturasComunesTest()
            throws RegistroNoEncontradoException
    {
        Calendar calendarIni = Calendar.getInstance();
        calendarIni.set(Calendar.HOUR, 10);
        calendarIni.set(Calendar.MINUTE, 0);

        Calendar calendarFin = Calendar.getInstance();
        calendarFin.set(Calendar.HOUR, 12);
        calendarFin.set(Calendar.MINUTE, 0);

        item.setComun(new Long(1));
        item.setHoraInicio(calendarIni.getTime());
        item.setHoraFin(calendarFin.getTime());
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        comun.setHoraInicio(item.getHoraInicio());
        comun.setHoraFin(item.getHoraFin());
        eventosDAO.insert(comun);

        eventosDAO.updateEventoConDetalleManual(item.getId(),
                Collections.singletonList(calendarIni.getTime()), item.getHoraInicio(),
                item.getHoraFin());
        
        List<ItemDetalleDTO> detalleList = eventosDAO.get(ItemDetalleDTO.class, "item_id=" + item.getId());
        List<ItemDetalleDTO> detalleComunList = eventosDAO.get(ItemDetalleDTO.class, "item_id=" + comun.getId());
        
        Assert.assertEquals(detalleList.size(), detalleComunList.size());
    }

}
