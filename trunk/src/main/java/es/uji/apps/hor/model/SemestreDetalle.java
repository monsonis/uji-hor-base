package es.uji.apps.hor.model;

import java.util.Date;

public class SemestreDetalle
{
    private Long id;
    private Semestre semestre;
    private TipoEstudio tipoEstudio;
    private Date fechaInicio = null;
    private Date fechaFin = null;
    private Date fechaExamenesInicio = null;
    private Date fechaExamenesFin = null;
    private Long numeroSemanas;

    public SemestreDetalle(Long id, Semestre semestre, TipoEstudio tipoEstudio, Date fechaInicio,
            Date fechaFin, Long numeroSemanas)
    {
        super();
        this.id = id;
        this.semestre = semestre;
        this.tipoEstudio = tipoEstudio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroSemanas = numeroSemanas;
    }

    public SemestreDetalle(Long id, Semestre semestre, TipoEstudio tipoEstudio, Date fechaInicio,
            Date fechaFin, Date fechaExamenesInicio, Date fechaExamenesFin, Long numeroSemanas)
    {
        super();
        this.id = id;
        this.semestre = semestre;
        this.tipoEstudio = tipoEstudio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaExamenesInicio = fechaExamenesInicio;
        this.fechaExamenesFin = fechaExamenesFin;
        this.numeroSemanas = numeroSemanas;
    }
    
    public SemestreDetalle()
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

    public Semestre getSemestre()
    {
        return semestre;
    }

    public void setSemestre(Semestre semestre)
    {
        this.semestre = semestre;
    }

    public TipoEstudio getTipoEstudio()
    {
        return tipoEstudio;
    }

    public void setTipoEstudio(TipoEstudio tipoEstudio)
    {
        this.tipoEstudio = tipoEstudio;
    }

    public Date getFechaInicio()
    {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin()
    {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin)
    {
        this.fechaFin = fechaFin;
    }

    public Date getFechaExamenesInicio()
    {
        return fechaExamenesInicio;
    }

    public void setFechaExamenesInicio(Date fechaExamenesInicio)
    {
        this.fechaExamenesInicio = fechaExamenesInicio;
    }

    public Date getFechaExamenesFin()
    {
        return fechaExamenesFin;
    }

    public void setFechaExamenesFin(Date fechaExamenesFin)
    {
        this.fechaExamenesFin = fechaExamenesFin;
    }

    public Long getNumeroSemanas()
    {
        return numeroSemanas;
    }

    public void setNumeroSemanas(Long numeroSemanas)
    {
        this.numeroSemanas = numeroSemanas;
    }

}
