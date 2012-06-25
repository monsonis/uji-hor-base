package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the HOR_EXT_CIRCUITOS database table.
 * 
 */
@Entity
@Table(name="HOR_EXT_CIRCUITOS")
public class ExtCircuitoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="ASIGNATURA_ID")
	private String asignaturaId;

	@Column(name="CIRCUITO_ID")
	private BigDecimal circuitoId;

	@Column(name="CURSO_ACA")
	private BigDecimal cursoAca;

	@Column(name="DETALLE_ID")
	private BigDecimal detalleId;

	@Column(name="ESTUDIO_ID")
	private BigDecimal estudioId;

	@Column(name="GRUPO_ID")
	private String grupoId;

	private BigDecimal plazas;

	@Column(name="SUBGRUPO_ID")
	private BigDecimal subgrupoId;

	private String tipo;

    public ExtCircuitoDTO() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAsignaturaId() {
		return this.asignaturaId;
	}

	public void setAsignaturaId(String asignaturaId) {
		this.asignaturaId = asignaturaId;
	}

	public BigDecimal getCircuitoId() {
		return this.circuitoId;
	}

	public void setCircuitoId(BigDecimal circuitoId) {
		this.circuitoId = circuitoId;
	}

	public BigDecimal getCursoAca() {
		return this.cursoAca;
	}

	public void setCursoAca(BigDecimal cursoAca) {
		this.cursoAca = cursoAca;
	}

	public BigDecimal getDetalleId() {
		return this.detalleId;
	}

	public void setDetalleId(BigDecimal detalleId) {
		this.detalleId = detalleId;
	}

	public BigDecimal getEstudioId() {
		return this.estudioId;
	}

	public void setEstudioId(BigDecimal estudioId) {
		this.estudioId = estudioId;
	}

	public String getGrupoId() {
		return this.grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public BigDecimal getPlazas() {
		return this.plazas;
	}

	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}

	public BigDecimal getSubgrupoId() {
		return this.subgrupoId;
	}

	public void setSubgrupoId(BigDecimal subgrupoId) {
		this.subgrupoId = subgrupoId;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}