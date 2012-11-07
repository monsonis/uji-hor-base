package es.uji.apps.hor.services.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.RangoHorarioFueradeLimites;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.GrupoHorario;
import es.uji.apps.hor.services.CalendariosService;
import es.uji.apps.hor.services.EventosService;
import es.uji.apps.hor.services.GrupoHorarioService;
import es.uji.commons.rest.ParamUtils;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Path("calendario")
public class CalendarResource
{
    @InjectParam
    private EventosService eventosService;

    @InjectParam
    private CalendariosService calendariosService;

    @InjectParam
    private GrupoHorarioService grupoHorarioService;

    private SimpleDateFormat hourDateFormat;
    private SimpleDateFormat shortDateFormat;
    private SimpleDateFormat dateFormat;

    public CalendarResource()
    {
        shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @GET
    @Path("config")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getConfiguracion(@QueryParam("estudioId") String estudioId,
            @QueryParam("cursoId") String cursoId, @QueryParam("semestreId") String semestreId,
            @QueryParam("grupoId") String grupoId) throws RegistroNoEncontradoException
    {

        ParamUtils.checkNotNull(estudioId, cursoId, semestreId, grupoId);

        GrupoHorario grupoHorario = grupoHorarioService.getHorarioById(
                ParamUtils.parseLong(estudioId), ParamUtils.parseLong(cursoId),
                ParamUtils.parseLong(semestreId), grupoId);

        return grupoHorarioToUI(grupoHorario);
    }

    @POST
    @Path("config")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> guardaConfiguracion(UIEntity entity) throws ParseException,
            RangoHorarioFueradeLimites
    {
        Long estudioId = ParamUtils.parseLong(entity.get("estudioId"));
        Long cursoId = ParamUtils.parseLong(entity.get("cursoId"));
        Long semestreId = ParamUtils.parseLong(entity.get("semestreId"));
        String grupoId = entity.get("grupoId");

        Calendar inicio = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();

        String horaInicio = entity.get("horaInicio");
        String horaFin = entity.get("horaFin");

        inicio.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaInicio.split(":")[0]));
        inicio.set(Calendar.MINUTE, Integer.parseInt(horaInicio.split(":")[1]));
        fin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaFin.split(":")[0]));
        fin.set(Calendar.MINUTE, Integer.parseInt(horaFin.split(":")[1]));

        GrupoHorario grupoHorario = grupoHorarioService.getHorarioById(estudioId, cursoId,
                semestreId, grupoId);

        grupoHorarioService.compruebaValidezRangoHorario(estudioId, cursoId, semestreId, grupoId,
                inicio.getTime(), fin.getTime());

        if (grupoHorario.getId() != null)
        {
            grupoHorario = grupoHorarioService.updateHorario(estudioId, cursoId, semestreId,
                    grupoId, inicio.getTime(), fin.getTime());
        }
        else
        {
            grupoHorario = grupoHorarioService.addHorario(estudioId, cursoId, semestreId, grupoId,
                    inicio.getTime(), fin.getTime());
        }

        return grupoHorarioToUI(grupoHorario);
    }

    @GET
    @Path("eventos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEventos(@QueryParam("estudioId") String estudioId,
            @QueryParam("cursoId") String cursoId, @QueryParam("startDate") String fechaInicio,
            @QueryParam("endDate") String fechaFin) throws ParseException
    {
        // ParamUtils.checkNotNull(estudioId, cursoId);

        estudioId = "224";
        cursoId = "1";

        Date rangoFechasInicio = shortDateFormat.parse(fechaInicio);
        Date rangoFechasFin = shortDateFormat.parse(fechaFin);

        List<Evento> eventos = eventosService.eventosDeUnEstudio(ParamUtils.parseLong(estudioId),
                ParamUtils.parseLong(cursoId), rangoFechasInicio, rangoFechasFin);

        return toUI(eventos);
    }

    @GET
    @Path("eventos/generica")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEventosGenerica(@QueryParam("estudioId") String estudioId,
            @QueryParam("cursoId") String cursoId, @QueryParam("semestreId") String semestreId,
            @QueryParam("grupoId") String grupoId,
            @QueryParam("calendariosIds") String calendariosIds) throws ParseException
    {
        ParamUtils.checkNotNull(estudioId, cursoId, semestreId, grupoId);

        String[] calendarios = calendariosIds.split(";");
        List<Long> calendariosList = new ArrayList<Long>();

        for (String calendario : calendarios)
        {
            calendario = calendario.trim();
            if (!calendario.equals(""))
            {
                calendariosList.add(ParamUtils.parseLong(calendario));
            }
        }

        List<Evento> eventos = new ArrayList<Evento>();

        if (calendariosList.size() != 0)
        {
            eventos = eventosService.eventosSemanaGenericaDeUnEstudio(
                    ParamUtils.parseLong(estudioId), ParamUtils.parseLong(cursoId),
                    ParamUtils.parseLong(semestreId), grupoId, calendariosList);
        }

        return toUI(eventos);
    }

    @PUT
    @Path("eventos/generica/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> updateEventoSemanaGenerica(UIEntity entity) throws ParseException,
            DuracionEventoIncorrectaException
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");

        Date inicio = formatter.parse(entity.get("start"));
        Date fin = formatter.parse(entity.get("end"));
        Date desdeElDia = null;
        Integer numeroIteraciones = null;
        Integer repetirCadaSemanas = null;
        Date hastaElDia = null;
        Integer posteoDetalle = Integer.parseInt(entity.get("posteo_detalle"));

        if (posteoDetalle.equals(0))
        {
            Evento evento = eventosService.modificaDiaYHoraGrupoAsignatura(
                    Long.parseLong(entity.get("id")), inicio, fin);
            return Collections.singletonList(UIEntity.toUI(evento));

        }
        else
        {
            if (entity.get("start_date_rep") != null && !entity.get("start_date_rep").equals(""))
            {
                desdeElDia = formatter2.parse(entity.get("start_date_rep"));
            }

            if (entity.get("repetir_cada") != null && !entity.get("repetir_cada").equals(""))
            {
                repetirCadaSemanas = Integer.parseInt(entity.get("repetir_cada"));
            }

            if (entity.get("seleccionRadioFechaFin").equals("R"))
            {
                numeroIteraciones = Integer.parseInt(entity.get("end_rep_number_comp"));
            }
            else if (entity.get("seleccionRadioFechaFin").equals("D"))
            {
                hastaElDia = formatter2.parse(entity.get("end_date_rep_comp"));
            }

            Evento evento = eventosService.modificaDetallesGrupoAsignatura(
                    Long.parseLong(entity.get("id")), inicio, fin, desdeElDia, numeroIteraciones,
                    repetirCadaSemanas, hastaElDia);
            return Collections.singletonList(UIEntity.toUI(evento));
        }

    }

    @DELETE
    @Path("eventos/generica/{id}")
    public Response deleteEventoSemanaGenerica(@PathParam("id") String eventoId)
            throws RegistroNoEncontradoException
    {
        eventosService.deleteEventoSemanaGenerica(Long.parseLong(eventoId));
        return Response.ok().build();
    }

    @POST
    @Path("eventos/generica/divide/{id}")
    public Response divideEventoSemanaGenerica(@PathParam("id") String eventoId)
            throws RegistroNoEncontradoException, EventoNoDivisibleException
    {
        eventosService.divideEventoSemanaGenerica(Long.parseLong(eventoId));
        return Response.ok().build();
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
            eventoUI.put("repetir_cada", evento.getRepetirCadaSemanas());
            eventoUI.put("start_date_rep", evento.getDesdeElDia());
            eventoUI.put("end_date_rep_comp", evento.getHastaElDia());
            eventoUI.put("end_rep_number_comp", evento.getNumeroIteraciones());

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

    private List<UIEntity> grupoHorarioToUI(GrupoHorario grupoHorario)
    {
        UIEntity grupoHorarioUI = new UIEntity();
        grupoHorarioUI.put("estudioId", grupoHorario.getEstudioId());
        grupoHorarioUI.put("cursoId", grupoHorario.getCursoId());
        grupoHorarioUI.put("semestreId", grupoHorario.getSemestreId());
        grupoHorarioUI.put("grupoId", grupoHorario.getGrupoId());
        grupoHorarioUI.put("horaFin", grupoHorario.getHoraFin());
        grupoHorarioUI.put("horaInicio", grupoHorario.getHoraInicio());

        return Collections.singletonList(grupoHorarioUI);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCalendarios()
    {
        List<UIEntity> calendars = new ArrayList<UIEntity>();

        List<Calendario> calendarios = calendariosService.getCalendarios();

        int i = 1;
        for (Calendario calendario : calendarios)
        {
            UIEntity calendar = new UIEntity();
            calendar.put("id", calendario.getId());
            calendar.put("title", calendario.getNombre());
            calendar.put("color", 20 + i);

            calendars.add(calendar);
            i++;
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

    @GET
    @Path("eventos/detalle/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEventosDetalleByEventoId(@PathParam("id") String eventoId)
    {

        List<Evento> eventosDetalle = eventosService.getEventosDetalleByEventoId(ParamUtils
                .parseLong(eventoId));

        return toUI(eventosDetalle);
    }
}