package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the HOR_HORARIOS_HORAS database table.
 * 
 */
@Entity
@SuppressWarnings("serial")
@Table(name="HOR_HORARIOS_HORAS")
public class GrupoHorarioDTO implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="CURSO_ID")
	private Long cursoId;

	@Column(name="ESTUDIO_ID")
	private Long estudioId;

	@Column(name="GRUPO_ID")
	private String grupoId;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="HORA_FIN")
	private Date horaFin;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="HORA_INICIO")
	private Date horaInicio;

	@Column(name="SEMESTRE_ID")
	private Long semestreId;

    public GrupoHorarioDTO() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCursoId() {
		return this.cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
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

	public Date getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public Date getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Long getSemestreId() {
		return this.semestreId;
	}

	public void setSemestreId(Long semestreId) {
		this.semestreId = semestreId;
	}

}