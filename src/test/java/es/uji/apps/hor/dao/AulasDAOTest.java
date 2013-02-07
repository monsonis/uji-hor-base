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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.builders.AreaEdificioBuilder;
import es.uji.apps.hor.builders.AulaBuilder;
import es.uji.apps.hor.builders.AulaPlanificacionBuilder;
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EdificioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.PlantaEdificioBuilder;
import es.uji.apps.hor.builders.SemestreBuilder;
import es.uji.apps.hor.builders.TipoAulaBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;
import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.AulaPlanificacionDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoAula;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = false)
public class AulasDAOTest
{
    @Autowired
    private AulaDAO aulasDAO;
    
    @Autowired
    private CentroDAO centroDAO;
    
    @Autowired
    private TipoEstudioDAO tipoEstudioDAO;
    
    @Autowired
    private EstudiosDAO estudioDAO;
    
    private Semestre semestre;
    
    private Centro centro;
    private TipoEstudio tipoEstudio;
    private Estudio estudio;
    private Aula aula;

    private List<AulaPlanificacion> aulas = new ArrayList<AulaPlanificacion>();

    @Before
    @Transactional
    public void rellenaDatos()
    {
        centro = new CentroBuilder(centroDAO).withNombre("Centro de prueba").build();
        tipoEstudio = new TipoEstudioBuilder(tipoEstudioDAO).withId("PR").withNombre("Pruebas").build();

        estudio = new EstudioBuilder(estudioDAO).withNombre("Estudio de prueba").withTipoEstudio(tipoEstudio).withCentro(centro).withOficial(true).build();

        Edificio edificio = new EdificioBuilder().withNombre("Edificio 1").withCentro(centro).build();
        PlantaEdificio plantaEdificio = new PlantaEdificioBuilder().withNombre("Planta 1").withEdificio(edificio).build();
        AreaEdificio areaEdificio = new AreaEdificioBuilder().withNombre("Area 1").withEdificio(edificio).build();
        TipoAula tipoAula = new TipoAulaBuilder().withNombre("Tipo Aula 1").withEdificio(edificio).build();

        aula = new AulaBuilder(aulasDAO).withNombre("Aula1000").withCentro(centro).withEdificio(edificio).withPlanta(plantaEdificio).withArea(areaEdificio).withTipo(tipoAula).build();
        Aula otraAula = new AulaBuilder(aulasDAO).withNombre("Aula2000").withCentro(centro).withEdificio(edificio).withPlanta(plantaEdificio).withArea(areaEdificio).withTipo(tipoAula).build();

        semestre = new SemestreBuilder().withSemestre(new Long(1)).build();
        Semestre semestre2 = new SemestreBuilder().withSemestre(new Long(2)).build();
        
        AulaPlanificacion aulaPlanificacion = new AulaPlanificacionBuilder(aulasDAO).withAula(aula).withEstudio(estudio).withSemestre(semestre).build();       
        AulaPlanificacion aulaPlanificacion2 = new AulaPlanificacionBuilder(aulasDAO).withAula(otraAula).withEstudio(estudio).withSemestre(semestre).build();
        AulaPlanificacion aulaPlanificacion3 = new AulaPlanificacionBuilder(aulasDAO).withAula(aula).withEstudio(estudio).withSemestre(semestre2).build();
        AulaPlanificacion aulaPlanificacion4  = new AulaPlanificacionBuilder(aulasDAO).withAula(otraAula).withEstudio(estudio).withSemestre(semestre2).build();
        
        aulas.add(aulaPlanificacion);
        aulas.add(aulaPlanificacion2);
        aulas.add(aulaPlanificacion3);
        aulas.add(aulaPlanificacion4);
    }

    @Test
    public void recuperaAulasAsignadasAUnEstudionTest()
    {
        List<AulaPlanificacion> aulas = aulasDAO.getAulasAsignadasToEstudio(estudio.getId(),
                new Long(1));

        Assert.assertEquals(1, aulas.size());
    }

    @Test
    public void asignaAulaAUnEstudioTest() throws RegistroNoEncontradoException,
            AulaYaAsignadaAEstudioException
    {

        // Creamos una nueva aula
        Edificio edificio = new EdificioBuilder().withNombre("Edificio 2").withCentro(centro).build();
        PlantaEdificio plantaEdificio = new PlantaEdificioBuilder().withNombre("Planta 2").withEdificio(edificio).build();
        AreaEdificio areaEdificio = new AreaEdificioBuilder().withNombre("Area 2").withEdificio(edificio).build();
        TipoAula tipoAula = new TipoAulaBuilder().withNombre("Tipo Aula 2").withEdificio(edificio).build();
        Aula nuevaAula = new AulaBuilder(aulasDAO).withNombre("Aula2000").withCentro(centro).withEdificio(edificio).withPlanta(plantaEdificio).withArea(areaEdificio).withTipo(tipoAula).build();

        AulaPlanificacion aulaPlan = aulasDAO.asignaAulaToEstudio(estudio.getId(), nuevaAula.getId(), semestre.getSemestre());

        AulaPlanificacion aux = aulasDAO.getAulaPlanificacionByAulaEstudioSemestre(estudio.getId(), nuevaAula.getId(), semestre.getSemestre());

        Assert.assertEquals(aulaPlan.getAula().getId(), aux.getAula().getId());
    }

    @Test(expected = AulaYaAsignadaAEstudioException.class)
    public void asignaAulaYaAsignadaAUnEstudioTest() throws RegistroNoEncontradoException,
            AulaYaAsignadaAEstudioException
    {

        AulaPlanificacion aulaPlan = aulasDAO.asignaAulaToEstudio(estudio.getId(), aula.getId(), semestre.getSemestre());
    }

    @Test(expected = RegistroNoEncontradoException.class)
    public void eliminaAulaPlanificadaTest() throws RegistroConHijosException, RegistroNoEncontradoException
    {

        aulasDAO.deleteAulaAsignadaToEstudio(aula.getId(), estudio.getId(), semestre.getSemestre());
        aulasDAO.getAulaPlanificacionByAulaEstudioSemestre(aula.getId(), estudio.getId(), semestre.getSemestre());

    }

    @Test(expected = JpaSystemException.class)
    public void eliminaAulaPlanificadaConEventoAsignadoTest() throws RegistroConHijosException
    {

        // Creamos un item al que asignar el aula

        SemestreDTO semestre = new SemestreDTO();
        semestre.setNombre("Prueba");
        semestre = aulasDAO.insert(semestre);

        ItemDTO item = new ItemDTO();
        // item.setAsignatura("PA");
        // item.setEstudio(estudio);
        item.setCursoId(new Long(1));
        item.setCaracterId("PC");
        item.setSemestre(semestre);
        item.setComun(new Long(0));
        item.setGrupoId("PG");
        item.setTipoSubgrupoId("PTS");
        item.setSubgrupoId(new Long(0));
        item.setDetalleManual(false);

//        AulaDTO aula = aulas.get(0);
//        item.setAula(aula);
//        item = aulasDAO.insert(item);
//
//        aulasDAO.deleteAulaAsignadaToEstudio(aulas.get(0).getId());

        AulaPlanificacionDTO aux = aulasDAO.get(AulaPlanificacionDTO.class, aula.getId()).get(0);

    }
}
