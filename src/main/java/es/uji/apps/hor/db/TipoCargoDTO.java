package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_TIPOS_CARGOS database table.
 * 
 */
@Entity
@Table(name="HOR_TIPOS_CARGOS")
public class TipoCargoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to CargoPersona
	@OneToMany(mappedBy="horTiposCargo")
	private Set<CargoPersonaDTO> horExtCargosPers;

	//bi-directional many-to-one association to PermisoExtra
	@OneToMany(mappedBy="horTiposCargo")
	private Set<PermisoExtraDTO> horPermisosExtras;

    public TipoCargoDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Set<PermisoExtraDTO> getHorPermisosExtras() {
		return this.horPermisosExtras;
	}

	public void setHorPermisosExtras(Set<PermisoExtraDTO> horPermisosExtras) {
		this.horPermisosExtras = horPermisosExtras;
	}
	
}