package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_EXT_PERSONAS database table.
 * 
 */
@Entity
@Table(name="HOR_EXT_PERSONAS")
public class Persona implements Serializable {
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
	private Set<CargoPersona> horExtCargosPers;

	//bi-directional many-to-one association to Departamento
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private Departamento horDepartamento;

	//bi-directional many-to-one association to PermisoExtra
	@OneToMany(mappedBy="horExtPersona")
	private Set<PermisoExtra> horPermisosExtras;

    public Persona() {
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

	public Set<CargoPersona> getHorExtCargosPers() {
		return this.horExtCargosPers;
	}

	public void setHorExtCargosPers(Set<CargoPersona> horExtCargosPers) {
		this.horExtCargosPers = horExtCargosPers;
	}
	
	public Departamento getHorDepartamento() {
		return this.horDepartamento;
	}

	public void setHorDepartamento(Departamento horDepartamento) {
		this.horDepartamento = horDepartamento;
	}
	
	public Set<PermisoExtra> getHorPermisosExtras() {
		return this.horPermisosExtras;
	}

	public void setHorPermisosExtras(Set<PermisoExtra> horPermisosExtras) {
		this.horPermisosExtras = horPermisosExtras;
	}
	
}