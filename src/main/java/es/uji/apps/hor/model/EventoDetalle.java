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
    private int comunes = 0;

    public EventoDetalle()
    {
    }

    public EventoDetalle(Evento evento, Date fecha)
    {
        this.evento = evento;
        evento.addEventoDetalle(this);
        this.descripcion = evento.getTitulo();

        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);
        int year = calFecha.get(Calendar.YEAR);
        int month = calFecha.get(Calendar.MONTH);
        int day = calFecha.get(Calendar.DAY_OF_MONTH);

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(evento.getInicio());
        calInicio.set(year, month, day);
        this.inicio = calInicio.getTime();

        Calendar calFin = Calendar.getInstance();
        calFin.setTime(evento.getFin());
        calFin.set(year, month, day);
        this.fin = calFin.getTime();
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
        evento.addEventoDetalle(this);
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
        if (descripcion != null && !descripcion.isEmpty())
        {
            return descripcion;
        }
        else
        {
            return this.toString();
        }
    }

    public String getDescripcionConGrupoYComunes()
    {
        Evento evento = getEvento();
        String tituloEvento = "";
        for (Asignatura asignatura : evento.getAsignaturas())
        {
            tituloEvento = tituloEvento + " " + asignatura.getId();
        }
        tituloEvento += " " + evento.getGrupoId() + " " + evento.getCalendario().getLetraId()
                + evento.getSubgrupoId();
        return tituloEvento;
    }

    public String getDescripcionParaUnEstudio(Long estudioId)
    {
        String titulo = evento.getDescripcionParaUnEstudio(estudioId);
        if (isEditable())
        {
            titulo += " *";
        }

        return titulo;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    @Override
    public String toString()
    {
        return evento.getTitulo();
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

    public void estableceHoraYMinutosInicio(Date horas)
    {
        this.setInicio(actualizaHoraYMinutosFecha(this.getInicio(), horas));
    }

    public void estableceHoraYMinutosFin(Date horas)
    {
        this.setFin(actualizaHoraYMinutosFecha(this.getFin(), horas));
    }

    private Date actualizaHoraYMinutosFecha(Date original, Date horas)
    {
        Calendar calendario = Calendar.getInstance();
        Calendar calendarioHorasNuevas = Calendar.getInstance();
        calendario.setTime(original);
        calendarioHorasNuevas.setTime(horas);

        calendario.set(Calendar.HOUR_OF_DAY, calendarioHorasNuevas.get(Calendar.HOUR_OF_DAY));
        calendario.set(Calendar.MINUTE, calendarioHorasNuevas.get(Calendar.MINUTE));

        return calendario.getTime();

    }

    public void setComunes(int comunes)
    {
        this.comunes = comunes;
    }

    private boolean isEditable()
    {
        return comunes == 0;
    }

}
