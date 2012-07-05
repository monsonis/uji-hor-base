package es.uji.apps.hor.model;

public class Semestre
{
    private Long semestre;

    public Semestre(Long semestre)
    {
        this.setSemestre(semestre);
    }

    public Long getSemestre()
    {
        return semestre;
    }

    public void setSemestre(Long semestre)
    {
        this.semestre = semestre;
    }

}
