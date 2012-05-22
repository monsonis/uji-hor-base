package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_TIPOS_ASIGNATURAS database table.
 * 
 */
@Entity
@Table(name="HOR_TIPOS_ASIGNATURAS")
public class TipoAsignatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String nombre;

	private BigDecimal orden;

	//bi-directional many-to-one association to Asignatura
	@OneToMany(mappedBy="horTiposAsignatura")
	private Set<Asignatura> horAsignaturas;

    public TipoAsignatura() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getOrden() {
		return this.orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

	public Set<Asignatura> getHorAsignaturas() {
		return this.horAsignaturas;
	}

	public void setHorAsignaturas(Set<Asignatura> horAsignaturas) {
		this.horAsignaturas = horAsignaturas;
	}
	
}