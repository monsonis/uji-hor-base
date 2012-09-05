package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_PROFESORES database table.
 * 
 */
@Entity
@Table(name="HOR_PROFESORES")
public class ProfesorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="DEPARTAMENTO_ID")
	private BigDecimal departamentoId;

	private String email;

	private String nombre;

	@Column(name="PENDIENTE_CONTRATACION")
	private BigDecimal pendienteContratacion;

	//bi-directional many-to-one association to ItemDTO
	@OneToMany(mappedBy="profesor")
	private Set<ItemDTO> items;

	//bi-directional many-to-one association to AreaDTO
    @ManyToOne
	@JoinColumn(name="AREA_ID")
	private AreaDTO area;

    public ProfesorDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getDepartamentoId() {
		return this.departamentoId;
	}

	public void setDepartamentoId(BigDecimal departamentoId) {
		this.departamentoId = departamentoId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPendienteContratacion() {
		return this.pendienteContratacion;
	}

	public void setPendienteContratacion(BigDecimal pendienteContratacion) {
		this.pendienteContratacion = pendienteContratacion;
	}

	public Set<ItemDTO> getItems() {
		return this.items;
	}

	public void setItems(Set<ItemDTO> items) {
		this.items = items;
	}
	
	public AreaDTO getArea() {
		return this.area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}
	
}