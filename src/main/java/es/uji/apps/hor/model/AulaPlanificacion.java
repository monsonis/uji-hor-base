package es.uji.apps.hor.model;

public class AulaPlanificacion
{
    private Long id;

    private String nombre;

    private Long aulaId;

    private Long estudioId;

    private Long semestreId;

    private String edificio;

    private String tipo;

    private String planta;

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
        return nombre == null ? "" : nombre;
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

    public Long getSemestreId()
    {
        return semestreId;
    }

    public void setSemestreId(Long semestreId)
    {
        this.semestreId = semestreId;
    }

    public String getEdificio()
    {
        return edificio;
    }

    public void setEdificio(String edificio)
    {
        this.edificio = edificio;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getPlanta()
    {
        return planta;
    }

    public void setPlanta(String planta)
    {
        this.planta = planta;
    }

}
