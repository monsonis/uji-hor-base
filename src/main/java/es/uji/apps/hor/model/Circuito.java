package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_CIRCUITOS database table.
 * 
 */
@Entity
@Table(name="HOR_CIRCUITOS")
public class Circuito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="CIRCUITO_ID")
	private BigDecimal circuitoId;

	private BigDecimal especial;

	private String nombre;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private Estudio horEstudio;

	//bi-directional many-to-one association to Grupo
    @ManyToOne
	@JoinColumn(name="GRUPO_ID")
	private Grupo horGrupo;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horCircuito")
	private Set<Item> horItems;

    public Circuito() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCircuitoId() {
		return this.circuitoId;
	}

	public void setCircuitoId(BigDecimal circuitoId) {
		this.circuitoId = circuitoId;
	}

	public BigDecimal getEspecial() {
		return this.especial;
	}

	public void setEspecial(BigDecimal especial) {
		this.especial = especial;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Estudio getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(Estudio horEstudio) {
		this.horEstudio = horEstudio;
	}
	
	public Grupo getHorGrupo() {
		return this.horGrupo;
	}

	public void setHorGrupo(Grupo horGrupo) {
		this.horGrupo = horGrupo;
	}
	
	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
}