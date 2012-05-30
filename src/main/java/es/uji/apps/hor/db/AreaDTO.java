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

	//bi-directional many-to-one association to Departamento
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private DepartamentoDTO horDepartamento;

	//bi-directional many-to-one association to Profesor
	@OneToMany(mappedBy="horArea")
	private Set<ProfesorDTO> horProfesores;

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

	public DepartamentoDTO getHorDepartamento() {
		return this.horDepartamento;
	}

	public void setHorDepartamento(DepartamentoDTO horDepartamento) {
		this.horDepartamento = horDepartamento;
	}
	
	public Set<ProfesorDTO> getHorProfesores() {
		return this.horProfesores;
	}

	public void setHorProfesores(Set<ProfesorDTO> horProfesores) {
		this.horProfesores = horProfesores;
	}
	
}