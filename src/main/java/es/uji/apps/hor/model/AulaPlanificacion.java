package es.uji.apps.hor.model;

public class AulaPlanificacion
{
    private Long id;

    private String nombre;

    private Long aulaId;

    private Long estudioId;

    private Long cursoId;

    private Long semestreId;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Long getAulaId()
    {
        return aulaId;
    }

    public void setAulaId(Long aulaId)
    {
        this.aulaId = aulaId;
    }

    public Long getEstudioId()
    {
        return estudioId;
    }

    public void setEstudioId(Long estudioId)
    {
        this.estudioId = estudioId;
    }

    public Long getCursoId()
    {
        return cursoId;
    }

    public void setCursoId(Long cursoId)
    {
        this.cursoId = cursoId;
    }

    public Long getSemestreId()
    {
        return semestreId;
    }

    public void setSemestreId(Long semestreId)
    {
        this.semestreId = semestreId;
    }

}