package es.uji.apps.hor.model;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

public class GrupoAsignatura
{
    private Long id;

    private String titulo;

    private Date inicio;

    private Date fin;

    private String diaSemana;

    private Calendario calendario;

    private Asignatura asignatura;

    private Long subgrupoId;

    public GrupoAsignatura(Long id)
    {
        this.id = id;
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
        if (titulo != null && !titulo.isEmpty())
        {
            return titulo;
        }
        else
        {
            return this.toString();
        }
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public Calendario getCalendario()
    {
        return calendario;
    }

    public void setCalendario(Calendario calendario)
    {
        this.calendario = calendario;
    }

    public Asignatura getAsignatura()
    {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura)
    {
        this.asignatura = asignatura;
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

    @Override
    public String toString()
    {
        return MessageFormat.format("{0} {1}{2}", getAsignatura().getId(), getCalendario()
                .getLetraId(), subgrupoId);
    }

    public Long getSubgrupoId()
    {
        return subgrupoId;
    }

    public void setSubgrupoId(Long subgrupoId)
    {
        this.subgrupoId = subgrupoId;
    }
}
