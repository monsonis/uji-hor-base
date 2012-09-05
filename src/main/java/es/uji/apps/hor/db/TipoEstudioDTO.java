package es.uji.apps.hor.db;

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
public class TipoEstudioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String nombre;

	private BigDecimal orden;

	//bi-directional many-to-one association to EstudioDTO
	@OneToMany(mappedBy="tiposEstudio")
	private Set<EstudioDTO> estudios;

	//bi-directional many-to-one association to DetalleSemestreDTO
	@OneToMany(mappedBy="tiposEstudio")
	private Set<DetalleSemestreDTO> detalleSemestres;

    public TipoEstudioDTO() {
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

	public Set<EstudioDTO> getEstudios() {
		return this.estudios;
	}

	public void setEstudios(Set<EstudioDTO> estudios) {
		this.estudios = estudios;
	}
	
	public Set<DetalleSemestreDTO> getDetalleSemestres() {
		return this.detalleSemestres;
	}

	public void setDetalleSemestres(Set<DetalleSemestreDTO> detalleSemestres) {
		this.detalleSemestres = detalleSemestres;
	}
	
}