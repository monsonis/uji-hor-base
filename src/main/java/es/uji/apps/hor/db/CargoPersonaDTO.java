package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


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
	private BigDecimal cursoId;

	//bi-directional many-to-one association to DepartamentoDTO
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private DepartamentoDTO horDepartamento;

	//bi-directional many-to-one association to EstudioDTO
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO horEstudio;

	//bi-directional many-to-one association to PersonaDTO
    @ManyToOne
	@JoinColumn(name="PERSONA_ID")
	private PersonaDTO horExtPersona;

	//bi-directional many-to-one association to TipoCargoDTO
    @ManyToOne
	@JoinColumn(name="TIPO_CARGO_ID")
	private TipoCargoDTO horTiposCargo;

    public CargoPersonaDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCursoId() {
		return this.cursoId;
	}

	public void setCursoId(BigDecimal cursoId) {
		this.cursoId = cursoId;
	}

	public DepartamentoDTO getHorDepartamento() {
		return this.horDepartamento;
	}

	public void setHorDepartamento(DepartamentoDTO horDepartamento) {
		this.horDepartamento = horDepartamento;
	}
	
	public EstudioDTO getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(EstudioDTO horEstudio) {
		this.horEstudio = horEstudio;
	}
	
	public PersonaDTO getHorExtPersona() {
		return this.horExtPersona;
	}

	public void setHorExtPersona(PersonaDTO horExtPersona) {
		this.horExtPersona = horExtPersona;
	}
	
	public TipoCargoDTO getHorTiposCargo() {
		return this.horTiposCargo;
	}

	public void setHorTiposCargo(TipoCargoDTO horTiposCargo) {
		this.horTiposCargo = horTiposCargo;
	}
	
}