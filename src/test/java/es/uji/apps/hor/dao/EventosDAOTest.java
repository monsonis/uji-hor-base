package es.uji.apps.hor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import es.uji.apps.hor.db.AsignaturaComunDTO;
import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.ItemComunDTO;
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
    public void getEventosDeUnCursoTest()
    {
        eventosDAO.insert(item);

        List<Evento> listaEventos = eventosDAO.getEventosDeUnCurso(estudio.getId(), new Long(1),
                semestre.getId(), "A");
        Assert.assertTrue(listaEventos.size() > 0);
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
    @Ignore
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

        // eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), aulaPlanificacion.getId(), false);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertEquals(aulaPlanificacion, item.getAulaPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertEquals(item.getAulaPlanificacion(), comun.getAulaPlanificacion());
    }

    @Test
    @Ignore
    public void desasignaAulaPlanificacionEventoConAsignaturasComunesTest()
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        rellenaDatosTestsConAulas();

        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        // eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), null, false);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertNull(item.getAulaPlanificacion());

        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);
        Assert.assertEquals(item.getAulaPlanificacion(), comun.getAulaPlanificacion());
    }

    @Test
    @Ignore
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

        // eventosDAO.actualizaAulaAsignadaAEvento(item.getId(), aulaPlanificacion.getId(), true);
        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);

        Assert.assertEquals(aulaPlanificacion, item.getAulaPlanificacion());

        grupoComun = eventosDAO.get(ItemDTO.class, grupoComun.getId()).get(0);

        Assert.assertEquals(item.getAulaPlanificacion(), grupoComun.getAulaPlanificacion());
    }

}
