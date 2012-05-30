package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the HOR_PERMISOS_EXTRA database table.
 * 
 */
@Entity
@Table(name="HOR_PERMISOS_EXTRA")
public class PermisoExtraDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	//bi-directional many-to-one association to Departamento
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private DepartamentoDTO horDepartamento;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO horEstudio;

	//bi-directional many-to-one association to Persona
    @ManyToOne
	@JoinColumn(name="PERSONA_ID")
	private PersonaDTO horExtPersona;

	//bi-directional many-to-one association to TipoCargo
    @ManyToOne
	@JoinColumn(name="TIPO_CARGO_ID")
	private TipoCargoDTO horTiposCargo;

    public PermisoExtraDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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