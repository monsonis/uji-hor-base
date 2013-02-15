package es.uji.apps.hor.model;

import java.util.ArrayList;
import java.util.List;

public class Semestre
{
    private Long semestre;
    private String nombre;

    public Semestre()
    {
    }

    public Semestre(Long semestre)
    {
        this.semestre = semestre;
    }

    public Semestre(Long semestre, String nombre)
    {
        this.semestre = semestre;
        this.nombre = nombre;
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

    public static List<Semestre> getTodosLosSemestres()
    {
        Semestre semestre1 = new Semestre(new Long(1), String.valueOf(new Long(1)));
        Semestre semestre2 = new Semestre(new Long(2), String.valueOf(new Long(2)));

        List<Semestre> semestres = new ArrayList<Semestre>(2);
        semestres.add(semestre1);
        semestres.add(semestre2);

        return semestres;
    }
}
