package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_AULAS database table.
 * 
 */
@Entity
@Table(name="HOR_AULAS")
public class Aula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long Aula;

	private String codigo;

	private String nombre;

	private BigDecimal plazas;

	private String tipo;

	//bi-directional many-to-one association to Centro
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private Centro horCentro;

	//bi-directional many-to-one association to AulaEstudio
	@OneToMany(mappedBy="horAula")
	private Set<AulaEstudio> horAulasEstudios;

	//bi-directional many-to-one association to AulaPlanificacion
	@OneToMany(mappedBy="horAula")
	private Set<AulaPlanificacion> horAulasPlanificacions;

    public Aula() {
    }

	public Long getAula() {
		return this.Aula;
	}

	public void setAula(Long Aula) {
		this.Aula = Aula;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPlazas() {
		return this.plazas;
	}

	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Centro getHorCentro() {
		return this.horCentro;
	}

	public void setHorCentro(Centro horCentro) {
		this.horCentro = horCentro;
	}
	
	public Set<AulaEstudio> getHorAulasEstudios() {
		return this.horAulasEstudios;
	}

	public void setHorAulasEstudios(Set<AulaEstudio> horAulasEstudios) {
		this.horAulasEstudios = horAulasEstudios;
	}
	
	public Set<AulaPlanificacion> getHorAulasPlanificacions() {
		return this.horAulasPlanificacions;
	}

	public void setHorAulasPlanificacions(Set<AulaPlanificacion> horAulasPlanificacions) {
		this.horAulasPlanificacions = horAulasPlanificacions;
	}
	
}