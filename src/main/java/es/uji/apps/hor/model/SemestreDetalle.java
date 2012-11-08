package es.uji.apps.hor.model;

import java.util.Date;

public class SemestreDetalle
{
    private Long id;
    private Semestre semestre;
    private TipoEstudio tipo_estudio;
    private Date fecha_inicio = null;
    private Date fecha_fin = null;
    private Date fecha_examenes_inicio = null;
    private Date fecha_examenes_fin = null;
    private Long numero_semanas;

    public SemestreDetalle(Long id, Semestre semestre, TipoEstudio tipo_estudio, Date fecha_inicio,
            Date fecha_fin, Long numero_semanas)
    {
        super();
        this.id = id;
        this.semestre = semestre;
        this.tipo_estudio = tipo_estudio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.numero_semanas = numero_semanas;
    }

    public SemestreDetalle(Long id, Semestre semestre, TipoEstudio tipo_estudio, Date fecha_inicio,
            Date fecha_fin, Date fecha_examenes_inicio, Date fecha_examenes_fin, Long numero_semanas)
    {
        super();
        this.id = id;
        this.semestre = semestre;
        this.tipo_estudio = tipo_estudio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.fecha_examenes_inicio = fecha_examenes_inicio;
        this.fecha_examenes_fin = fecha_examenes_fin;
        this.numero_semanas = numero_semanas;
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

    public TipoEstudio getTipo_estudio()
    {
        return tipo_estudio;
    }

    public void setTipo_estudio(TipoEstudio tipo_estudio)
    {
        this.tipo_estudio = tipo_estudio;
    }

    public Date getFecha_inicio()
    {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio)
    {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin()
    {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin)
    {
        this.fecha_fin = fecha_fin;
    }

    public Date getFecha_examenes_inicio()
    {
        return fecha_examenes_inicio;
    }

    public void setFecha_examenes_inicio(Date fecha_examenes_inicio)
    {
        this.fecha_examenes_inicio = fecha_examenes_inicio;
    }

    public Date getFecha_examenes_fin()
    {
        return fecha_examenes_fin;
    }

    public void setFecha_examenes_fin(Date fecha_examenes_fin)
    {
        this.fecha_examenes_fin = fecha_examenes_fin;
    }

    public Long getNumero_semanas()
    {
        return numero_semanas;
    }

    public void setNumero_semanas(Long numero_semanas)
    {
        this.numero_semanas = numero_semanas;
    }

}
