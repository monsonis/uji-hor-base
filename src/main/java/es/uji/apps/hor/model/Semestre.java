package es.uji.apps.hor.model;

public class Semestre
{
    private Long semestre;
    private String nombre;

    public Semestre() {
    }
    
    public Semestre(Long semestre)
    {
        this.setSemestre(semestre);
    }

    public Semestre(Long semestre, String nombre)
    {
        this.setSemestre(semestre);
        this.setNombre(nombre);
    }

    public Long getSemestre()
    {
        return semestre;
    }

    public void setSemestre(Long semestre)
    {
        this.semestre = semestre;
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
