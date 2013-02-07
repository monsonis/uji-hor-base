package es.uji.apps.hor.model;

public class AulaPlanificacion
{
    private Long id;
    private Aula aula;
    private Estudio estudio;
    private Semestre semestre;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Aula getAula()
    {
        return aula;
    }

    public void setAula(Aula aula)
    {
        this.aula = aula;
    }

    public Estudio getEstudio()
    {
        return estudio;
    }

    public void setEstudio(Estudio estudio)
    {
        this.estudio = estudio;
    }

    public Semestre getSemestre()
    {
        return semestre;
    }

    public void setSemestre(Semestre semestre)
    {
        this.semestre = semestre;
    }

}
