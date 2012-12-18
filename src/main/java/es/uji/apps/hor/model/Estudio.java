package es.uji.apps.hor.model;


public class Estudio
{
    private Long id;
    private String nombre;
    private String tipoEstudioId;
    private String tipoEstudio;

    public Estudio(Long id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Estudio() {
    }

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

    public String getTipoEstudioId()
    {
        return tipoEstudioId;
    }

    public void setTipoEstudioId(String tipoEstudioId)
    {
        this.tipoEstudioId = tipoEstudioId;
    }

    public String getTipoEstudio()
    {
        return tipoEstudio;
    }

    public void setTipoEstudio(String tipoEstudio)
    {
        this.tipoEstudio = tipoEstudio;
    }
}
