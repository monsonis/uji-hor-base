package es.uji.apps.hor.model;


public class Estudio
{
    private Long id;
    private String nombre;
    private TipoEstudio tipoEstudio;
    private Centro centro;
    private Boolean oficial;
    private Integer numeroCursos;

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

    public TipoEstudio getTipoEstudio()
    {
        return tipoEstudio;
    }

    public void setTipoEstudio(TipoEstudio tipoEstudio)
    {
        this.tipoEstudio = tipoEstudio;
    }

    public Centro getCentro()
    {
        return centro;
    }

    public void setCentro(Centro centro)
    {
        this.centro = centro;
    }

    public Boolean getOficial()
    {
        return oficial;
    }

    public void setOficial(Boolean oficial)
    {
        this.oficial = oficial;
    }

    public Integer getNumeroCursos()
    {
        return numeroCursos;
    }

    public void setNumeroCursos(Integer numeroCursos)
    {
        this.numeroCursos = numeroCursos;
    }
}
