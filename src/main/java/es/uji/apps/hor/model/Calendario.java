package es.uji.apps.hor.model;

public class Calendario
{
    private Long id;
    private String nombre;

    public Calendario()
    {

    }

    public Calendario(Long id)
    {
        this.id = id;
    }

    public Calendario(Long id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
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
}
