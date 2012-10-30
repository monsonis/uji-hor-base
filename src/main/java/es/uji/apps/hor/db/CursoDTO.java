package es.uji.apps.hor.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the HOR_V_CURSOS database table.
 * 
 */
@Entity
@Table(name="HOR_V_CURSOS")
public class CursoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CURSO_ID")
	private Long cursoId;

	private String estudio;

	@Column(name="ESTUDIO_ID")
	private Long estudioId;

    public CursoDTO() {
    }

	public Long getCursoId() {
		return this.cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public String getEstudio() {
		return this.estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public Long getEstudioId() {
		return this.estudioId;
	}

	public void setEstudioId(Long estudioId) {
		this.estudioId = estudioId;
	}

}