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

	//bi-directional many-to-one association to AulaDTO
    @ManyToOne
	@JoinColumn(name="AULA_ID")
	private AulaDTO aula;

	//bi-directional many-to-one association to EstudioDTO
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO estudio;

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

	public AulaDTO getAula() {
		return this.aula;
	}

	public void setAula(AulaDTO aula) {
		this.aula = aula;
	}
	
	public EstudioDTO getEstudio() {
		return this.estudio;
	}

	public void setEstudio(EstudioDTO estudio) {
		this.estudio = estudio;
	}
	
}