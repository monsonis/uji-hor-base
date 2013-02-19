package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the HOR_EXT_PERSONAS database table.
 * 
 */
@Entity
@Table(name="HOR_EXT_PERSONAS")
public class PersonaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="ACTIVIDAD_ID")
	private String actividadId;

	private String email;

	private String nombre;

	//bi-directional many-to-one association to CargoPersonaDTO
	@OneToMany(mappedBy="persona")
	private Set<CargoPersonaDTO> cargosPersona;

	//bi-directional many-to-one association to DepartamentoDTO
	// Lo comentamos temporalmente
//    @ManyToOne
//	@JoinColumn(name="DEPARTAMENTO_ID")
//	private DepartamentoDTO departamento;

	//bi-directional many-to-one association to PermisoExtraDTO
	@OneToMany(mappedBy="persona")
	private Set<PermisoExtraDTO> permisosExtras;

    public PersonaDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActividadId() {
		return this.actividadId;
	}

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
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

	public Set<CargoPersonaDTO> getCargosPersona() {
		return this.cargosPersona;
	}

	public void setCargosPersona(Set<CargoPersonaDTO> cargosPersona) {
		this.cargosPersona = cargosPersona;
	}
	
//	public DepartamentoDTO getDepartamento() {
//		return this.departamento;
//	}
//
//	public void setDepartamento(DepartamentoDTO departamento) {
//		this.departamento = departamento;
//	}
	
	public Set<PermisoExtraDTO> getPermisosExtras() {
		return this.permisosExtras;
	}

	public void setPermisosExtras(Set<PermisoExtraDTO> permisosExtras) {
		this.permisosExtras = permisosExtras;
	}
	
}