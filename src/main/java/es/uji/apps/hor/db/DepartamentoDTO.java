package es.uji.apps.hor.db;

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
public class DepartamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private BigDecimal activo;

	private String nombre;

	//bi-directional many-to-one association to Area
	@OneToMany(mappedBy="horDepartamento")
	private Set<AreaDTO> horAreas;

	//bi-directional many-to-one association to Centro
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private CentroDTO horCentro;

	//bi-directional many-to-one association to CargoPersona
	@OneToMany(mappedBy="horDepartamento")
	private Set<CargoPersonaDTO> horExtCargosPers;

	//bi-directional many-to-one association to Persona
	@OneToMany(mappedBy="horDepartamento")
	private Set<PersonaDTO> horExtPersonas;

	//bi-directional many-to-one association to PermisoExtra
	@OneToMany(mappedBy="horDepartamento")
	private Set<PermisoExtraDTO> horPermisosExtras;

    public DepartamentoDTO() {
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

	public Set<AreaDTO> getHorAreas() {
		return this.horAreas;
	}

	public void setHorAreas(Set<AreaDTO> horAreas) {
		this.horAreas = horAreas;
	}
	
	public CentroDTO getHorCentro() {
		return this.horCentro;
	}

	public void setHorCentro(CentroDTO horCentro) {
		this.horCentro = horCentro;
	}
	
	public Set<CargoPersonaDTO> getHorExtCargosPers() {
		return this.horExtCargosPers;
	}

	public void setHorExtCargosPers(Set<CargoPersonaDTO> horExtCargosPers) {
		this.horExtCargosPers = horExtCargosPers;
	}
	
	public Set<PersonaDTO> getHorExtPersonas() {
		return this.horExtPersonas;
	}

	public void setHorExtPersonas(Set<PersonaDTO> horExtPersonas) {
		this.horExtPersonas = horExtPersonas;
	}
	
	public Set<PermisoExtraDTO> getHorPermisosExtras() {
		return this.horPermisosExtras;
	}

	public void setHorPermisosExtras(Set<PermisoExtraDTO> horPermisosExtras) {
		this.horPermisosExtras = horPermisosExtras;
	}
	
}