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

    public PermisoExtraDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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