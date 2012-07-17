package es.uji.apps.hor.model;

import java.util.Date;

public class GrupoHorario
{
    private String id;
    private Date horaInicio;
    private Date horaFin;

    public GrupoHorario(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getHoraInicio()
    {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio)
    {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin()
    {
        return horaFin;
    }

    public void setHoraFin(Date horaFin)
    {
        this.horaFin = horaFin;
    }

}
