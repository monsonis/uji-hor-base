package es.uji.apps.hor.builders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.Semestre;

public class EventoBuilder
{
    private Evento evento;
    private EventosDAO eventoDAO;

    private SimpleDateFormat formatter;

    public EventoBuilder(EventosDAO eventoDAO)
    {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.eventoDAO = eventoDAO;
        evento = new Evento();
    }

    public EventoBuilder()
    {
        this(null);
    }

    public EventoBuilder withTitulo(String titulo)
    {
        evento.setTitulo(titulo);
        return this;
    }

    public EventoBuilder withInicioYFinFechaString(String inicio, String fin)
            throws ParseException, DuracionEventoIncorrectaException
    {
        evento.setFechaInicioYFin(formatter.parse(inicio), formatter.parse(fin));
        return this;
    }

    public EventoBuilder withInicioYFin(Date inicio, Date fin)
            throws DuracionEventoIncorrectaException
    {
        evento.setFechaInicioYFin(inicio, fin);
        return this;
    }

    public EventoBuilder withGrupoId(String grupoId)
    {
        evento.setGrupoId(grupoId);
        return this;
    }

    public EventoBuilder withSubgrupoId(Long subgrupoId)
    {
        evento.setSubgrupoId(subgrupoId);
        return this;
    }

    public EventoBuilder withSemestre(Semestre semestre)
    {
        evento.setSemestre(semestre);
        return this;
    }

    public EventoBuilder withAsignatura(Asignatura asignatura)
    {
        evento.getAsignaturas().add(asignatura);
        return this;
    }

    public EventoBuilder withAsignaturas(List<Asignatura> asignaturas)
    {
        evento.setAsignaturas(asignaturas);
        return this;
    }

    public EventoBuilder withCalendario(Calendario calendario)
    {
        evento.setCalendario(calendario);
        return this;
    }

    public EventoBuilder withDetalleManual(Boolean detalleManual)
    {
        evento.setDetalleManual(detalleManual);
        return this;
    }

    public EventoBuilder withAulaPlanficacion(AulaPlanificacion aulaPlanificacion)
    {
        evento.setAulaPlanificacion(aulaPlanificacion);
        return this;
    }

    public Evento build()
    {
        if (eventoDAO != null)
        {
            evento = eventoDAO.insertEvento(evento);
        }

        return evento;
    }
}