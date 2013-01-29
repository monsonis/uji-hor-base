package es.uji.apps.hor.model;

import java.util.List;


public class PermisoExtra
{
    private Long id;
    private String nombre;
    private List<Edificio> edificios;

    public PermisoExtra() {
        
    }
    
    public PermisoExtra(Long id, String nombre)
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

    public List<Edificio> getEdificios()
    {
        return edificios;
    }

    public void setEdificios(List<Edificio> edificios)
    {
        this.edificios = edificios;
    }
}
