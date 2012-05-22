package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_CURSOS database table.
 * 
 */
@Entity
@Table(name="HOR_CURSOS")
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	//bi-directional many-to-one association to Asignatura
	@OneToMany(mappedBy="horCurso")
	private Set<Asignatura> horAsignaturas;

	//bi-directional many-to-one association to EstudioCurso
	@OneToMany(mappedBy="horCurso")
	private Set<EstudioCurso> horCursosEstudios;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horCurso")
	private Set<Item> horItems;

    public Curso() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Asignatura> getHorAsignaturas() {
		return this.horAsignaturas;
	}

	public void setHorAsignaturas(Set<Asignatura> horAsignaturas) {
		this.horAsignaturas = horAsignaturas;
	}
	
	public Set<EstudioCurso> getHorCursosEstudios() {
		return this.horCursosEstudios;
	}

	public void setHorCursosEstudios(Set<EstudioCurso> horCursosEstudios) {
		this.horCursosEstudios = horCursosEstudios;
	}
	
	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
}