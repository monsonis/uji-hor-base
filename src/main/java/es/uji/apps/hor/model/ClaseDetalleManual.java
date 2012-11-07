package es.uji.apps.hor.model;

import java.util.Date;

public class ClaseDetalleManual
{
    private Long id;
    private Date fecha;
    private Boolean impartirClase;
    private Boolean periodoLectivo;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Boolean getImpartirClase()
    {
        return impartirClase;
    }

    public void setImpartirClase(Boolean impartirClase)
    {
        this.impartirClase = impartirClase;
    }

    public Boolean getPeriodoLectivo()
    {
        return periodoLectivo;
    }

    public void setPeriodoLectivo(Boolean periodoLectivo)
    {
        this.periodoLectivo = periodoLectivo;
    }
}
