package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the HOR_DIAS_SEMANA database table.
 * 
 */
@Entity
@Table(name="HOR_DIAS_SEMANA")
public class DiaSemana implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horDiasSemana")
	private Set<Item> horItems;

    public DiaSemana() {
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

	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
}