package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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

	private Long plazas;

	private String tipo;

	//bi-directional many-to-one association to CentroDTO
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private CentroDTO centro;

	//bi-directional many-to-one association to AulaEstudioDTO
	@OneToMany(mappedBy="aula")
	private Set<AulaEstudioDTO> aulasEstudios;

	//bi-directional many-to-one association to AulaPlanificacionDTO
	@OneToMany(mappedBy="aula")
	private Set<AulaPlanificacionDTO> aulasPlanificacions;

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

	public Long getPlazas() {
		return this.plazas;
	}

	public void setPlazas(Long plazas) {
		this.plazas = plazas;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public CentroDTO getCentro() {
		return this.centro;
	}

	public void setCentro(CentroDTO centro) {
		this.centro = centro;
	}
	
	public Set<AulaEstudioDTO> getAulasEstudios() {
		return this.aulasEstudios;
	}

	public void setAulasEstudios(Set<AulaEstudioDTO> aulasEstudios) {
		this.aulasEstudios = aulasEstudios;
	}
	
	public Set<AulaPlanificacionDTO> getAulasPlanificacions() {
		return this.aulasPlanificacions;
	}

	public void setAulasPlanificacions(Set<AulaPlanificacionDTO> aulasPlanificacions) {
		this.aulasPlanificacions = aulasPlanificacions;
	}
	
}