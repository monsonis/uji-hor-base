package es.uji.apps.hor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.db.AsignaturaComunDTO;
import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.db.TipoEstudioDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class GrupoAsignaturaDAOTest
{
    @Autowired
    private GrupoAsignaturaDAO grupoAsignaturaDAO;

    @Autowired
    private EventosDAO eventosDAO;

    private ItemDTO item;
    private ItemDTO comun;

    private CentroDTO centro;
    private TipoEstudioDTO tipoEstudio;
    private EstudioDTO estudio;
    private DiaSemanaDTO diaSemana;
    private SemestreDTO semestre;
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
    public void asignaDiaYHoraPorDefectoConComunesTest()
    {
        item.setComun(new Long(1));
        eventosDAO.insert(item);

        rellenaDatosItemComun();
        eventosDAO.insert(comun);

        grupoAsignaturaDAO.asignaDiaYHoraPorDefecto(item.getId());

        item = eventosDAO.get(ItemDTO.class, item.getId()).get(0);
        comun = eventosDAO.get(ItemDTO.class, comun.getId()).get(0);

        Assert.assertNotNull(item.getHoraFin());
        Assert.assertEquals(item.getHoraInicio(), comun.getHoraInicio());
    }
}
