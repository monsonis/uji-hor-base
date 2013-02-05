package es.uji.apps.hor.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.EventoFueraDeRangoException;
import es.uji.apps.hor.EventoMasDeUnaRepeticionException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.dao.RangoHorarioDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.RangoHorario;
import es.uji.commons.rest.Role;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.sso.exceptions.UnauthorizedUserException;

@Service
public class EventosService
{
    private final EventosDAO eventosDAO;

    private final AulaDAO aulaDAO;

    @Autowired
    private PersonaDAO personaDAO;

    @Autowired
    private RangoHorarioDAO rangoHorarioDAO;

    @Autowired
    private CentroDAO centroDAO;

    @Autowired
    public EventosService(EventosDAO eventosDAO, AulaDAO aulaDAO)
    {
        this.eventosDAO = eventosDAO;
        this.aulaDAO = aulaDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Evento> eventosSemanaGenericaDeUnEstudio(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return eventosDAO.getEventosSemanaGenerica(estudioId, cursoId, semestreId, grupoId,
                calendariosIds);
    }

    @Role({ "ADMIN", "USUARIO" })
    public Evento modificaDiaYHoraEvento(Long eventoId, Date inicio, Date fin, Long connectedUserId)
            throws DuracionEventoIncorrectaException, RegistroNoEncontradoException,
            UnauthorizedUserException, EventoFueraDeRangoException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

        evento.setFechaInicioYFin(inicio, fin);

        List<RangoHorario> rangosHorarios = rangoHorarioDAO.getRangosHorariosDelEvento(evento);
        evento.compruebaDentroDeLosRangosHorarios(rangosHorarios);

        eventosDAO.updateHorasEventoYSusDetalles(evento);
        return evento;
    }

    @Role({ "ADMIN", "USUARIO" })
    public Evento modificaDiaYHoraEventoEnVistaDetalle(Long eventoDetalleId, Date inicio,
            Date fin, Long connectedUserId) throws DuracionEventoIncorrectaException,
            RegistroNoEncontradoException, UnauthorizedUserException, EventoFueraDeRangoException,
            EventoMasDeUnaRepeticionException
    {
        Evento evento = eventosDAO.getEventoByEventoDetalleId(eventoDetalleId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

        if (elEventoTieneMasDeUnaRepeticion(evento))
        {
            throw new EventoMasDeUnaRepeticionException();
        }

        evento.setFechaInicioYFin(inicio, fin);

        List<RangoHorario> rangosHorarios = rangoHorarioDAO.getRangosHorariosDelEvento(evento);
        evento.compruebaDentroDeLosRangosHorarios(rangosHorarios);

        eventosDAO.updateHorasEventoYSusDetalles(evento);
        return evento;
    }

    private boolean elEventoTieneMasDeUnaRepeticion(Evento evento)
    {
        return evento.getEventosDetalle().size() > 1;
    }

    @Role({ "ADMIN", "USUARIO" })
    public void deleteEventoSemanaGenerica(Long eventoId, Long connectedUserId)
            throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

        for (EventoDetalle detalle : evento.getEventosDetalle())
        {
            eventosDAO.deleteEventoDetalle(detalle);
        }

        if (esElultimoEventoAsignadoDelGrupo(evento))
        {
            evento.desplanificar();
            eventosDAO.desplanificaEvento(evento);
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

    @Role({ "ADMIN", "USUARIO" })
    public void divideEventoSemanaGenerica(Long eventoId, Long connectedUserId)
            throws RegistroNoEncontradoException, EventoNoDivisibleException,
            UnauthorizedUserException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

        Evento nuevoEvento = evento.divide();

        eventosDAO.insertEvento(nuevoEvento);
        eventosDAO.updateHorasEventoYSusDetalles(evento);
    }

    @Role({ "ADMIN", "USUARIO" })
    public Evento modificaDetallesGrupoAsignatura(Long eventoId, Date inicio, Date fin,
            Date desdeElDia, Integer numeroIteraciones, Integer repetirCadaSemanas,
            Date hastaElDia, Boolean detalleManual, Long connectedUserId)
            throws DuracionEventoIncorrectaException, RegistroNoEncontradoException,
            UnauthorizedUserException
    {

        Evento evento = eventosDAO.getEventoById(eventoId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

        evento.setFechaInicioYFin(inicio, fin);
        evento.setDesdeElDia(desdeElDia);
        evento.setNumeroIteraciones(numeroIteraciones);
        evento.setRepetirCadaSemanas(repetirCadaSemanas);
        evento.setHastaElDia(hastaElDia);
        evento.setDetalleManual(detalleManual);

        return eventosDAO.modificaDetallesGrupoAsignatura(evento);

    }

    @Role({ "ADMIN", "USUARIO" })
    public List<EventoDocencia> getDiasDocenciaDeUnEventoByEventoId(Long eventoId,
            Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

        return eventosDAO.getDiasDocenciaDeUnEventoByEventoId(eventoId);
    }

    @Role({ "ADMIN", "USUARIO" })
    public Evento updateEventoConDetalleManual(Long eventoId, List<Date> fechas, Date inicio,
            Date fin, Long connectedUserId) throws RegistroNoEncontradoException,
            EventoDetalleSinEventoException, DuracionEventoIncorrectaException,
            UnauthorizedUserException
    {
        Evento evento = eventosDAO.getEventoById(eventoId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

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

    @Role({ "ADMIN", "USUARIO" })
    public List<EventoDetalle> eventosDetalleDeUnEstudio(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds, Date rangoFechaInicio,
            Date rangoFechaFin, Long connectedUserId) throws UnauthorizedUserException,
            RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return eventosDAO.getEventosDetalle(estudioId, cursoId, semestreId, grupoId,
                calendariosIds, rangoFechaInicio, rangoFechaFin);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Evento> actualizaAulaAsignadaAEvento(Long eventoId, Long aulaId, boolean propagar,
            Long connectedUserId) throws RegistroNoEncontradoException,
            AulaNoAsignadaAEstudioDelEventoException, UnauthorizedUserException
    {

        Evento evento = eventosDAO.getEventoById(eventoId);

        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEvento(evento);
        }

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

    @Role({ "ADMIN", "USUARIO" })
    public List<EventoDetalle> getEventosDetallePorAula(Long aulaId, Long semestreId,
            List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin,
            Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            Centro centro = centroDAO.getCentroByAulaId(aulaId);
            persona.compruebaAccesoACentro(centro.getId());
        }

        return eventosDAO.getEventosDetallePorAula(aulaId, semestreId, calendariosIds,
                rangoFechaInicio, rangoFechaFin);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Evento> getEventosSemanaGenericaPorAula(Long aulaId, Long semestreId,
            List<Long> calendariosIds, Long connectedUserId) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            Centro centro = centroDAO.getCentroByAulaId(aulaId);
            persona.compruebaAccesoACentro(centro.getId());
        }

        return eventosDAO.getEventosSemanaGenericaPorAula(aulaId, semestreId, calendariosIds);
    }
}
