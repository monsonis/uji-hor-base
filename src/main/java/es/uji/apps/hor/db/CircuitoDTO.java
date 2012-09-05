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

	private BigDecimal especial;

	@Column(name="GRUPO_ID")
	private String grupoId;

	@Column(name="ID_CIRCUITO")
	private BigDecimal idCircuito;

	private String nombre;

	//bi-directional many-to-one association to EstudioDTO
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO estudio;

	//bi-directional many-to-one association to ItemCircuitoDTO
	@OneToMany(mappedBy="circuito")
	private Set<ItemCircuitoDTO> itemsCircuitos;

    public CircuitoDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getIdCircuito() {
		return this.idCircuito;
	}

	public void setIdCircuito(BigDecimal idCircuito) {
		this.idCircuito = idCircuito;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EstudioDTO getEstudio() {
		return this.estudio;
	}

	public void setEstudio(EstudioDTO estudio) {
		this.estudio = estudio;
	}
	
	public Set<ItemCircuitoDTO> getItemsCircuitos() {
		return this.itemsCircuitos;
	}

	public void setItemsCircuitos(Set<ItemCircuitoDTO> itemsCircuitos) {
		this.itemsCircuitos = itemsCircuitos;
	}
	
}