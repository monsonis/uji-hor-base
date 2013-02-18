package es.uji.apps.hor.model;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

public class GrupoAsignatura
{

    private static final int HORA_INICIO_POR_DEFECTO = 8;
    private static final int MINUTO_INICIO_POR_DEFECTO = 0;
    private static final int SEGUNDO_INICIO_POR_DEFECTO = 0;

    private static final int HORA_FIN_POR_DEFECTO = 10;
    private static final int MINUTO_FIN_POR_DEFECTO = 0;
    private static final int SEGUNDO_FIN_POR_DEFECTO = 0;

    private static final String DIA_SEMANA_POR_DEFECTO = "Dilluns";

    private Long id;

    private String titulo;

    private Date inicio;

    private Date fin;

    private String diaSemana;

    private Calendario calendario;

    private Asignatura asignatura;

    private Long subgrupoId;

    private String grupoId;

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
        calInicio.set(Calendar.HOUR_OF_DAY, HORA_INICIO_POR_DEFECTO);
        calInicio.set(Calendar.MINUTE, MINUTO_INICIO_POR_DEFECTO);
        calInicio.set(Calendar.SECOND, SEGUNDO_INICIO_POR_DEFECTO);
        calFin.set(Calendar.HOUR_OF_DAY, HORA_FIN_POR_DEFECTO);
        calFin.set(Calendar.MINUTE, MINUTO_FIN_POR_DEFECTO);
        calFin.set(Calendar.SECOND, SEGUNDO_FIN_POR_DEFECTO);

        inicio = calInicio.getTime();
        fin = calFin.getTime();
        setDiaSemana(DIA_SEMANA_POR_DEFECTO);
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("{0} {1}{2} {3}", getAsignatura().getId(), getCalendario()
                .getLetraId(), subgrupoId, grupoId);
    }

    public Long getSubgrupoId()
    {
        return subgrupoId;
    }

    public void setSubgrupoId(Long subgrupoId)
    {
        this.subgrupoId = subgrupoId;
    }

    public String getGrupoId()
    {
        return grupoId;
    }

    public void setGrupoId(String grupoId)
    {
        this.grupoId = grupoId;
    }
}
