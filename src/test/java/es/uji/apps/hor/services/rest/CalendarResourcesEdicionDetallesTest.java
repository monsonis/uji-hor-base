package es.uji.apps.hor.services.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.ClientResponse;

import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.DepartamentoBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.PersonaBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.DepartamentoDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Departamento;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.rest.UIEntity;

public class CalendarResourcesEdicionDetallesTest extends AbstractCalendarResourceTest
{

    @Autowired
    protected PersonaDAO personaDAO;

    @Autowired
    protected CentroDAO centroDAO;

    @Autowired
    protected DepartamentoDAO departamentoDAO;

    Long estudioId;
    
    @Before
    @Transactional
    public void creaDatosIniciales() throws Exception
    {
        TipoEstudio tipoEstudio = new TipoEstudioBuilder().withId("G").withNombre("Grau").build();

        Centro centro = new CentroBuilder(centroDAO).withNombre("Centro 1").withId(new Long(1))
                .build();
        Departamento departamento = new DepartamentoBuilder(departamentoDAO)
                .withNombre("Departamento1").withCentro(centro).build();

        Estudio estudio = new EstudioBuilder(estudiosDao).withNombre("Grau en Psicologia")
                .withTipoEstudio(tipoEstudio).build();
        
        estudioId = estudio.getId();

        Persona persona = new PersonaBuilder(personaDAO).withId(new Long(1))
                .withNombre("Persona 1").withEmail("persona@uji.es").withActividadId("HOLA")
                .withDepartamento(departamento).withCentroAutorizado(centro)
                .withEstudioAutorizado(estudio).build();
    }

    @Test
    @Transactional
    public void cambiaFechasDetalleManualDeUnEvento() throws Exception
    {
        Map<String, String> nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
        nuevosDatos.put("detalle_manual", "on");
        nuevosDatos.put("fecha_detalle_manual_int",
                "[\"10/10/2012 09:00:00\", \"17/10/2012 09:00:00\"]");

        llamaServicioModificacionEventoGenerico1(nuevosDatos, estudioId);

        String inicioPeriodo = "2012-10-01";
        String finPeriodo = "2012-10-28";
        List<UIEntity> listaEventos = getEventosDetalladosEnRangoDeFechas(inicioPeriodo, finPeriodo);

        assertThat(listaEventos, hasSize(8));

    }

    @Test
    @Transactional
    public void cambiaInformacionRepeticionDeUnEvento() throws Exception
    {
        Map<String, String> nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
        String nuevaStartDateRep = "20/12/2012";
        String repetirCada2 = "2";
        String tipoSeleccionFechaFinEsRepeticiones = "R";
        String numeroRepeticiones3 = "3";
        nuevosDatos.put("detalle_manual", "off");
        nuevosDatos.put("start_date_rep", nuevaStartDateRep);
        nuevosDatos.put("repetir_cada", repetirCada2);
        nuevosDatos.put("seleccionRadioFechaFin", tipoSeleccionFechaFinEsRepeticiones);
        nuevosDatos.put("end_rep_number_comp", numeroRepeticiones3);

        llamaServicioModificacionEventoGenerico1(nuevosDatos, estudioId);

        UIEntity entityActualizada = getDatosEventoGenerico("1");
        assertThat(valorPropiedadTipoFechaDeUIEntity(entityActualizada, "start_date_rep"),
                is(nuevaStartDateRep));
        assertThat(valorPropiedadDeUIEntity(entityActualizada, "repetir_cada"), is(repetirCada2));
        assertThat(valorPropiedadDeUIEntity(entityActualizada, "end_rep_number_comp"),
                is(numeroRepeticiones3));
        assertThat(valorPropiedadDeUIEntity(entityActualizada, "end_date_rep_comp"), is(""));
    }

    @Test
    @Transactional
    public void cambiaFinRepeticionesEventoYEnviaTambienFechaFin() throws Exception
    {
        Map<String, String> nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
        String nuevaStartDateRep = "20/12/2012";
        String nuevaEndDateRep = "20/04/2013";
        String repetirCada2 = "2";
        String tipoSeleccionFechaFinEsRepeticiones = "R";
        String numeroRepeticiones3 = "3";
        nuevosDatos.put("detalle_manual", "off");
        nuevosDatos.put("start_date_rep", nuevaStartDateRep);
        nuevosDatos.put("repetir_cada", repetirCada2);
        nuevosDatos.put("seleccionRadioFechaFin", tipoSeleccionFechaFinEsRepeticiones);
        nuevosDatos.put("end_rep_number_comp", numeroRepeticiones3);
        nuevosDatos.put("end_date_rep_comp", nuevaEndDateRep);

        llamaServicioModificacionEventoGenerico1(nuevosDatos, estudioId);

        UIEntity entityActualizada = getDatosEventoGenerico("1");
        assertThat(valorPropiedadTipoFechaDeUIEntity(entityActualizada, "start_date_rep"),
                is(nuevaStartDateRep));
        assertThat(valorPropiedadDeUIEntity(entityActualizada, "repetir_cada"), is(repetirCada2));
        assertThat(valorPropiedadDeUIEntity(entityActualizada, "end_rep_number_comp"),
                is(numeroRepeticiones3));
        assertThat(valorPropiedadTipoFechaDeUIEntity(entityActualizada, "end_date_rep_comp"),
                is(""));
    }

    @Test
    @Transactional
    public void cambiaFechaFinRepeticionesEventoYEnviaTambienNumeroRepeticiones() throws Exception
    {
        Map<String, String> nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
        String nuevaStartDateRep = "20/12/2012";
        String nuevaEndDateRep = "20/04/2013";
        String repetirCada2 = "2";
        String tipoSeleccionFechaFinEsRepeticiones = "D";
        String numeroRepeticiones3 = "3";
        nuevosDatos.put("detalle_manual", "off");
        nuevosDatos.put("start_date_rep", nuevaStartDateRep);
        nuevosDatos.put("repetir_cada", repetirCada2);
        nuevosDatos.put("seleccionRadioFechaFin", tipoSeleccionFechaFinEsRepeticiones);
        nuevosDatos.put("end_rep_number_comp", numeroRepeticiones3);
        nuevosDatos.put("end_date_rep_comp", nuevaEndDateRep);

        llamaServicioModificacionEventoGenerico1(nuevosDatos, estudioId);

        UIEntity entityActualizada = getDatosEventoGenerico("1");
        assertThat(valorPropiedadTipoFechaDeUIEntity(entityActualizada, "start_date_rep"),
                is(nuevaStartDateRep));
        assertThat(valorPropiedadDeUIEntity(entityActualizada, "repetir_cada"), is(repetirCada2));
        assertThat(valorPropiedadDeUIEntity(entityActualizada, "end_rep_number_comp"), is(""));
        assertThat(valorPropiedadTipoFechaDeUIEntity(entityActualizada, "end_date_rep_comp"),
                is(nuevaEndDateRep));
    }

    private String valorPropiedadDeUIEntity(UIEntity entity, String clave)
    {
        if (entity.keySet().contains(clave))
        {
            return entity.get(clave).replace("\"", "");
        }
        else
            return "";
    }

    private String valorPropiedadTipoFechaDeUIEntity(UIEntity entity, String clave)
    {
        String stringFecha = valorPropiedadDeUIEntity(entity, clave);
        if (stringFecha.length() > 0)
            return stringFecha.split(" ")[0];
        else
            return "";
    }

    private void llamaServicioModificacionEventoGenerico1(Map<String, String> nuevosDatos, Long estudioId)
    {
        resource.path("calendario/eventos/generica/" + estudioId.toString()).type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, nuevosDatos);
    }

    private Map<String, String> creaJSONBasicoDelEventoGenericoConDetalle1() throws JSONException
    {
        String fechaInicio = "2012-10-10T09:00:00";
        String fechaFin = "2012-10-10T11:00:00";

        Map<String, String> entity = new HashMap<String, String>();
        entity.put("id", "1");
        entity.put("posteo_detalle", "1");
        entity.put("start", fechaInicio);
        entity.put("end", fechaFin);

        return entity;
    }
}
