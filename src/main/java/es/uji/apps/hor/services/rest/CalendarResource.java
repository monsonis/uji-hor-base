package es.uji.apps.hor.services.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.services.ConsultaEventosService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;

@Path("calendario")
public class CalendarResource
{
    @InjectParam
    private ConsultaEventosService consultaEventos;

    private SimpleDateFormat shortDateFormat;
    private SimpleDateFormat dateFormat;

    public CalendarResource()
    {
        shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @GET
    @Path("eventos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEventos(@QueryParam("estudioId") String estudioId,
            @QueryParam("cursoId") String cursoId, @QueryParam("startDate") String fechaInicio,
            @QueryParam("endDate") String fechaFin) throws ParseException
    {
        //ParamUtils.checkNotNull(estudioId, cursoId);
        
        estudioId = "224";
        cursoId = "1";

        Date rangoFechasInicio = shortDateFormat.parse(fechaInicio);
        Date rangoFechasFin = shortDateFormat.parse(fechaFin);

        List<Evento> eventos = consultaEventos.eventosDeUnEstudio(ParamUtils.parseLong(estudioId),
                ParamUtils.parseLong(cursoId), rangoFechasInicio, rangoFechasFin);

        return toUI(eventos);
    }

    private List<UIEntity> toUI(List<Evento> eventos)
    {
        List<UIEntity> eventosUI = new ArrayList<UIEntity>();

        for (Evento evento : eventos)
        {
            UIEntity eventoUI = new UIEntity();
            eventoUI.put("id", evento.getId());
            eventoUI.put("cid", evento.getCalendario().getId());
            eventoUI.put("title", evento.getTitulo());
            eventoUI.put("notes", evento.getObservaciones());

            if (evento.getInicio() != null)
            {
                eventoUI.put("start", dateFormat.format(evento.getInicio()));
            }

            if (evento.getFin() != null)
            {
                eventoUI.put("end", dateFormat.format(evento.getFin()));
            }

            eventosUI.add(eventoUI);
        }

        return eventosUI;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCalendarios()
    {
        List<UIEntity> calendars = new ArrayList<UIEntity>();

        for (int i = 1; i < 6; i++)
        {
            UIEntity calendar = new UIEntity();
            calendar.put("id", i);
            calendar.put("title", "Calendario " + i);
            calendar.put("color", 20 + i);

            calendars.add(calendar);
        }

        return calendars;
    }

    @POST
    @Path("eventos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> addEvento(UIEntity evento)
    {
        evento.put("id", new Random().nextInt());
        return Collections.singletonList(evento);
    }

    @PUT
    @Path("eventos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> updateEvento(UIEntity evento)
    {
        return Collections.singletonList(evento);
    }
}