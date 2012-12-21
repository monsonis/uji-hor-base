package es.uji.apps.hor.model;

import java.util.Calendar;
import java.util.Date;

public class GrupoAsignatura
{
    private Long id;

    private String titulo;

    private Date inicio;

    private Date fin;

    private String diaSemana;

    public GrupoAsignatura(Long id, String titulo)
    {
        this.id = id;
        this.titulo = titulo;
    }

    public GrupoAsignatura()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public Date getInicio()
    {
        return inicio;
    }

    public void setInicio(Date inicio)
    {
        this.inicio = inicio;
    }

    public Date getFin()
    {
        return fin;
    }

    public void setFin(Date fin)
    {
        this.fin = fin;
    }

    public String getDiaSemana()
    {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana)
    {
        this.diaSemana = diaSemana;
    }

    public void planificaGrupoAsignaturaSinAsignar()
    {
        Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();
        calInicio.set(Calendar.HOUR_OF_DAY, 8);
        calInicio.set(Calendar.MINUTE, 0);
        calInicio.set(Calendar.SECOND, 0);
        calFin.set(Calendar.HOUR_OF_DAY, 10);
        calFin.set(Calendar.MINUTE, 0);
        calFin.set(Calendar.SECOND, 0);

        inicio = calInicio.getTime();
        fin = calFin.getTime();
        setDiaSemana("Dilluns");
    }
}
