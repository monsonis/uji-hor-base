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

	//bi-directional many-to-one association to AreaDTO
	@OneToMany(mappedBy="departamento")
	private Set<AreaDTO> areas;

	//bi-directional many-to-one association to CentroDTO
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private CentroDTO centro;

	//bi-directional many-to-one association to CargoPersonaDTO
	@OneToMany(mappedBy="departamento")
	private Set<CargoPersonaDTO> cargosPers;

	//bi-directional many-to-one association to PersonaDTO
	@OneToMany(mappedBy="departamento")
	private Set<PersonaDTO> personas;

	//bi-directional many-to-one association to PermisoExtraDTO
	@OneToMany(mappedBy="departamento")
	private Set<PermisoExtraDTO> permisosExtras;

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

	public Set<AreaDTO> getAreas() {
		return this.areas;
	}

	public void setAreas(Set<AreaDTO> areas) {
		this.areas = areas;
	}
	
	public CentroDTO getCentro() {
		return this.centro;
	}

	public void setCentro(CentroDTO centro) {
		this.centro = centro;
	}
	
	public Set<CargoPersonaDTO> getCargosPers() {
		return this.cargosPers;
	}

	public void setCargosPers(Set<CargoPersonaDTO> cargosPers) {
		this.cargosPers = cargosPers;
	}
	
	public Set<PersonaDTO> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<PersonaDTO> personas) {
		this.personas = personas;
	}
	
	public Set<PermisoExtraDTO> getPermisosExtras() {
		return this.permisosExtras;
	}

	public void setPermisosExtras(Set<PermisoExtraDTO> permisosExtras) {
		this.permisosExtras = permisosExtras;
	}
	
}