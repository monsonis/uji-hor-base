package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_DEPARTAMENTOS database table.
 * 
 */
@Entity
@Table(name="HOR_DEPARTAMENTOS")
public class Departamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private BigDecimal activo;

	private String nombre;

	//bi-directional many-to-one association to Area
	@OneToMany(mappedBy="horDepartamento")
	private Set<Area> horAreas;

	//bi-directional many-to-one association to Centro
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private Centro horCentro;

	//bi-directional many-to-one association to CargoPersona
	@OneToMany(mappedBy="horDepartamento")
	private Set<CargoPersona> horExtCargosPers;

	//bi-directional many-to-one association to Persona
	@OneToMany(mappedBy="horDepartamento")
	private Set<Persona> horExtPersonas;

	//bi-directional many-to-one association to PermisoExtra
	@OneToMany(mappedBy="horDepartamento")
	private Set<PermisoExtra> horPermisosExtras;

    public Departamento() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getActivo() {
		return this.activo;
	}

	public void setActivo(BigDecimal activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Area> getHorAreas() {
		return this.horAreas;
	}

	public void setHorAreas(Set<Area> horAreas) {
		this.horAreas = horAreas;
	}
	
	public Centro getHorCentro() {
		return this.horCentro;
	}

	public void setHorCentro(Centro horCentro) {
		this.horCentro = horCentro;
	}
	
	public Set<CargoPersona> getHorExtCargosPers() {
		return this.horExtCargosPers;
	}

	public void setHorExtCargosPers(Set<CargoPersona> horExtCargosPers) {
		this.horExtCargosPers = horExtCargosPers;
	}
	
	public Set<Persona> getHorExtPersonas() {
		return this.horExtPersonas;
	}

	public void setHorExtPersonas(Set<Persona> horExtPersonas) {
		this.horExtPersonas = horExtPersonas;
	}
	
	public Set<PermisoExtra> getHorPermisosExtras() {
		return this.horPermisosExtras;
	}

	public void setHorPermisosExtras(Set<PermisoExtra> horPermisosExtras) {
		this.horPermisosExtras = horPermisosExtras;
	}
	
}