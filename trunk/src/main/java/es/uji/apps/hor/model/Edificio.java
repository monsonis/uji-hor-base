package es.uji.apps.hor.model;

import java.util.List;

public class Edificio
{
    private Long id;
    private String nombre;
    private List<PlantaEdificio> plantas;
    private List<AreaEdificio> areas;
    private List<TipoAula> tiposAulas;
    private Centro centro;
    
    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(String nombre)
    {
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
    public List<PlantaEdificio> getPlantas()
    {
        return plantas;
    }
    public void setPlantas(List<PlantaEdificio> plantas)
    {
        this.plantas = plantas;
    }
    public List<AreaEdificio> getAreas()
    {
        return areas;
    }
    public void setAreas(List<AreaEdificio> areas)
    {
        this.areas = areas;
    }
    public List<TipoAula> getTiposAulas()
    {
        return tiposAulas;
    }
    public void setTiposAulas(List<TipoAula> tiposAulas)
    {
        this.tiposAulas = tiposAulas;
    }
    public Centro getCentro()
    {
        return centro;
    }
    public void setCentro(Centro centro)
    {
        this.centro = centro;
    }
}
