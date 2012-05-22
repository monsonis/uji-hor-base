package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the HOR_GRUPOS_ASIGNATURAS database table.
 * 
 */
@Entity
@Table(name="HOR_GRUPOS_ASIGNATURAS")
public class GrupoAsignatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="LIMITE_NUEVOS")
	private BigDecimal limiteNuevos;

	@Column(name="LIMITE_REPETIDORES")
	private BigDecimal limiteRepetidores;

	//bi-directional many-to-one association to Asignatura
    @ManyToOne
	@JoinColumn(name="ASIGNATURA_ID")
	private Asignatura horAsignatura;

	//bi-directional many-to-one association to Grupo
    @ManyToOne
	@JoinColumn(name="GRUPO_ID")
	private Grupo horGrupo;

	//bi-directional many-to-one association to Semestre
    @ManyToOne
	@JoinColumn(name="SEMESTRE_ID")
	private Semestre horSemestre;

    public GrupoAsignatura() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getLimiteNuevos() {
		return this.limiteNuevos;
	}

	public void setLimiteNuevos(BigDecimal limiteNuevos) {
		this.limiteNuevos = limiteNuevos;
	}

	public BigDecimal getLimiteRepetidores() {
		return this.limiteRepetidores;
	}

	public void setLimiteRepetidores(BigDecimal limiteRepetidores) {
		this.limiteRepetidores = limiteRepetidores;
	}

	public Asignatura getHorAsignatura() {
		return this.horAsignatura;
	}

	public void setHorAsignatura(Asignatura horAsignatura) {
		this.horAsignatura = horAsignatura;
	}
	
	public Grupo getHorGrupo() {
		return this.horGrupo;
	}

	public void setHorGrupo(Grupo horGrupo) {
		this.horGrupo = horGrupo;
	}
	
	public Semestre getHorSemestre() {
		return this.horSemestre;
	}

	public void setHorSemestre(Semestre horSemestre) {
		this.horSemestre = horSemestre;
	}
	
}