package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the HOR_AULAS_ESTUDIO database table.
 * 
 */
@Entity
@Table(name="HOR_AULAS_ESTUDIO")
public class AulaEstudioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String descripcion;

	//bi-directional many-to-one association to Aula
    @ManyToOne
	@JoinColumn(name="AULA_ID")
	private AulaDTO horAula;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO horEstudio;

    public AulaEstudioDTO() {
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

	public AulaDTO getHorAula() {
		return this.horAula;
	}

	public void setHorAula(AulaDTO horAula) {
		this.horAula = horAula;
	}
	
	public EstudioDTO getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(EstudioDTO horEstudio) {
		this.horEstudio = horEstudio;
	}
	
}