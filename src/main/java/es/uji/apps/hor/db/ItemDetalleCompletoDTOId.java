package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ItemDetalleCompletoDTOId implements Serializable
{
    private Long id;

    private Date fecha;

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

    public int hashCode()
    {
        return (int) (id + fecha.getTime());
    }

    public boolean equals(Object o)
    {
        return ((o instanceof ItemDetalleCompletoDTOId)
                && id.equals(((ItemDetalleCompletoDTOId) o).getId()) && fecha
                    .equals(((ItemDetalleCompletoDTOId) o).getFecha()));
    }

}
