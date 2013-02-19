package es.uji.apps.hor.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.EventoFueraDeFechasSemestreException;
import es.uji.apps.hor.EventoFueraDeRangoException;
import es.uji.apps.hor.EventoMasDeUnaRepeticionException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.dao.PersonaDAO;
import es.uji.apps.hor.dao.RangoHorarioDAO;
import es.uji.apps.hor.dao.SemestresDetalleDAO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;
import es.uji.apps.hor.model.EventoDocencia;
import es.uji.apps.hor.model.Persona;
import es.uji.apps.hor.model.RangoHorario;
import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.commons.rest.auth.Role;
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
    private SemestresDetalleDAO semestresDetalleDAO;

    @Autowired
    public EventosService(EventosDAO eventosDAO, AulaDAO aulaDAO)
    {
        this.eventosDAO = eventosDAO;
        this.aulaDAO = aulaDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Evento> eventosSemanaGenericaDeUnEstudio(Long estudioId, Long cursoId,
            Long semestreId, List<String> gruposIds, List<Long> calendariosIds, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        return eventosDAO.getEventosSemanaGenerica(estudioId, cursoId, semestreId, gruposIds,
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
    public Evento modificaDiaYHoraEventoEnVistaDetalle(Long eventoId, Date inicio, Date fin,
            Long connectedUserId) throws DuracionEventoIncorrectaException,
            RegistroNoEncontradoException, UnauthorizedUserException, EventoFueraDeRangoException,
            EventoMasDeUnaRepeticionException, EventoFueraDeFechasSemestreException
    {
        Evento evento = eventosDAO.getEventoByIdConDetalles(eventoId);

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

        SemestreDetalle semestre = getSemestreDelEvento(evento);
        evento.compruebaDentroFechasSemestre(semestre.getFechaInicio(), semestre.getFechaFin());

        List<RangoHorario> rangosHorarios = rangoHorarioDAO.getRangosHorariosDelEvento(evento);
        evento.compruebaDentroDeLosRangosHorarios(rangosHorarios);

        eventosDAO.updateHorasEventoYSusDetalles(evento);
        return evento;
    }

    private SemestreDetalle getSemestreDelEvento(Evento evento)
    {
        Estudio unEstudio = evento.getAsignaturas().get(0).getEstudio();
        return semestresDetalleDAO.getSemestresDetallesPorEstudioIdYSemestreId(unEstudio.getId(),
                evento.getSemestre().getSemestre()).get(0);
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
        return eventosDAO.getEventosDelMismoGrupo(eventoReferencia);
    }

    private boolean esElultimoEventoAsignadoDelGrupo(Evento evento)
    {
        long cantidadEventosDelMismoGrupo = cantidadEventosDelMismoGrupo(evento);
        return cantidadEventosDelMismoGrupo == 0;
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
            Long semestreId, List<String> gruposIds, List<Long> calendariosIds,
            Date rangoFechaInicio, Date rangoFechaFin, Long connectedUserId)
            throws UnauthorizedUserException, RegistroNoEncontradoException
    {
        if (!personaDAO.esAdmin(connectedUserId))
        {
            Persona persona = personaDAO.getPersonaConTitulacionesYCentrosById(connectedUserId);
            persona.compruebaAccesoAEstudio(estudioId);
        }

        List<EventoDetalle> listaEventos = eventosDAO.getEventosDetalle(estudioId, cursoId,
                semestreId, gruposIds, calendariosIds, rangoFechaInicio, rangoFechaFin);

        for (EventoDetalle eventoDetalle : listaEventos)
        {
            Evento evento = eventosDAO.getEventoByEventoDetalleId(eventoDetalle.getId());
            eventoDetalle.setComunes(evento.getEventosDetalle().size() - 1);
        }

        return listaEventos;
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

        Aula aula = null;

        if (aulaId != null)
        {
            aula = aulaDAO.getAulaYPlanificacionesByAulaId(aulaId);
        }
        actualizaAula(evento, aula);

        List<Evento> eventos = new ArrayList<Evento>();

        if (propagar)
        {
            eventos = getEventosDelMismoGrupo(evento);
            for (Evento grupoComun : eventos)
            {
                actualizaAula(grupoComun, aula);
            }
        }
        eventos.add(evento);

        return eventos;
    }

    private void actualizaAula(Evento evento, Aula aula)
            throws AulaNoAsignadaAEstudioDelEventoException, RegistroNoEncontradoException
    {
        if (aula != null)
        {
            evento.actualizaAula(aula);
            eventosDAO.actualizaAulaAsignadaAEvento(evento.getId(), aula.getId());
        }
        else
        {
            evento.desasignaAula();
            eventosDAO.desasignaAula(evento.getId());
        }
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<EventoDetalle> getEventosDetallePorAula(Long aulaId, Long semestreId,
            List<Long> calendariosIds, Date rangoFechaInicio, Date rangoFechaFin,
            Long connectedUserId) throws RegistroNoEncontradoException, UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId)
                && !personaDAO.isAulaAutorizada(aulaId, connectedUserId))
        {
            throw new UnauthorizedUserException();
        }

        return eventosDAO.getEventosDetallePorAula(aulaId, semestreId, calendariosIds,
                rangoFechaInicio, rangoFechaFin);
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Evento> getEventosSemanaGenericaPorAula(Long aulaId, Long semestreId,
            List<Long> calendariosIds, Long connectedUserId) throws RegistroNoEncontradoException,
            UnauthorizedUserException
    {
        if (!personaDAO.esAdmin(connectedUserId)
                && !personaDAO.isAulaAutorizada(aulaId, connectedUserId))
        {
            throw new UnauthorizedUserException();
        }

        return eventosDAO.getEventosSemanaGenericaPorAula(aulaId, semestreId, calendariosIds);
    }
}
