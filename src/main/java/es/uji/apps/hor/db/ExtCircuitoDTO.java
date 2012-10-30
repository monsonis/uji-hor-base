package es.uji.apps.hor.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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
	private Long circuitoId;

	@Column(name="CURSO_ACA")
	private Long cursoAca;

	@Column(name="DETALLE_ID")
	private Long detalleId;

	@Column(name="ESTUDIO_ID")
	private Long estudioId;

	@Column(name="GRUPO_ID")
	private String grupoId;

	private Long plazas;

	@Column(name="SUBGRUPO_ID")
	private Long subgrupoId;

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

	public Long getCircuitoId() {
		return this.circuitoId;
	}

	public void setCircuitoId(Long circuitoId) {
		this.circuitoId = circuitoId;
	}

	public Long getCursoAca() {
		return this.cursoAca;
	}

	public void setCursoAca(Long cursoAca) {
		this.cursoAca = cursoAca;
	}

	public Long getDetalleId() {
		return this.detalleId;
	}

	public void setDetalleId(Long detalleId) {
		this.detalleId = detalleId;
	}

	public Long getEstudioId() {
		return this.estudioId;
	}

	public void setEstudioId(Long estudioId) {
		this.estudioId = estudioId;
	}

	public String getGrupoId() {
		return this.grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public Long getPlazas() {
		return this.plazas;
	}

	public void setPlazas(Long plazas) {
		this.plazas = plazas;
	}

	public Long getSubgrupoId() {
		return this.subgrupoId;
	}

	public void setSubgrupoId(Long subgrupoId) {
		this.subgrupoId = subgrupoId;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}