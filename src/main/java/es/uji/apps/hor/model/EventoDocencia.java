package es.uji.apps.hor.model;

import java.util.Date;

public class EventoDocencia
{
    private Long eventoId;
    private Date fecha;
    private String docencia;
    private String tipoDia;

    public Long getEventoId()
    {
        return eventoId;
    }

    public void setEventoId(Long eventoId)
    {
        this.eventoId = eventoId;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public String getDocencia()
    {
        return docencia;
    }

    public void setDocencia(String docencia)
    {
        this.docencia = docencia;
    }

    public String getTipoDia()
    {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia)
    {
        this.tipoDia = tipoDia;
    }
}
