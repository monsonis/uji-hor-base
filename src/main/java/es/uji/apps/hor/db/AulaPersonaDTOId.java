package es.uji.apps.hor.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AulaPersonaDTOId implements Serializable
{
    private Long aulaId;

    private Long personaId;

    public Long getAulaId()
    {
        return aulaId;
    }

    public void setAulaId(Long aulaId)
    {
        this.aulaId = aulaId;
    }

    public Long getPersonaId()
    {
        return personaId;
    }

    public void setPersonaId(Long personaId)
    {
        this.personaId = personaId;
    }

    public int hashCode()
    {
        return (int) (aulaId + personaId);
    }

    public boolean equals(Object o)
    {
        return ((o instanceof AulaPersonaDTOId) && aulaId == ((AulaPersonaDTOId) o).getAulaId() && personaId == ((AulaPersonaDTOId) o)
                .getPersonaId());
    }

}
