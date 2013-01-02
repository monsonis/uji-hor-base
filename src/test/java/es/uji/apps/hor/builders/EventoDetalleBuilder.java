package es.uji.apps.hor.builders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.uji.apps.hor.EventoDetalleSinEventoException;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.EventoDetalle;

public class EventoDetalleBuilder
{
    private EventoDetalle eventoDetalle;
    private EventosDAO eventoDAO;

    private SimpleDateFormat formatter;

    public EventoDetalleBuilder(EventosDAO eventoDAO)
    {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.eventoDAO = eventoDAO;
        eventoDetalle = new EventoDetalle();
        eventoDetalle.setDescripcion("");
    }

    public EventoDetalleBuilder withInicioFechaString(String inicio) throws ParseException
    {
        eventoDetalle.setInicio(formatter.parse(inicio));
        return this;
    }

    public EventoDetalleBuilder withFinFechaString(String fin) throws ParseException
    {
        eventoDetalle.setFin(formatter.parse(fin));
        return this;
    }

    public EventoDetalleBuilder withInicio(Date inicio)
    {
        eventoDetalle.setInicio(inicio);
        return this;
    }

    public EventoDetalleBuilder withFin(Date fin)
    {
        eventoDetalle.setFin(fin);
        return this;
    }

    public EventoDetalleBuilder withDescripcion(String descripcion)
    {
        eventoDetalle.setDescripcion(descripcion);
        return this;
    }

    public EventoDetalleBuilder withId(Long id)
    {
        eventoDetalle.setId(id);
        return this;
    }

    public EventoDetalleBuilder withEvento(Evento evento)
    {
        eventoDetalle.setEvento(evento);
        return this;
    }

    public EventoDetalle build() throws EventoDetalleSinEventoException
    {
        if (eventoDAO != null)
        {
            eventoDetalle = eventoDAO.insertEventoDetalle(eventoDetalle);
        }

        return eventoDetalle;
    }
}
