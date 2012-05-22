package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_CENTROS database table.
 * 
 */
@Entity
@Table(name="HOR_CENTROS")
public class Centro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to Aula
	@OneToMany(mappedBy="horCentro")
	private Set<Aula> horAulas;

	//bi-directional many-to-one association to Departamento
	@OneToMany(mappedBy="horCentro")
	private Set<Departamento> horDepartamentos;

	//bi-directional many-to-one association to Estudio
	@OneToMany(mappedBy="horCentro")
	private Set<Estudio> horEstudios;

    public Centro() {
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

	public Set<Aula> getHorAulas() {
		return this.horAulas;
	}

	public void setHorAulas(Set<Aula> horAulas) {
		this.horAulas = horAulas;
	}
	
	public Set<Departamento> getHorDepartamentos() {
		return this.horDepartamentos;
	}

	public void setHorDepartamentos(Set<Departamento> horDepartamentos) {
		this.horDepartamentos = horDepartamentos;
	}
	
	public Set<Estudio> getHorEstudios() {
		return this.horEstudios;
	}

	public void setHorEstudios(Set<Estudio> horEstudios) {
		this.horEstudios = horEstudios;
	}
	
}