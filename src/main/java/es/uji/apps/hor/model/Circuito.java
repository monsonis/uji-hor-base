package es.uji.apps.hor.model;




public class Circuito
{
    private Long id;
    private String nombre;
    private String grupo;
    private Semestre semestre;
    private Estudio estudio;
    private Long plazas;
    

    public Circuito() {
        
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getPlazas()
    {
        return plazas;
    }

    public void setPlazas(Long plazas)
    {
        this.plazas = plazas;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getGrupo()
    {
        return grupo;
    }

    public void setGrupo(String grupo)
    {
        this.grupo = grupo;
    }

    public Semestre getSemestre()
    {
        return semestre;
    }

    public void setSemestre(Semestre semestre)
    {
        this.semestre = semestre;
    }

    public Estudio getEstudio()
    {
        return estudio;
    }

    public void setEstudio(Estudio estudio)
    {
        this.estudio = estudio;
    }
    
}
