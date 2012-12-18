package es.uji.apps.hor.model;

import java.util.Calendar;
import java.util.Date;


public class EventoDetalle
{
    private Long id;
    private Evento evento;
    private Date inicio;
    private Date fin;
    private String descripcion;
    
    public EventoDetalle() {
    }
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Evento getEvento()
    {
        return evento;
    }
    public void setEvento(Evento evento)
    {
        this.evento = evento;
    }
    public Date getInicio()
    {
        return inicio;
    }
    public void setInicio(Date inicio)
    {
        this.inicio = inicio;
    }
    public Date getFin()
    {
        return fin;
    }
    public void setFin(Date fin)
    {
        this.fin = fin;
    }
    public String getDescripcion()
    {
        return descripcion;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public EventoDetalle clonar()
    {
        EventoDetalle nuevoEventoDetalle = new EventoDetalle();
        nuevoEventoDetalle.setDescripcion(this.getDescripcion());
        nuevoEventoDetalle.setEvento(this.getEvento());
        nuevoEventoDetalle.setFin(this.getFin());
        nuevoEventoDetalle.setInicio(this.getInicio());
        
        return nuevoEventoDetalle;
    }

    public void estableceHoraYMinutosInicio(Date horas) {
        this.setInicio(actualizaHoraYMinutosFecha(this.getInicio(), horas));
    }

    public void estableceHoraYMinutosFin(Date horas) {
        this.setFin(actualizaHoraYMinutosFecha(this.getFin(), horas));
    }

    private Date actualizaHoraYMinutosFecha(Date original, Date horas)
    {
        Calendar calendario = Calendar.getInstance();
        Calendar calendarioHorasNuevas = Calendar.getInstance();
        calendario.setTime(original);
        calendarioHorasNuevas.setTime(horas);
        
        calendario.set(Calendar.HOUR, calendarioHorasNuevas.get(Calendar.HOUR));
        calendario.set(Calendar.MINUTE, calendarioHorasNuevas.get(Calendar.MINUTE));
        
        return calendario.getTime();
        
    }

}