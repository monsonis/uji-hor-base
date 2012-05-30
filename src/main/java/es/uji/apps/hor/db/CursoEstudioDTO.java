package es.uji.apps.hor.db;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


/**
 * The persistent class for the HOR_V_CURSOS database table.
 * 
 */
@Entity
@IdClass(CursoEstudioIdDTO.class)
@Table(name="HOR_V_CURSOS")
public class CursoEstudioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CURSO_ID")
	private BigDecimal cursoId;

	private String estudio;

    @Id
	@Column(name="ESTUDIO_ID")
	private BigDecimal estudioId;

    public CursoEstudioDTO() {
    }

	public BigDecimal getCursoId() {
		return this.cursoId;
	}

	public void setCursoId(BigDecimal cursoId) {
		this.cursoId = cursoId;
	}

	public String getEstudio() {
		return this.estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public BigDecimal getEstudioId() {
		return this.estudioId;
	}

	public void setEstudioId(BigDecimal estudioId) {
		this.estudioId = estudioId;
	}

}