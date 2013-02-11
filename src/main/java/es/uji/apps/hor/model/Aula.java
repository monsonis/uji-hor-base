package es.uji.apps.hor.model;

import java.util.List;

public class Aula
{
    private Long id;
    private String nombre;
    private Long plazas;
    private String codigo;
    private AreaEdificio area;
    private PlantaEdificio planta;
    private TipoAula tipo;
    private Edificio edificio;
    private Centro centro;
    private List<AulaPlanificacion> planificacion;
    private List<Evento> eventos;
    
    public Aula() {
        
    }
    
    public Aula(Long id)
    {
        this.id = id;
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

    public Long getPlazas()
    {
        return plazas;
    }

    public void setPlazas(Long plazas)
    {
        this.plazas = plazas;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public AreaEdificio getArea()
    {
        return area;
    }

    public void setArea(AreaEdificio area)
    {
        this.area = area;
    }

    public PlantaEdificio getPlanta()
    {
        return planta;
    }

    public void setPlanta(PlantaEdificio planta)
    {
        this.planta = planta;
    }

    public TipoAula getTipo()
    {
        return tipo;
    }

    public void setTipo(TipoAula tipo)
    {
        this.tipo = tipo;
    }

    public Edificio getEdificio()
    {
        return edificio;
    }

    public void setEdificio(Edificio edificio)
    {
        this.edificio = edificio;
    }

    public Centro getCentro()
    {
        return centro;
    }

    public void setCentro(Centro centro)
    {
        this.centro = centro;
    }

    public List<AulaPlanificacion> getPlanificacion()
    {
        return planificacion;
    }

    public void setPlanificacion(List<AulaPlanificacion> planificacion)
    {
        this.planificacion = planificacion;
    }

    public boolean sePuedeDesplanificar()
    {
        return this.eventos == null;
    }

    public List<Evento> getEventos()
    {
        return eventos;
    }

    public void setEventos(List<Evento> eventos)
    {
        this.eventos = eventos;
    }


}
