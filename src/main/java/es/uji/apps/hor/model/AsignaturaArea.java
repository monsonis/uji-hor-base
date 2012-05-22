package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the HOR_ASIGNATURAS_AREA database table.
 * 
 */
@Entity
@Table(name="HOR_ASIGNATURAS_AREA")
public class AsignaturaArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="GESTIONA_ACTA")
	private BigDecimal gestionaActa;

	private BigDecimal porcentaje;

	//bi-directional many-to-one association to Area
    @ManyToOne
	@JoinColumn(name="AREA_ID")
	private Area horArea;

	//bi-directional many-to-one association to Asignatura
    @ManyToOne
	@JoinColumn(name="ASIGNATURA_ID")
	private Asignatura horAsignatura;

    public AsignaturaArea() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getGestionaActa() {
		return this.gestionaActa;
	}

	public void setGestionaActa(BigDecimal gestionaActa) {
		this.gestionaActa = gestionaActa;
	}

	public BigDecimal getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Area getHorArea() {
		return this.horArea;
	}

	public void setHorArea(Area horArea) {
		this.horArea = horArea;
	}
	
	public Asignatura getHorAsignatura() {
		return this.horAsignatura;
	}

	public void setHorAsignatura(Asignatura horAsignatura) {
		this.horAsignatura = horAsignatura;
	}
	
}