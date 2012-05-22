package es.uji.apps.hor.model;

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
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private BigDecimal activa;

	private String nombre;

	//bi-directional many-to-one association to Departamento
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private Departamento horDepartamento;

	//bi-directional many-to-one association to AsignaturaArea
	@OneToMany(mappedBy="horArea")
	private Set<AsignaturaArea> horAsignaturasAreas;

	//bi-directional many-to-one association to Profesor
	@OneToMany(mappedBy="horArea")
	private Set<Profesor> horProfesores;

    public Area() {
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

	public Departamento getHorDepartamento() {
		return this.horDepartamento;
	}

	public void setHorDepartamento(Departamento horDepartamento) {
		this.horDepartamento = horDepartamento;
	}
	
	public Set<AsignaturaArea> getHorAsignaturasAreas() {
		return this.horAsignaturasAreas;
	}

	public void setHorAsignaturasAreas(Set<AsignaturaArea> horAsignaturasAreas) {
		this.horAsignaturasAreas = horAsignaturasAreas;
	}
	
	public Set<Profesor> getHorProfesores() {
		return this.horProfesores;
	}

	public void setHorProfesores(Set<Profesor> horProfesores) {
		this.horProfesores = horProfesores;
	}
	
}