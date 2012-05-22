package es.uji.apps.hor.model;

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
public class AulaPlanificacion implements Serializable {
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

	//bi-directional many-to-one association to Aula
    @ManyToOne
	@JoinColumn(name="AULA_ID")
	private Aula horAula;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horAulasPlanificacion")
	private Set<Item> horItems;

    public AulaPlanificacion() {
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

	public Aula getHorAula() {
		return this.horAula;
	}

	public void setHorAula(Aula horAula) {
		this.horAula = horAula;
	}
	
	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
}