package es.uji.apps.hor.model;


public class Centro
{
    private Long id;
    private String nombre;

    public Centro(Long id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Centro()
    {
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
