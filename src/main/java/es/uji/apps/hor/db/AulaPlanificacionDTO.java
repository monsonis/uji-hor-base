package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


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
	private BigDecimal cursoId;

	@Column(name="ESTUDIO_ID")
	private BigDecimal estudioId;

	private String nombre;

	@Column(name="SEMESTRE_ID")
	private BigDecimal semestreId;

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

	public BigDecimal getCursoId() {
		return this.cursoId;
	}

	public void setCursoId(BigDecimal cursoId) {
		this.cursoId = cursoId;
	}

	public BigDecimal getEstudioId() {
		return this.estudioId;
	}

	public void setEstudioId(BigDecimal estudioId) {
		this.estudioId = estudioId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getSemestreId() {
		return this.semestreId;
	}

	public void setSemestreId(BigDecimal semestreId) {
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