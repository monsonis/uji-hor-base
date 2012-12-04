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
    private Boolean detalleManual;
    private Integer numeroIteraciones;
    private Integer repetirCadaSemanas;
    private Date desdeElDia;
    private Date hastaElDia;
    private String comunes;

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

    public Boolean getDetalleManual()
    {
        return detalleManual;
    }

    public void setDetalleManual(Boolean detalleManual)
    {
        this.detalleManual = detalleManual;
    }

    public Integer getNumeroIteraciones()
    {
        return numeroIteraciones;
    }

    public void setNumeroIteraciones(Integer numeroIteraciones)
    {
        this.numeroIteraciones = numeroIteraciones;
    }

    public Integer getRepetirCadaSemanas()
    {
        return repetirCadaSemanas;
    }

    public void setRepetirCadaSemanas(Integer repetirCadaSemanas)
    {
        this.repetirCadaSemanas = repetirCadaSemanas;
    }

    public Date getDesdeElDia()
    {
        return desdeElDia;
    }

    public void setDesdeElDia(Date desdeElDia)
    {
        this.desdeElDia = desdeElDia;
    }

    public Date getHastaElDia()
    {
        return hastaElDia;
    }

    public void setHastaElDia(Date hastaElDia)
    {
        this.hastaElDia = hastaElDia;
    }

    public String getComunes()
    {
        return comunes;
    }

    public void setComunes(String comunes)
    {
        this.comunes = comunes;
    }
}
