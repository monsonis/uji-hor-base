package es.uji.apps.hor.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.uji.commons.rest.UIEntity;

@Path("calendario")
public class CalendarService
{
    @GET
    @Path("eventos")
    @Produces(MediaType.APPLICATION_JSON)    
    public List<UIEntity> getEventos()
    {
        List<UIEntity> events = new ArrayList<UIEntity>();
        
        for (int i=1 ; i<10 ; i++)
        {
            UIEntity event = new UIEntity();
            event.put("id", i);
            event.put("cid", (i % 2 == 0) ? 1 : 2);
            event.put("title", "Evento " + i);
            event.put("notes", "Comentarios");
            event.put("start", "2012-05-0" + i + "T10:00:00");
            event.put("end", "2012-05-0" + i + "T11:00:00");
            
            events.add(event);
        }
        
        return events;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCalendarios()
    {
        List<UIEntity> calendars = new ArrayList<UIEntity>();
        
        for (int i=1 ; i<3 ; i++)
        {
            UIEntity calendar = new UIEntity();
            calendar.put("id", i);
            calendar.put("title", "Evento " + i);
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