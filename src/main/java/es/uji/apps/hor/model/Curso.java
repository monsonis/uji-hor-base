package es.uji.apps.hor.model;

public class Curso
{
    private Long curso;

    public Curso(Long curso)
    {
        this.setCurso(curso);
    }

    public Long getCurso()
    {
        return curso;
    }

    public void setCurso(Long curso)
    {
        this.curso = curso;
    }

}
