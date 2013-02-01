package es.uji.apps.hor.db;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the HOR_V_ITEMS_DETALLE database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@IdClass(ItemDetalleCompletoDTOId.class)
@Table(name = "HOR_V_ITEMS_DETALLE")
public class ItemDetalleCompletoDTO implements Serializable
{
    @Column(name = "ASIGNATURA_ID")
    private String asignaturaId;

    @Column(name = "CURSO_ID")
    private Long cursoId;

    @Column(name = "DIA_SEMANA_ID")
    private Long diaSemanaId;

    private String docencia;

    @Column(name = "DOCENCIA_PASO_1")
    private String docenciaPaso1;

    @Column(name = "DOCENCIA_PASO_2")
    private String docenciaPaso2;

    @Column(name = "ESTUDIO_ID")
    private Long estudioId;

    @Id
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_FIN")
    private Date fechaFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;

    @Column(name = "GRUPO_ID")
    private String grupoId;

    @Id
    private Long id;

    @Column(name = "NUMERO_ITERACIONES")
    private Long numeroIteraciones;

    @Column(name = "ORDEN_ID")
    private Long ordenId;

    @Column(name = "REPETIR_CADA_SEMANAS")
    private Long repetirCadaSemanas;

    @Column(name = "SEMESTRE_ID")
    private Long semestreId;

    @Column(name = "SUBGRUPO_ID")
    private Long subgrupoId;

    @Column(name = "TIPO_SUBGRUPO_ID")
    private String tipoSubgrupoId;

    private BigDecimal festivos;

    @Column(name = "TIPO_DIA")
    private String tipoDia;

    public ItemDetalleCompletoDTO()
    {
    }

    public String getAsignaturaId()
    {
        return this.asignaturaId;
    }

    public void setAsignaturaId(String asignaturaId)
    {
        this.asignaturaId = asignaturaId;
    }

    public Long getCursoId()
    {
        return this.cursoId;
    }

    public void setCursoId(Long cursoId)
    {
        this.cursoId = cursoId;
    }

    public Long getDiaSemanaId()
    {
        return this.diaSemanaId;
    }

    public void setDiaSemanaId(Long diaSemanaId)
    {
        this.diaSemanaId = diaSemanaId;
    }

    public String getDocencia()
    {
        return this.docencia;
    }

    public void setDocencia(String docencia)
    {
        this.docencia = docencia;
    }

    public String getDocenciaPaso1()
    {
        return this.docenciaPaso1;
    }

    public void setDocenciaPaso1(String docenciaPaso1)
    {
        this.docenciaPaso1 = docenciaPaso1;
    }

    public String getDocenciaPaso2()
    {
        return this.docenciaPaso2;
    }

    public void setDocenciaPaso2(String docenciaPaso2)
    {
        this.docenciaPaso2 = docenciaPaso2;
    }

    public Long getEstudioId()
    {
        return this.estudioId;
    }

    public void setEstudioId(Long estudioId)
    {
        this.estudioId = estudioId;
    }

    public Date getFecha()
    {
        return this.fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Date getFechaFin()
    {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin)
    {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio()
    {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    public String getGrupoId()
    {
        return this.grupoId;
    }

    public void setGrupoId(String grupoId)
    {
        this.grupoId = grupoId;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getNumeroIteraciones()
    {
        return this.numeroIteraciones;
    }

    public void setNumeroIteraciones(Long numeroIteraciones)
    {
        this.numeroIteraciones = numeroIteraciones;
    }

    public Long getOrdenId()
    {
        return this.ordenId;
    }

    public void setOrdenId(Long ordenId)
    {
        this.ordenId = ordenId;
    }

    public Long getRepetirCadaSemanas()
    {
        return this.repetirCadaSemanas;
    }

    public void setRepetirCadaSemanas(Long repetirCadaSemanas)
    {
        this.repetirCadaSemanas = repetirCadaSemanas;
    }

    public Long getSemestreId()
    {
        return this.semestreId;
    }

    public void setSemestreId(Long semestreId)
    {
        this.semestreId = semestreId;
    }

    public Long getSubgrupoId()
    {
        return this.subgrupoId;
    }

    public void setSubgrupoId(Long subgrupoId)
    {
        this.subgrupoId = subgrupoId;
    }

    public String getTipoSubgrupoId()
    {
        return this.tipoSubgrupoId;
    }

    public void setTipoSubgrupoId(String tipoSubgrupoId)
    {
        this.tipoSubgrupoId = tipoSubgrupoId;
    }

    public BigDecimal getFestivos()
    {
        return festivos;
    }

    public void setFestivos(BigDecimal festivos)
    {
        this.festivos = festivos;
    }

    public String getTipoDia()
    {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia)
    {
        this.tipoDia = tipoDia;
    }

}