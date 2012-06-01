package es.uji.apps.hor.db;

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
public class AulaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String codigo;

	private String nombre;

	private BigDecimal plazas;

	private String tipo;

	//bi-directional many-to-one association to CentroDTO
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private CentroDTO horCentro;

	//bi-directional many-to-one association to AulaEstudioDTO
	@OneToMany(mappedBy="horAula")
	private Set<AulaEstudioDTO> horAulasEstudios;

	//bi-directional many-to-one association to AulaPlanificacionDTO
	@OneToMany(mappedBy="horAula")
	private Set<AulaPlanificacionDTO> horAulasPlanificacions;

    public AulaDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CentroDTO getHorCentro() {
		return this.horCentro;
	}

	public void setHorCentro(CentroDTO horCentro) {
		this.horCentro = horCentro;
	}
	
	public Set<AulaEstudioDTO> getHorAulasEstudios() {
		return this.horAulasEstudios;
	}

	public void setHorAulasEstudios(Set<AulaEstudioDTO> horAulasEstudios) {
		this.horAulasEstudios = horAulasEstudios;
	}
	
	public Set<AulaPlanificacionDTO> getHorAulasPlanificacions() {
		return this.horAulasPlanificacions;
	}

	public void setHorAulasPlanificacions(Set<AulaPlanificacionDTO> horAulasPlanificacions) {
		this.horAulasPlanificacions = horAulasPlanificacions;
	}
	
}