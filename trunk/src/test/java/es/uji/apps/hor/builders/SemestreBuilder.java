package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.Semestre;

public class SemestreBuilder
{
    private Semestre semestre;

    public SemestreBuilder()
    {
        semestre = new Semestre();
    }

    public SemestreBuilder withSemestre(Long semestre)
    {
        this.semestre.setSemestre(semestre);
        return this;
    }

    public SemestreBuilder withNombre(String nombre)
    {
        semestre.setNombre(nombre);
        return this;
    }

    public Semestre build()
    {
        return semestre;
    }

}
