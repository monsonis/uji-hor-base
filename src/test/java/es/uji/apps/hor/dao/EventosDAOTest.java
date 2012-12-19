package es.uji.apps.hor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
import es.uji.apps.hor.db.ItemComunDTO;
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
    private ItemComunDTO itemComun1;
    private ItemComunDTO itemComun2;

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

    private void rellenaItemsComunes(ItemDTO item1, ItemDTO item2)
    {
        itemComun1 = new ItemComunDTO();
        itemComun1.setItem(item1);
        itemComun1.setAsignaturaId(item1.getAsignaturaId());
        itemComun1.setItemComun(item2);
        itemComun1.setAsignaturaComunId(item2.getAsignaturaId());

        itemComun2 = new ItemComunDTO();
        itemComun2.setItem(item2);
        itemComun2.setAsignaturaId(item2.getAsignaturaId());
        itemComun2.setItemComun(item1);
        itemComun2.setAsignaturaComunId(item1.getAsignaturaId());
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

        rellenaItemsComunes(item, comun);
        eventosDAO.insert(itemComun1);
        eventosDAO.insert(itemComun2);

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
    public void desasignaEventoSemanaGenericaTest() throws RegistroNoEncontradoException
    {
        eventosDAO.insert(item);

        eventosDAO.deleteEventoSemanaGenerica(item.getId());

        List<ItemDTO> items = eventosDAO.get(ItemDTO.class, item.getId());
        Assert.assertTrue(items.size() > 0);
        Assert.assertNull(items.get(0).getDiaSemana());

        List<Evento> eventosDetalle = eventosDAO.getEventosDetalleByEventoId(item.getId());
        Assert.assertEquals(0, eventosDetalle.size());
    }

    @Test
    public void desasignaEventoSemanaGenericaConAsignaturasComunesTest()
            throws RegistroNoEncontradoException
    {
        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        rellenaItemsComunes(item, comun);
        eventosDAO.insert(itemComun1);
        eventosDAO.insert(itemComun2);

        eventosDAO.deleteEventoSemanaGenerica(item.getId());

        List<ItemDTO> items = eventosDAO.get(ItemDTO.class, comun.getId());
        Assert.assertTrue(items.size() > 0);
        Assert.assertNull(items.get(0).getDiaSemana());

        List<Evento> eventosDetalle = eventosDAO.getEventosDetalleByEventoId(comun.getId());
        Assert.assertEquals(0, eventosDetalle.size());
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
    public void divideClaseDeMasDeUnaHoraConAsignaturasComunesTest() throws RegistroNoEncontradoException,
            EventoNoDivisibleException
    {
        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        rellenaItemsComunes(item, comun);
        eventosDAO.insert(itemComun1);
        eventosDAO.insert(itemComun2);

        eventosDAO.divideEventoSemanaGenerica(item.getId());

        List<Evento> eventos = eventosDAO.getEventosDeUnCurso(estudio.getId(), comun.getCursoId(),
                semestre.getId(), comun.getGrupoId());

        Evento eventoComunDividido = null;

        // Buscamos el evento común dividido
        for (Evento evento : eventos)
        {
            if (evento.getTitulo().startsWith(comun.getAsignaturaId())
                    && !evento.getId().equals(comun.getId()))
            {
                eventoComunDividido = evento;
                break;
            }
        }
        
        Assert.assertNotNull(eventoComunDividido);
    }

    @Test
    public void eliminaEventoDuplicadoEnSemanaGenericaTest() throws RegistroNoEncontradoException,
            EventoNoDivisibleException
    {
        eventosDAO.insert(item);
        eventosDAO.divideEventoSemanaGenerica(item.getId());

        List<Evento> eventos = eventosDAO.getEventosDeUnCurso(estudio.getId(), item.getCursoId(),
                semestre.getId(), item.getGrupoId());

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

        // Eliminamos el evento
        eventosDAO.deleteEventoSemanaGenerica(eventoDividido.getId());
        Assert.assertEquals(0, eventosDAO.get(ItemDTO.class, eventoDividido.getId()).size());
    }

    @Test
    public void eliminaEventoDuplicadoEnSemanaGenericaConAsignaturasComunesTest()
            throws RegistroNoEncontradoException, EventoNoDivisibleException
    {
        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        rellenaItemsComunes(item, comun);
        eventosDAO.insert(itemComun1);
        eventosDAO.insert(itemComun2);

        eventosDAO.divideEventoSemanaGenerica(item.getId());

        List<Evento> eventos = eventosDAO.getEventosDeUnCurso(estudio.getId(), item.getCursoId(),
                semestre.getId(), item.getGrupoId());

        Evento eventoDividido = null;
        Evento eventoComunDividido = null;

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

        // Y el común dividio
        for (Evento evento : eventos)
        {
            if (evento.getTitulo().startsWith(comun.getAsignaturaId())
                    && !evento.getId().equals(comun.getId()))
            {
                eventoComunDividido = evento;
                break;
            }
        }

        // Eliminamos el evento
        eventosDAO.deleteEventoSemanaGenerica(eventoDividido.getId());

        Assert.assertEquals(0, eventosDAO.get(ItemDTO.class, eventoComunDividido.getId()).size());
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
        asignaturaComun1.setGrupoComunId(new Long(1));
        asignaturaComun1.setNombre("PR1001 i Comun");
        eventosDAO.insert(asignaturaComun1);

        asignaturaComun2 = new AsignaturaComunDTO();
        asignaturaComun2.setAsignaturaId("Comun");
        asignaturaComun2.setGrupoComunId(new Long(1));
        asignaturaComun2.setNombre("PR1001 i Comun");
        eventosDAO.insert(asignaturaComun2);

        comun = new ItemDTO();
        comun.setAsignaturaId(asignaturaComun2.getAsignaturaId());
        comun.setAsignatura(asignaturaComun2.getAsignaturaId());
        comun.setCaracter(item.getCaracter());
        comun.setCaracterId(item.getCaracterId());
        comun.setComun(new Long(1));
        comun.setCursoId(item.getCursoId());
        comun.setDiaSemana(item.getDiaSemana());
        comun.setEstudio(estudio);
        comun.setEstudioDesc(estudio.getNombre());
        comun.setGrupoId(item.getGrupoId());
        comun.setHoraFin(item.getHoraFin());
        comun.setHoraInicio(item.getHoraInicio());
        comun.setDetalleManual(false);
        comun.setSemestre(item.getSemestre());
        comun.setSubgrupoId(item.getSubgrupoId());
        comun.setTipoSubgrupoId(item.getTipoSubgrupoId());
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

        rellenaItemsComunes(item, comun);
        eventosDAO.insert(itemComun1);
        eventosDAO.insert(itemComun2);

        eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), aulaPlanificacion.getId(), false);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertEquals(aulaPlanificacion, item.getAulaPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertEquals(item.getAulaPlanificacion(), comun.getAulaPlanificacion());
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

        eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), null, false);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertNull(item.getAulaPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);
        Assert.assertEquals(item.getAulaPlanificacion(), comun.getAulaPlanificacion());
    }

    @Test
    public void actualizaAulaPlanificacionEventoConGruposComunesTest()
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        rellenaDatosTestsConAulas();

        ItemDTO grupoComun = new ItemDTO();
        grupoComun.setAsignaturaId(item.getAsignaturaId());
        grupoComun.setAsignatura(item.getAsignatura());
        grupoComun.setCaracter(item.getCaracter());
        grupoComun.setCaracterId(item.getCaracterId());
        grupoComun.setComun(new Long(0));
        grupoComun.setCursoId(item.getCursoId());
        grupoComun.setDiaSemana(item.getDiaSemana());
        grupoComun.setEstudio(item.getEstudio());
        grupoComun.setEstudioDesc(item.getEstudioDesc());
        grupoComun.setGrupoId(item.getGrupoId());
        grupoComun.setHoraFin(new Date());
        grupoComun.setHoraInicio(new Date());
        grupoComun.setDetalleManual(false);
        grupoComun.setSemestre(item.getSemestre());
        grupoComun.setSubgrupoId(item.getSubgrupoId());
        grupoComun.setTipoSubgrupoId(item.getTipoSubgrupoId());

        eventosDAO.insert(item);
        eventosDAO.insert(grupoComun);

        eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), aulaPlanificacion.getId(), true);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertEquals(aulaPlanificacion, item.getAulaPlanificacion());

        grupoComun = eventosDAO.get(ItemDTO.class, grupoComun.getId()).get(0);

        Assert.assertEquals(item.getAulaPlanificacion(), grupoComun.getAulaPlanificacion());
    }

    @Test
    public void modificaDetallesGrupoAsignaturaConComunesTest()
    {
        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        rellenaItemsComunes(item, comun);
        eventosDAO.insert(itemComun1);
        eventosDAO.insert(itemComun2);

        eventosDAO.modificaDetallesGrupoAsignatura(item.getId(), item.getHoraFin(),
                item.getHoraFin(), new Date(), new Integer(4), item.getRepetirCadaSemanas(),
                item.getHastaElDia(), item.getDetalleManual());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertEquals(new Integer(4), comun.getNumeroIteraciones());
    }

    @Test
    public void updateHorasEventoDetalleManualConAsignaturasComunesTest()
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

        ItemDetalleDTO itemDetalle = new ItemDetalleDTO();
        itemDetalle.setItem(item);
        itemDetalle.setInicio(item.getHoraInicio());
        itemDetalle.setFin(item.getHoraFin());
        eventosDAO.insert(itemDetalle);

        rellenaDatosItemComun();
        comun.setHoraInicio(item.getHoraInicio());
        comun.setHoraFin(item.getHoraFin());
        eventosDAO.insert(comun);

        ItemDetalleDTO itemDetalleComun = new ItemDetalleDTO();
        itemDetalleComun.setItem(comun);
        itemDetalleComun.setInicio(comun.getHoraInicio());
        itemDetalleComun.setFin(comun.getHoraFin());
        eventosDAO.insert(itemDetalleComun);

        calendarIni.set(Calendar.HOUR, 15);
        calendarIni.set(Calendar.MINUTE, 0);

        calendarFin.set(Calendar.HOUR, 17);
        calendarFin.set(Calendar.MINUTE, 0);

        Evento evento = eventosDAO.updateHorasEventoDetalleManual(item.getId(),
                calendarIni.getTime(), calendarFin.getTime());
        System.out.println(evento.getInicio());

        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);
        Assert.assertEquals(calendarIni.getTime(), item.getHoraInicio());

        List<ItemDetalleDTO> detalleList = eventosDAO.get(ItemDetalleDTO.class,
                "item_id=" + item.getId());
        List<ItemDetalleDTO> detalleComunList = eventosDAO.get(ItemDetalleDTO.class, "item_id="
                + comun.getId());

        Assert.assertEquals(detalleList.get(0).getInicio(), detalleComunList.get(0).getInicio());
    }

}
