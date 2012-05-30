package es.uji.apps.hor.model;

import java.util.Date;

public class Evento
{
    private Long id;
    private Calendario calendario;
    private String titulo;
    private String observaciones;
    private Date inicio;
    private Date fin;

    public Evento(Long id, Calendario calendario, String titulo, Date inicio, Date fin)
    {
        this.id = id;
        this.calendario = calendario;
        this.titulo = titulo;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Calendario getCalendario()
    {
        return calendario;
    }

    public void setCalendario(Calendario calendario)
    {
        this.calendario = calendario;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getObservaciones()
    {
        return observaciones;
    }

    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
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
}
