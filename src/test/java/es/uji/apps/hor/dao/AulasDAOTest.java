package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.db.TipoEstudioDTO;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AulasDAOTest
{
    @Autowired
    private AulaDAO aulasDAO;

    private CentroDTO centro;
    private TipoEstudioDTO tipoEstudio;
    private EstudioDTO estudio;
    private AulaDTO aula;

    private List<AulaPlanificacionDTO> aulas = new ArrayList<AulaPlanificacionDTO>();

    @Before
    public void rellenaDatos()
    {
        centro = new CentroDTO();
        centro.setNombre("Centro de prueba");

        tipoEstudio = new TipoEstudioDTO();
        tipoEstudio.setId("PR");
        tipoEstudio.setNombre("Pruebas");

        estudio = new EstudioDTO();
        estudio.setNombre("Estudio de prueba");
        estudio.setTiposEstudio(tipoEstudio);
        estudio.setCentro(centro);
        estudio.setOficial(new Long(1));

        aula = new AulaDTO();
        aula.setNombre("Aula1000");
        aula.setCentro(centro);

        AulaPlanificacionDTO aulaPlan = new AulaPlanificacionDTO();
        aulaPlan.setNombre("Aula1000-1");
        aulaPlan.setAula(aula);

        aulas.add(aulaPlan);

        aulaPlan = new AulaPlanificacionDTO();
        aulaPlan.setNombre("Aula1000-2");
        aulaPlan.setAula(aula);
        aulaPlan.setSemestreId(new Long(1));

        aulas.add(aulaPlan);

        aulaPlan = new AulaPlanificacionDTO();
        aulaPlan.setNombre("Aula1000-3");
        aulaPlan.setAula(aula);
        aulaPlan.setCursoId(new Long(1));

        aulas.add(aulaPlan);

        aulaPlan = new AulaPlanificacionDTO();
        aulaPlan.setNombre("Aula1000-3");
        aulaPlan.setAula(aula);
        aulaPlan.setSemestreId(new Long(2));
        aulaPlan.setCursoId(new Long(2));

        aulas.add(aulaPlan);
    }

    private void insertaDatos()
    {
        centro = aulasDAO.insert(centro);
        tipoEstudio = aulasDAO.insert(tipoEstudio);
        estudio = aulasDAO.insert(estudio);
        aulasDAO.insert(aula);

        for (AulaPlanificacionDTO aulaPlan : aulas)
        {
            aulaPlan.setEstudioId(estudio.getId());
            aulasDAO.insert(aulaPlan);
        }
    }

    @Test
    public void recuperaAulasAsignadasAUnEstudioSinSemestreNiGrupoTest()
    {
        insertaDatos();
        List<AulaPlanificacion> aulas = aulasDAO.getAulasAsignadasToEstudio(estudio.getId(), null,
                null);

        Assert.assertEquals(1, aulas.size());
    }

    @Test
    public void recuperaAulasAsignadasAUnEstudionConSemestreTest()
    {
        insertaDatos();
        List<AulaPlanificacion> aulas = aulasDAO.getAulasAsignadasToEstudio(estudio.getId(),
                new Long(1), null);

        Assert.assertEquals(2, aulas.size());
    }

    @Test
    public void recuperaAulasAsignadasAUnEstudioConCursoTest()
    {
        insertaDatos();
        List<AulaPlanificacion> aulas = aulasDAO.getAulasAsignadasToEstudio(estudio.getId(), null,
                new Long(1));

        Assert.assertEquals(2, aulas.size());
    }

    @Test
    public void recuperaAulasAsignadasAUnEstudioConSemestreYCursoTest()
    {
        insertaDatos();
        List<AulaPlanificacion> aulas = aulasDAO.getAulasAsignadasToEstudio(estudio.getId(),
                new Long(2), new Long(2));

        Assert.assertEquals(2, aulas.size());
    }

    @Test
    public void asignaAulaAUnEstudioTest() throws RegistroNoEncontradoException
    {
        insertaDatos();

        // Creamos una nueva aula

        aula = new AulaDTO();
        aula.setNombre("Aula2000");
        aula.setCentro(centro);
        aula.setEdificio("EDI");

        aula = aulasDAO.insert(aula);

        AulaPlanificacion aulaPlan = aulasDAO.asignaAulaToEstudio(estudio.getId(), aula.getId(),
                null, null);

        AulaPlanificacionDTO aux = aulasDAO.get(AulaPlanificacionDTO.class, aulaPlan.getId())
                .get(0);

        Assert.assertEquals(aulaPlan.getNombre(), aux.getNombre());
        Assert.assertEquals(aula.getEdificio(), aux.getAula().getEdificio());
    }

    @Test
    public void eliminaAulaPlanificadaTest() throws RegistroConHijosException
    {
        insertaDatos();

        AulaPlanificacionDTO aulaPlan = aulas.get(0);

        aulasDAO.deleteAulaAsignadaToEstudio(aulaPlan.getId());

        List<AulaPlanificacionDTO> aux = aulasDAO.get(AulaPlanificacionDTO.class, aulaPlan.getId());

        Assert.assertEquals(0, aux.size());
    }

    @Test(expected = JpaSystemException.class)
    public void eliminaAulaPlanificadaConEventoAsignadoTest() throws RegistroConHijosException
    {
        insertaDatos();

        // Creamos un item al que asignar el aula

        SemestreDTO semestre = new SemestreDTO();
        semestre.setNombre("Prueba");
        semestre = aulasDAO.insert(semestre);

        ItemDTO item = new ItemDTO();
        item.setAsignaturaId("PA");
        item.setEstudio(estudio);
        item.setCursoId(new Long(1));
        item.setCaracterId("PC");
        item.setSemestre(semestre);
        item.setComun(new Long(0));
        item.setGrupoId("PG");
        item.setTipoSubgrupoId("PTS");
        item.setSubgrupoId(new Long(0));
        item.setDetalleManual(false);

        AulaPlanificacionDTO aula = aulas.get(0);
        item.setAulasPlanificacion(aula);
        item = aulasDAO.insert(item);

        aulasDAO.deleteAulaAsignadaToEstudio(aulas.get(0).getId());

        AulaPlanificacionDTO aux = aulasDAO.get(AulaPlanificacionDTO.class, aula.getId()).get(0);

        System.out.println(aux.getNombre());
    }
}
