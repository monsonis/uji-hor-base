package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the HOR_V_GRUPOS database table.
 * 
 */
@Entity
@Table(name="HOR_V_GRUPOS")
public class GrupoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String especial;

	private String estudio;

	@Column(name="ESTUDIO_ID")
	private BigDecimal estudioId;

	@Column(name="GRUPO_ID")
	private String grupoId;

    public GrupoDTO() {
    }

	public String getEspecial() {
		return this.especial;
	}

	public void setEspecial(String especial) {
		this.especial = especial;
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

	public String getGrupoId() {
		return this.grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

}