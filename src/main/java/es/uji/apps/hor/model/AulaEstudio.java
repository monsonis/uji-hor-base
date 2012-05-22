package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the HOR_AULAS_ESTUDIO database table.
 * 
 */
@Entity
@Table(name="HOR_AULAS_ESTUDIO")
public class AulaEstudio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String descripcion;

	//bi-directional many-to-one association to Aula
    @ManyToOne
	@JoinColumn(name="AULA_ID")
	private Aula horAula;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private Estudio horEstudio;

    public AulaEstudio() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Aula getHorAula() {
		return this.horAula;
	}

	public void setHorAula(Aula horAula) {
		this.horAula = horAula;
	}
	
	public Estudio getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(Estudio horEstudio) {
		this.horEstudio = horEstudio;
	}
	
}