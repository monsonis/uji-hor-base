package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_TIPOS_ESTUDIOS database table.
 * 
 */
@Entity
@Table(name="HOR_TIPOS_ESTUDIOS")
public class TipoEstudio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String nombre;

	private BigDecimal orden;

	//bi-directional many-to-one association to Estudio
	@OneToMany(mappedBy="horTiposEstudio")
	private Set<Estudio> horEstudios;

	//bi-directional many-to-one association to DetalleSemestre
	@OneToMany(mappedBy="horTiposEstudio")
	private Set<DetalleSemestre> horSemestresDetalles;

    public TipoEstudio() {
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

	public Set<Estudio> getHorEstudios() {
		return this.horEstudios;
	}

	public void setHorEstudios(Set<Estudio> horEstudios) {
		this.horEstudios = horEstudios;
	}
	
	public Set<DetalleSemestre> getHorSemestresDetalles() {
		return this.horSemestresDetalles;
	}

	public void setHorSemestresDetalles(Set<DetalleSemestre> horSemestresDetalles) {
		this.horSemestresDetalles = horSemestresDetalles;
	}
	
}