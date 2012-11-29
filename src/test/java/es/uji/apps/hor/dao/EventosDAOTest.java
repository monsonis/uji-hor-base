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

import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.model.Evento;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class EventosDAOTest
{
    @Autowired
    private EventosDAO eventosDAO;
    
    @Autowired
    private GrupoAsignaturaDAO grupoAsignaturaDAO;

    private ItemDTO item;

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

        String fechaIniStr = "31/07/2012 10:00";
        String fechaFinStr = "31/07/2012 12:00";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaInicio = sdf.parse(fechaIniStr);
        Date fechaFin = sdf.parse(fechaFinStr);

        Evento evento = eventosDAO.modificaDiaYHoraGrupoAsignatura(item.getId(), fechaInicio,
                fechaFin);
        Assert.assertEquals(fechaInicio, evento.getInicio());
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

        ItemDTO itemDTO = eventosDAO.get(ItemDTO.class, item.getId()).get(0);
        Assert.assertNull(itemDTO.getDiaSemana());
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

}
