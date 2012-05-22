package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_SEMESTRES database table.
 * 
 */
@Entity
@Table(name="HOR_SEMESTRES")
public class Semestre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to GrupoAsignatura
	@OneToMany(mappedBy="horSemestre")
	private Set<GrupoAsignatura> horGruposAsignaturas;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horSemestre")
	private Set<Item> horItems;

	//bi-directional many-to-one association to DetalleSemestre
	@OneToMany(mappedBy="horSemestre")
	private Set<DetalleSemestre> horSemestresDetalles;

    public Semestre() {
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

	public Set<GrupoAsignatura> getHorGruposAsignaturas() {
		return this.horGruposAsignaturas;
	}

	public void setHorGruposAsignaturas(Set<GrupoAsignatura> horGruposAsignaturas) {
		this.horGruposAsignaturas = horGruposAsignaturas;
	}
	
	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
	public Set<DetalleSemestre> getHorSemestresDetalles() {
		return this.horSemestresDetalles;
	}

	public void setHorSemestresDetalles(Set<DetalleSemestre> horSemestresDetalles) {
		this.horSemestresDetalles = horSemestresDetalles;
	}
	
}