package es.uji.apps.hor.model;



public class Aula
{
    private Long id;
    private String nombre;
    private Centro centro;
    private String tipo;
    private Long plazas;
    private String codigo;
    private String area;
    private String edificio;
    private String planta;

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

    public Centro getCentro()
    {
        return centro;
    }

    public void setCentro(Centro centro)
    {
        this.centro = centro;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
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

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public String getEdificio()
    {
        return edificio;
    }

    public void setEdificio(String edificio)
    {
        this.edificio = edificio;
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
