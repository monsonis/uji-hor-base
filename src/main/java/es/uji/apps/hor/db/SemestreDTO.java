package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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
	private Set<ItemDTO> items;

	//bi-directional many-to-one association to DetalleSemestreDTO
	@OneToMany(mappedBy="semestre")
	private Set<DetalleSemestreDTO> detalleSemestres;

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

	public Set<ItemDTO> getItems() {
		return this.items;
	}

	public void setItems(Set<ItemDTO> items) {
		this.items = items;
	}
	
	public Set<DetalleSemestreDTO> getDetalleSemestres() {
		return this.detalleSemestres;
	}

	public void setDetalleSemestres(Set<DetalleSemestreDTO> detalleSemestres) {
		this.detalleSemestres = detalleSemestres;
	}
	
}