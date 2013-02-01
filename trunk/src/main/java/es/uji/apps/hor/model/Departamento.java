package es.uji.apps.hor.model;



public class Departamento
{
    private Long id;
    private String nombre;
    private Centro centro;
    private Long activo;

    public Departamento() {
        
    }
    
    public Departamento(Long id, String nombre)
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

    public Centro getCentro()
    {
        return centro;
    }

    public void setCentro(Centro centro)
    {
        this.centro = centro;
    }

    public Long getActivo()
    {
        return activo;
    }

    public void setActivo(Long activo)
    {
        this.activo = activo;
    }
}
