package es.uji.apps.hor.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Service
public class EventosService
{
    private final EventosDAO eventosDAO;

    private final AulaDAO aulaDAO;

    @Autowired
    public EventosService(EventosDAO eventosDAO, AulaDAO aulaDAO)
    {
        this.eventosDAO = eventosDAO;
        this.aulaDAO = aulaDAO;
    }

    public List<Evento> eventosSemanaGenericaDeUnEstudio(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds)
    {
        return eventosDAO.getEventosSemanaGenerica(estudioId, cursoId, semestreId, grupoId,
                calendariosIds);
    }

    public Evento modificaDiaYHoraEvento(Long eventoId, Date inicio, Date fin)
            throws DuracionEventoIncorrectaException, RegistroNoEncontradoException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);
        evento.setFechaInicioYFin(inicio, fin);
        eventosDAO.updateHorasEventoYSusDetalles(evento);
        return evento;
    }

    public void deleteEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);

        for (EventoDetalle detalle : evento.getEventosDetalle())
        {
            eventosDAO.deleteEventoDetalle(detalle);
        }

        if (esElultimoEventoAsignadoDelGrupo(evento))
        {
            evento.desplanificar();
            eventosDAO.updateHorasEventoYSusDetalles(evento);
            eventosDAO.desasignaAulaPlanificacion(eventoId);
        }
        else
        {
            eventosDAO.deleteEventoSemanaGenerica(eventoId);
        }
    }

    private long cantidadEventosDelMismoGrupo(Evento evento)
    {

        return getEventosDelMismoGrupo(evento).size();
    }

    private List<Evento> getEventosDelMismoGrupo(Evento eventoReferencia)
    {
        List<Evento> eventosDeLaMismaSemana = getEventosDeLaMismaSemanaGenericaQueElEvento(eventoReferencia);
        return getEventosDelMismoGrupoEnLaLista(eventoReferencia, eventosDeLaMismaSemana);

    }

    private List<Evento> getEventosDeLaMismaSemanaGenericaQueElEvento(Evento eventoReferencia)
    {
        if (eventoReferencia.getAsignaturas().isEmpty())
        {
            return new ArrayList<Evento>();
        }

        Asignatura unaAsignatura = eventoReferencia.getAsignaturas().get(0);
        Long estudioId = unaAsignatura.getEstudio().getId();
        Long cursoId = unaAsignatura.getCursoId();
        Long semestreId = eventoReferencia.getSemestre().getSemestre();
        String grupoId = eventoReferencia.getGrupoId();
        List<Long> calendariosIds = new ArrayList<Long>();
        calendariosIds.add(eventoReferencia.getCalendario().getId());

        return eventosDAO.getEventosSemanaGenerica(estudioId, cursoId, semestreId, grupoId,
                calendariosIds);
    }

    private List<Evento> getEventosDelMismoGrupoEnLaLista(Evento eventoReferencia,
            List<Evento> eventos)
    {
        List<Evento> eventosDelMismoGrupo = new ArrayList<Evento>();
        for (Evento evento : eventos)
        {
            if (elEventoEsDelMismoGrupo(eventoReferencia, evento))
            {
                eventosDelMismoGrupo.add(evento);
            }
        }
        return eventosDelMismoGrupo;
    }

    private boolean elEventoEsDelMismoGrupo(Evento eventoReferencia, Evento evento)
    {
        for (Asignatura asignatura : evento.getAsignaturas())
        {
            if (!eventoReferencia.getAsignaturas().contains(asignatura))
            {
                return false;
            }
        }

        return true;
    }

    private boolean esElultimoEventoAsignadoDelGrupo(Evento evento)
    {
        long cantidadEventosDelMismoGrupo = cantidadEventosDelMismoGrupo(evento);
        return cantidadEventosDelMismoGrupo == 1;
    }

    public void divideEventoSemanaGenerica(Long eventoId) throws RegistroNoEncontradoException,
            EventoNoDivisibleException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);
        Evento nuevoEvento = evento.divide();

        eventosDAO.insertEvento(nuevoEvento);
        eventosDAO.updateHorasEventoYSusDetalles(evento);
    }

    public Evento modificaDetallesGrupoAsignatura(Long eventoId, Date inicio, Date fin,
            Date desdeElDia, Integer numeroIteraciones, Integer repetirCadaSemanas,
            Date hastaElDia, Boolean detalleManual) throws DuracionEventoIncorrectaException,
            RegistroNoEncontradoException
    {

        Evento evento = eventosDAO.getEventoById(eventoId);
        evento.setFechaInicioYFin(inicio, fin);
        evento.setDesdeElDia(desdeElDia);
        evento.setNumeroIteraciones(numeroIteraciones);
        evento.setRepetirCadaSemanas(repetirCadaSemanas);
        evento.setHastaElDia(hastaElDia);
        evento.setDetalleManual(detalleManual);

        return eventosDAO.modificaDetallesGrupoAsignatura(evento);

    }

    public List<EventoDocencia> getDiasDocenciaDeUnEventoByEventoId(Long eventoId)
    {
        return eventosDAO.getDiasDocenciaDeUnEventoByEventoId(eventoId);
    }

    public Evento updateEventoConDetalleManual(Long eventoId, List<Date> fechas, Date inicio,
            Date fin) throws RegistroNoEncontradoException, EventoDetalleSinEventoException,
            DuracionEventoIncorrectaException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);
        evento.setDetalleManual(true);
        evento.setFechaInicioYFin(inicio, fin);
        eventosDAO.updateEvento(evento);

        eventosDAO.deleteDetallesDeEvento(evento);
        evento.vaciaEventosDetalle();

        for (Date fecha : fechas)
        {
            EventoDetalle detalle = evento.creaDetalleEnFecha(fecha);
            eventosDAO.insertEventoDetalle(detalle);
        }

        return evento;
    }

    public List<EventoDetalle> eventosDetalleDeUnEstudio(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds, Date rangoFechaInicio,
            Date rangoFechaFin)
    {
        return eventosDAO.getEventosDetalle(estudioId, cursoId, semestreId, grupoId,
                calendariosIds, rangoFechaInicio, rangoFechaFin);
    }

    public List<Evento> actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId, boolean propagar)
            throws RegistroNoEncontradoException, AulaNoAsignadaAEstudioDelEventoException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);
        AulaPlanificacion aula = null;

        if (aulaId != null)
        {
            aula = aulaDAO.getAulaById(aulaId);
        }
        actualizaAulaPlanificacion(evento, aula);

        List<Evento> eventos = new ArrayList<Evento>();

        if (propagar)
        {
            eventos = getEventosDelMismoGrupo(evento);
            for (Evento grupoComun : eventos)
            {
                actualizaAulaPlanificacion(grupoComun, aula);
            }
        }
        eventos.add(evento);

        return eventos;
    }

    private void actualizaAulaPlanificacion(Evento evento, AulaPlanificacion aula)
            throws AulaNoAsignadaAEstudioDelEventoException, RegistroNoEncontradoException
    {
        if (aula != null)
        {
            evento.actualizaAulaPlanificacion(aula);
            eventosDAO.actualizaAulaAsignadaAEvento(evento.getId(), aula.getId());
        }
        else
        {
            evento.desasignaAulaPlanificacion();
            eventosDAO.desasignaAulaPlanificacion(evento.getId());
        }
    }
}
