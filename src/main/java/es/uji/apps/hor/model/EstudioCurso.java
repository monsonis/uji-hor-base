package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the HOR_CURSOS_ESTUDIOS database table.
 * 
 */
@Entity
@Table(name="HOR_CURSOS_ESTUDIOS")
public class EstudioCurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	//bi-directional many-to-one association to Curso
    @ManyToOne
	@JoinColumn(name="CURSO_ID")
	private Curso horCurso;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private Estudio horEstudio;

    public EstudioCurso() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Curso getHorCurso() {
		return this.horCurso;
	}

	public void setHorCurso(Curso horCurso) {
		this.horCurso = horCurso;
	}
	
	public Estudio getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(Estudio horEstudio) {
		this.horEstudio = horEstudio;
	}
	
}