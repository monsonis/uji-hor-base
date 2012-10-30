package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the HOR_AULAS_PLANIFICACION database table.
 * 
 */
@Entity
@Table(name="HOR_AULAS_PLANIFICACION")
public class AulaPlanificacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="CURSO_ID")
	private Long cursoId;

	@Column(name="ESTUDIO_ID")
	private Long estudioId;

	private String nombre;

	@Column(name="SEMESTRE_ID")
	private Long semestreId;

	//bi-directional many-to-one association to AulaDTO
    @ManyToOne
	@JoinColumn(name="AULA_ID")
	private AulaDTO aula;

	//bi-directional many-to-one association to ItemDTO
	@OneToMany(mappedBy="aulasPlanificacion")
	private Set<ItemDTO> items;

    public AulaPlanificacionDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getSemestreId() {
		return this.semestreId;
	}

	public void setSemestreId(Long semestreId) {
		this.semestreId = semestreId;
	}

	public AulaDTO getAula() {
		return this.aula;
	}

	public void setAula(AulaDTO aula) {
		this.aula = aula;
	}
	
	public Set<ItemDTO> getItems() {
		return this.items;
	}

	public void setItems(Set<ItemDTO> items) {
		this.items = items;
	}
	
}