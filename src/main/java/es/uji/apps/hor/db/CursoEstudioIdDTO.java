package es.uji.apps.hor.db;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class CursoEstudioIdDTO implements Serializable
{
    private BigDecimal cursoId;
    private BigDecimal estudioId;

    public BigDecimal getCursoId()
    {
        return cursoId;
    }

    public void setCursoId(BigDecimal cursoId)
    {
        this.cursoId = cursoId;
    }

    public BigDecimal getEstudioId()
    {
        return estudioId;
    }

    public void setEstudioId(BigDecimal estudioId)
    {
        this.estudioId = estudioId;
    }
}
