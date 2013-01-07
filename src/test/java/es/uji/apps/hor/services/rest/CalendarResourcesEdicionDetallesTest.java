package es.uji.apps.hor.services.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import es.uji.commons.rest.UIEntity;

public class CalendarResourcesEdicionDetallesTest extends AbstractCalendarResourceTest
{
    @Test
    @Transactional
    public void cambiaFechasDetalleManualDeUnEvento() throws Exception
    {
        JSONObject nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
        nuevosDatos.put("detalle_manual", "on");
        nuevosDatos.put("fecha_detalle_manual_int",
                "[\"10/10/2012 09:00:00\", \"17/10/2012 09:00:00\"]");

        llamaServicioModificacionEventoGenerico1(nuevosDatos);

        String inicioPeriodo = "2012-10-01";
        String finPeriodo = "2012-10-28";
        List<UIEntity> listaEventos = getEventosDetalladosEnRangoDeFechas(inicioPeriodo, finPeriodo);

        assertThat(listaEventos, hasSize(8));

    }

    @Test
    @Transactional
    public void cambiaInformacionRepeticionDeUnEvento() throws Exception
    {
        JSONObject nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
        String nuevaStartDateRep = "20/12/2012";
        String repetirCada2 = "2";
        String tipoSeleccionFechaFinEsRepeticiones = "R";
        String numeroRepeticiones3 = "3";
        nuevosDatos.put("detalle_manual", "off");
        nuevosDatos.put("start_date_rep", nuevaStartDateRep);
        nuevosDatos.put("repetir_cada", repetirCada2);
        nuevosDatos.put("seleccionRadioFechaFin", tipoSeleccionFechaFinEsRepeticiones);
        nuevosDatos.put("end_rep_number_comp", numeroRepeticiones3);

        llamaServicioModificacionEventoGenerico1(nuevosDatos);

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
        JSONObject nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
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

        llamaServicioModificacionEventoGenerico1(nuevosDatos);

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
        JSONObject nuevosDatos = creaJSONBasicoDelEventoGenericoConDetalle1();
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

        llamaServicioModificacionEventoGenerico1(nuevosDatos);

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

    private void llamaServicioModificacionEventoGenerico1(JSONObject nuevosDatos)
    {
        resource.path("calendario/eventos/generica/1").accept(MediaType.APPLICATION_JSON)
                .put(nuevosDatos);
    }

    private JSONObject creaJSONBasicoDelEventoGenericoConDetalle1() throws JSONException
    {
        String fechaInicio = "2012-10-10T09:00:00";
        String fechaFin = "2012-10-10T11:00:00";

        JSONObject entity = new JSONObject();
        entity.put("id", "1");
        entity.put("posteo_detalle", "1");
        entity.put("start", fechaInicio);
        entity.put("end", fechaFin);
        return entity;
    }
}
