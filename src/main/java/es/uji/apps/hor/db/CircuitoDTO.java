package es.uji.apps.hor.db;

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
public class CircuitoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="CIRCUITO_ID")
	private BigDecimal circuitoId;

	private BigDecimal especial;

	@Column(name="GRUPO_ID")
	private String grupoId;

	private String nombre;

	//bi-directional many-to-one association to EstudioDTO
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO horEstudio;

	//bi-directional many-to-one association to ItemDTO
	@OneToMany(mappedBy="horCircuito")
	private Set<ItemDTO> horItems;

    public CircuitoDTO() {
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

	public String getGrupoId() {
		return this.grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EstudioDTO getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(EstudioDTO horEstudio) {
		this.horEstudio = horEstudio;
	}
	
	public Set<ItemDTO> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<ItemDTO> horItems) {
		this.horItems = horItems;
	}
	
}