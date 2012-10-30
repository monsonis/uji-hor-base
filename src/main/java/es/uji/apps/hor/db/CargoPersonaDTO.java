package es.uji.apps.hor.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the HOR_EXT_CARGOS_PER database table.
 * 
 */
@Entity
@Table(name="HOR_EXT_CARGOS_PER")
public class CargoPersonaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="CURSO_ID")
	private Long cursoId;

	//bi-directional many-to-one association to DepartamentoDTO
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private DepartamentoDTO departamento;

	//bi-directional many-to-one association to EstudioDTO
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO estudio;

	//bi-directional many-to-one association to PersonaDTO
    @ManyToOne
	@JoinColumn(name="PERSONA_ID")
	private PersonaDTO persona;

	//bi-directional many-to-one association to TipoCargoDTO
    @ManyToOne
	@JoinColumn(name="TIPO_CARGO_ID")
	private TipoCargoDTO tiposCargo;

    public CargoPersonaDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCursoId() {
		return this.cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public DepartamentoDTO getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(DepartamentoDTO departamento) {
		this.departamento = departamento;
	}
	
	public EstudioDTO getEstudio() {
		return this.estudio;
	}

	public void setEstudio(EstudioDTO estudio) {
		this.estudio = estudio;
	}
	
	public PersonaDTO getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	
	public TipoCargoDTO getTiposCargo() {
		return this.tiposCargo;
	}

	public void setTiposCargo(TipoCargoDTO tiposCargo) {
		this.tiposCargo = tiposCargo;
	}
	
}