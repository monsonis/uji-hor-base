package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_SEMESTRES database table.
 * 
 */
@Entity
@Table(name="HOR_SEMESTRES")
public class SemestreDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to ItemDTO
	@OneToMany(mappedBy="semestre")
	private Set<ItemDTO> horItems;

	//bi-directional many-to-one association to DetalleSemestreDTO
	@OneToMany(mappedBy="horSemestre")
	private Set<DetalleSemestreDTO> horSemestresDetalles;

    public SemestreDTO() {
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

	public Set<ItemDTO> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<ItemDTO> horItems) {
		this.horItems = horItems;
	}
	
	public Set<DetalleSemestreDTO> getHorSemestresDetalles() {
		return this.horSemestresDetalles;
	}

	public void setHorSemestresDetalles(Set<DetalleSemestreDTO> horSemestresDetalles) {
		this.horSemestresDetalles = horSemestresDetalles;
	}
	
}