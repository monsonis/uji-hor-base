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
	private Long departamentoId;

	private String email;

	private String nombre;

	@Column(name="PENDIENTE_CONTRATACION")
	private Long pendienteContratacion;

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

	public Long getDepartamentoId() {
		return this.departamentoId;
	}

	public void setDepartamentoId(Long departamentoId) {
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

	public Long getPendienteContratacion() {
		return this.pendienteContratacion;
	}

	public void setPendienteContratacion(Long pendienteContratacion) {
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