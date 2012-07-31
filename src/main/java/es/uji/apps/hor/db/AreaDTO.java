package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_AREAS database table.
 * 
 */
@Entity
@Table(name="HOR_AREAS")
public class AreaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private BigDecimal activa;

	private String nombre;

	//bi-directional many-to-one association to DepartamentoDTO
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private DepartamentoDTO departamento;

	//bi-directional many-to-one association to ProfesorDTO
	@OneToMany(mappedBy="area")
	private Set<ProfesorDTO> profesores;

    public AreaDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getActiva() {
		return this.activa;
	}

	public void setActiva(BigDecimal activa) {
		this.activa = activa;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DepartamentoDTO getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(DepartamentoDTO departamento) {
		this.departamento = departamento;
	}
	
	public Set<ProfesorDTO> getProfesores() {
		return this.profesores;
	}

	public void setProfesores(Set<ProfesorDTO> profesores) {
		this.profesores = profesores;
	}
	
}