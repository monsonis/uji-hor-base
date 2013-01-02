package es.uji.apps.hor.builders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Asignatura;
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

    public EventoBuilder withInicioFechaString(String inicio) throws ParseException
    {
        evento.setInicio(formatter.parse(inicio));
        return this;
    }

    public EventoBuilder withFinFechaString(String fin) throws ParseException
    {
        evento.setFin(formatter.parse(fin));
        return this;
    }

    public EventoBuilder withInicio(Date inicio)
    {
        evento.setInicio(inicio);
        return this;
    }

    public EventoBuilder withFin(Date fin)
    {
        evento.setFin(fin);
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
        evento.setAsignatura(asignatura);
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

    public Evento build()
    {
        if (eventoDAO != null)
        {
            evento = eventoDAO.insertEvento(evento);
        }

        return evento;
    }
}