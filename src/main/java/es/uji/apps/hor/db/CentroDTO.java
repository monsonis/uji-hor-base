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
@SuppressWarnings("serial")
public class CentroDTO implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to AulaDTO
	@OneToMany(mappedBy="centro")
	private Set<AulaDTO> aulas;

	//bi-directional many-to-one association to DepartamentoDTO
	@OneToMany(mappedBy="centro")
	private Set<DepartamentoDTO> departamentos;

	//bi-directional many-to-one association to EstudioDTO
	@OneToMany(mappedBy="centro")
	private Set<EstudioDTO> estudios;

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

	public Set<AulaDTO> getAulas() {
		return this.aulas;
	}

	public void setAulas(Set<AulaDTO> aulas) {
		this.aulas = aulas;
	}
	
	public Set<DepartamentoDTO> getDepartamentos() {
		return this.departamentos;
	}

	public void setDepartamentos(Set<DepartamentoDTO> departamentos) {
		this.departamentos = departamentos;
	}
	
	public Set<EstudioDTO> getEstudios() {
		return this.estudios;
	}

	public void setEstudios(Set<EstudioDTO> estudios) {
		this.estudios = estudios;
	}
	
}