package es.uji.apps.hor.model;

public class TipoEstudio
{
    private String id;
    private String nombre;
    private Integer orden = 0;

    public TipoEstudio() {
        
    }
    
    public TipoEstudio(String id, String nombre)
    {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public TipoEstudio()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
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

    public Integer getOrden()
    {
        return orden;
    }

    public void setOrden(Integer orden)
    {
        this.orden = orden;
    }

}
