package es.uji.apps.hor.services.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.RangoHorarioFueradeLimites;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
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
    private static final String END_DATE_QUERY_PARAM = "endDate";
    private static final String START_DATE_QUERY_PARAM = "startDate";
    private static final String CALENDARIOS_IDS_QUERY_PARAM = "calendariosIds";
    private static final String GRUPO_ID_QUERY_PARAM = "grupoId";
    private static final String CURSO_ID_QUERY_PARAM = "cursoId";
    private static final String SEMESTRE_ID_QUERY_PARAM = "semestreId";
    private static final String ESTUDIO_ID_QUERY_PARAM = "estudioId";
    private static final String TIPO_ACCION_FORM_PARAM = "tipoAccion";
    private static final String AULA_ID_FORM_PARAM = "aulaId";
    private static final String ID_PATH_PARAM = "id";

    @InjectParam
    private EventosService eventosService;

    @InjectParam
    private CalendariosService calendariosService;

    @InjectParam
    private GrupoHorarioService grupoHorarioService;

    private SimpleDateFormat queryParamDateFormat;
    private SimpleDateFormat uIEntitydateFormat;

    public CalendarResource()
    {
        queryParamDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        uIEntitydateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @GET
    @Path("config")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getConfiguracion(@QueryParam(ESTUDIO_ID_QUERY_PARAM) String estudioId,
            @QueryParam(CURSO_ID_QUERY_PARAM) String cursoId,
            @QueryParam(SEMESTRE_ID_QUERY_PARAM) String semestreId,
            @QueryParam(GRUPO_ID_QUERY_PARAM) String grupoId) throws RegistroNoEncontradoException
    {

        ParamUtils.checkNotNull(estudioId, cursoId, semestreId, grupoId);

        GrupoHorario grupoHorario = grupoHorarioService.getHorario(ParamUtils.parseLong(estudioId),
                ParamUtils.parseLong(cursoId), ParamUtils.parseLong(semestreId), grupoId);

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
        inicio.set(Calendar.SECOND, 0);
        fin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaFin.split(":")[0]));
        fin.set(Calendar.MINUTE, Integer.parseInt(horaFin.split(":")[1]));
        fin.set(Calendar.SECOND, 0);

        GrupoHorario grupoHorario = grupoHorarioService.guardaConfiguracionGrupoHorario(estudioId,
                cursoId, semestreId, grupoId, inicio.getTime(), fin.getTime());

        return grupoHorarioToUI(grupoHorario);
    }

    @GET
    @Path("eventos/generica")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEventosGenerica(@QueryParam(ESTUDIO_ID_QUERY_PARAM) String estudioId,
            @QueryParam(CURSO_ID_QUERY_PARAM) String cursoId,
            @QueryParam(SEMESTRE_ID_QUERY_PARAM) String semestreId,
            @QueryParam(GRUPO_ID_QUERY_PARAM) String grupoId,
            @QueryParam(CALENDARIOS_IDS_QUERY_PARAM) String calendariosIds) throws ParseException
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

    @GET
    @Path("eventos/detalle")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEventosDetalle(@QueryParam(ESTUDIO_ID_QUERY_PARAM) String estudioId,
            @QueryParam(CURSO_ID_QUERY_PARAM) String cursoId,
            @QueryParam(SEMESTRE_ID_QUERY_PARAM) String semestreId,
            @QueryParam(GRUPO_ID_QUERY_PARAM) String grupoId,
            @QueryParam(CALENDARIOS_IDS_QUERY_PARAM) String calendariosIds,
            @QueryParam(START_DATE_QUERY_PARAM) String startDate,
            @QueryParam(END_DATE_QUERY_PARAM) String endDate) throws ParseException
    {
        ParamUtils.checkNotNull(estudioId, cursoId, semestreId, grupoId, startDate, endDate);

        Date rangoFechaInicio = queryParamDateFormat.parse(startDate);
        Date rangoFechaFin = queryParamDateFormat.parse(endDate);

        // Todos los eventos hasta el final del día
        Calendar c = Calendar.getInstance();
        c.setTime(rangoFechaFin);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        rangoFechaFin = c.getTime();

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

        List<EventoDetalle> eventosDetalle = new ArrayList<EventoDetalle>();

        if (calendariosList.size() != 0)
        {
            eventosDetalle = eventosService.eventosDetalleDeUnEstudio(
                    ParamUtils.parseLong(estudioId), ParamUtils.parseLong(cursoId),
                    ParamUtils.parseLong(semestreId), grupoId, calendariosList, rangoFechaInicio,
                    rangoFechaFin);
        }

        return eventosDetalletoUI(eventosDetalle);
    }

    private List<UIEntity> eventosDetalletoUI(List<EventoDetalle> eventosDetalle)
    {
        List<UIEntity> eventosUI = new ArrayList<UIEntity>();

        for (EventoDetalle eventoDetalle : eventosDetalle)
        {
            UIEntity eventoUI = new UIEntity();
            eventoUI.put("id", eventoDetalle.getId());
            eventoUI.put("title", eventoDetalle.getDescripcion());
            eventoUI.put("cid", eventoDetalle.getEvento().getCalendario().getId());

            if (eventoDetalle.getInicio() != null)
            {
                eventoUI.put("start", uIEntitydateFormat.format(eventoDetalle.getInicio()));
            }

            if (eventoDetalle.getFin() != null)
            {
                eventoUI.put("end", uIEntitydateFormat.format(eventoDetalle.getFin()));
            }

            eventosUI.add(eventoUI);
        }

        return eventosUI;
    }

    @PUT
    @Path("eventos/generica/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> updateEventoSemanaGenerica(UIEntity entity) throws ParseException,
            DuracionEventoIncorrectaException, JSONException, RegistroNoEncontradoException,
            EventoDetalleSinEventoException
    {
        DateFormat uIEntityDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date inicio = uIEntityDateFormat.parse(entity.get("start"));
        Date fin = uIEntityDateFormat.parse(entity.get("end"));
        Long idEvento = (Long.parseLong(entity.get("id")));

        if (!enviandoDatosDetalleEvento(entity))
        {
            Evento evento = eventosService.modificaDiaYHoraEvento(idEvento, inicio, fin);
            return toUI(Collections.singletonList(evento));
        }
        else
        {
            if (enviandoDetalleManual(entity))
            {
                List<Date> fechas = getListaFechasDetalleManual(entity);
                Evento evento = eventosService.updateEventoConDetalleManual(idEvento, fechas,
                        inicio, fin);
                return toUI(Collections.singletonList(evento));
            }
            else
            {
                Date desdeElDia = valorPropiedadDateDeUIEntity(entity, "start_date_rep");
                Integer repetirCadaSemanas = valorPropiedadIntegerDeUIEntity(entity, "repetir_cada");
                Integer numeroIteraciones = null;
                Date hastaElDia = null;
                if (finalizaRepeticionesPorNumero(entity))
                {
                    numeroIteraciones = valorPropiedadIntegerDeUIEntity(entity,
                            "end_rep_number_comp");
                }
                else if (finalizaRepeticionesPorFecha(entity))
                {
                    hastaElDia = valorPropiedadDateDeUIEntity(entity, "end_date_rep_comp");
                }

                Evento evento = eventosService.modificaDetallesGrupoAsignatura(idEvento, inicio,
                        fin, desdeElDia, numeroIteraciones, repetirCadaSemanas, hastaElDia, false);
                return toUI(Collections.singletonList(evento));
            }
        }

    }

    private boolean finalizaRepeticionesPorNumero(UIEntity entity)
    {
        String criterioFinalizacionRepeticiones = valorPropiedadDeUIEntity(entity,
                "seleccionRadioFechaFin");
        return criterioFinalizacionRepeticiones.equals("R");
    }

    private boolean finalizaRepeticionesPorFecha(UIEntity entity)
    {
        String criterioFinalizacionRepeticiones = valorPropiedadDeUIEntity(entity,
                "seleccionRadioFechaFin");
        return criterioFinalizacionRepeticiones.equals("D");
    }

    private Date valorPropiedadDateDeUIEntity(UIEntity entity, String clave) throws ParseException
    {
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        String valor = valorPropiedadDeUIEntity(entity, clave);
        if (valor == null)
        {
            return null;
        }
        else
        {
            return dateFormatter.parse(valor);
        }
    }

    private Integer valorPropiedadIntegerDeUIEntity(UIEntity entity, String clave)
    {
        String valor = valorPropiedadDeUIEntity(entity, clave);
        if (valor == null)
        {
            return null;
        }
        else
        {
            return Integer.parseInt(valor);
        }
    }

    private String valorPropiedadDeUIEntity(UIEntity entity, String clave)
    {
        if (entity.keySet().contains(clave) && entity.get(clave).length() > 0)
        {
            return entity.get(clave);
        }
        else
        {
            return null;
        }
    }

    private List<Date> getListaFechasDetalleManual(UIEntity entity) throws JSONException,
            ParseException
    {
        DateFormat formatoFechaManual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String strFechas = entity.get("fecha_detalle_manual_int");
        JSONArray array = new JSONArray(strFechas);

        List<Date> fechas = new ArrayList<Date>();
        for (int i = 0; i < array.length(); i++)
        {
            fechas.add(formatoFechaManual.parse((String) array.get(i)));
        }
        return fechas;
    }

    private boolean enviandoDatosDetalleEvento(UIEntity entity)
    {
        Integer posteoDetalle = Integer.parseInt(entity.get("posteo_detalle"));
        return posteoDetalle == 1;
    }

    private boolean enviandoDetalleManual(UIEntity entity)
    {
        return entity.get("detalle_manual") != null && entity.get("detalle_manual").equals("on");
    }

    @DELETE
    @Path("eventos/generica/{id}")
    public Response deleteEventoSemanaGenerica(@PathParam(ID_PATH_PARAM) String eventoId)
            throws RegistroNoEncontradoException
    {
        eventosService.deleteEventoSemanaGenerica(Long.parseLong(eventoId));
        return Response.ok().build();
    }

    @POST
    @Path("eventos/generica/divide/{id}")
    public Response divideEventoSemanaGenerica(@PathParam(ID_PATH_PARAM) String eventoId)
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
            eventoUI.put("detalle_manual", evento.hasDetalleManual());
            eventoUI.put("comunes", evento.getAsignatura().getComunes());

            if (evento.getAulaPlanificacion() != null)
            {
                eventoUI.put("aula_planificacion_id", evento.getAulaPlanificacion().getId());
            }

            if (evento.getInicio() != null)
            {
                eventoUI.put("start", uIEntitydateFormat.format(evento.getInicio()));
            }

            if (evento.getFin() != null)
            {
                eventoUI.put("end", uIEntitydateFormat.format(evento.getFin()));
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

    @GET
    @Path("eventos/docencia/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getEventosDocenciaByEventoId(@PathParam(ID_PATH_PARAM) String eventoId)
    {
        List<EventoDocencia> eventosDocencia = eventosService
                .getDiasDocenciaDeUnEventoByEventoId(ParamUtils.parseLong(eventoId));

        return UIEntity.toUI(eventosDocencia);
    }

    @PUT
    @Path("eventos/aula/evento/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> actualizaAulaAsignadaAEvento(@PathParam(ID_PATH_PARAM) String eventoId,
            @FormParam(AULA_ID_FORM_PARAM) String aulaId,
            @FormParam(TIPO_ACCION_FORM_PARAM) String tipoAccion)
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        boolean propagar = tipoAccion.equals("T");
        Long aula;
        try
        {
            aula = Long.parseLong(aulaId);
        }
        catch (Exception e)
        {
            aula = null;
        }

        List<Evento> eventos = eventosService.actualizaAulaAsignadaAEvento(
                Long.parseLong(eventoId), aula, propagar);

        return toUI(eventos);
    }
}