package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_CENTROS database table.
 * 
 */
@Entity
@Table(name="HOR_CENTROS")
public class CentroDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to AulaDTO
	@OneToMany(mappedBy="horCentro")
	private Set<AulaDTO> horAulas;

	//bi-directional many-to-one association to DepartamentoDTO
	@OneToMany(mappedBy="horCentro")
	private Set<DepartamentoDTO> horDepartamentos;

	//bi-directional many-to-one association to EstudioDTO
	@OneToMany(mappedBy="horCentro")
	private Set<EstudioDTO> horEstudios;

    public CentroDTO() {
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

	public Set<AulaDTO> getHorAulas() {
		return this.horAulas;
	}

	public void setHorAulas(Set<AulaDTO> horAulas) {
		this.horAulas = horAulas;
	}
	
	public Set<DepartamentoDTO> getHorDepartamentos() {
		return this.horDepartamentos;
	}

	public void setHorDepartamentos(Set<DepartamentoDTO> horDepartamentos) {
		this.horDepartamentos = horDepartamentos;
	}
	
	public Set<EstudioDTO> getHorEstudios() {
		return this.horEstudios;
	}

	public void setHorEstudios(Set<EstudioDTO> horEstudios) {
		this.horEstudios = horEstudios;
	}
	
}