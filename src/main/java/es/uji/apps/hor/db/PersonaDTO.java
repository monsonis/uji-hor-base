package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


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

	//bi-directional many-to-one association to CargoPersona
	@OneToMany(mappedBy="horExtPersona")
	private Set<CargoPersonaDTO> horExtCargosPers;

	//bi-directional many-to-one association to Departamento
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private DepartamentoDTO horDepartamento;

	//bi-directional many-to-one association to PermisoExtra
	@OneToMany(mappedBy="horExtPersona")
	private Set<PermisoExtraDTO> horPermisosExtras;

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

	public Set<CargoPersonaDTO> getHorExtCargosPers() {
		return this.horExtCargosPers;
	}

	public void setHorExtCargosPers(Set<CargoPersonaDTO> horExtCargosPers) {
		this.horExtCargosPers = horExtCargosPers;
	}
	
	public DepartamentoDTO getHorDepartamento() {
		return this.horDepartamento;
	}

	public void setHorDepartamento(DepartamentoDTO horDepartamento) {
		this.horDepartamento = horDepartamento;
	}
	
	public Set<PermisoExtraDTO> getHorPermisosExtras() {
		return this.horPermisosExtras;
	}

	public void setHorPermisosExtras(Set<PermisoExtraDTO> horPermisosExtras) {
		this.horPermisosExtras = horPermisosExtras;
	}
	
}