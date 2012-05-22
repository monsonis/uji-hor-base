package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the HOR_SEMESTRES_DETALLE database table.
 * 
 */
@Entity
@Table(name="HOR_SEMESTRES_DETALLE")
public class DetalleSemestre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_EXAMENES_FIN")
	private Date fechaExamenesFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_EXAMENES_INICIO")
	private Date fechaExamenesInicio;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

	@Column(name="NUMERO_SEMANAS")
	private BigDecimal numeroSemanas;

	//bi-directional many-to-one association to Semestre
    @ManyToOne
	@JoinColumn(name="SEMESTRE_ID")
	private Semestre horSemestre;

	//bi-directional many-to-one association to TipoEstudio
    @ManyToOne
	@JoinColumn(name="TIPO_ESTUDIO_ID")
	private TipoEstudio horTiposEstudio;

    public DetalleSemestre() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaExamenesFin() {
		return this.fechaExamenesFin;
	}

	public void setFechaExamenesFin(Date fechaExamenesFin) {
		this.fechaExamenesFin = fechaExamenesFin;
	}

	public Date getFechaExamenesInicio() {
		return this.fechaExamenesInicio;
	}

	public void setFechaExamenesInicio(Date fechaExamenesInicio) {
		this.fechaExamenesInicio = fechaExamenesInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public BigDecimal getNumeroSemanas() {
		return this.numeroSemanas;
	}

	public void setNumeroSemanas(BigDecimal numeroSemanas) {
		this.numeroSemanas = numeroSemanas;
	}

	public Semestre getHorSemestre() {
		return this.horSemestre;
	}

	public void setHorSemestre(Semestre horSemestre) {
		this.horSemestre = horSemestre;
	}
	
	public TipoEstudio getHorTiposEstudio() {
		return this.horTiposEstudio;
	}

	public void setHorTiposEstudio(TipoEstudio horTiposEstudio) {
		this.horTiposEstudio = horTiposEstudio;
	}
	
}