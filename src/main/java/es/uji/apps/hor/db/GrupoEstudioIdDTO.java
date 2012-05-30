package es.uji.apps.hor.db;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class GrupoEstudioIdDTO implements Serializable
{
    private BigDecimal estudioId;
    private String grupoId;

    public BigDecimal getEstudioId()
    {
        return estudioId;
    }

    public void setEstudioId(BigDecimal estudioId)
    {
        this.estudioId = estudioId;
    }

    public String getGrupoId()
    {
        return grupoId;
    }

    public void setGrupoId(String grupoId)
    {
        this.grupoId = grupoId;
    }
}
