package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the HOR_PERMISOS_EXTRA database table.
 * 
 */
@Entity
@Table(name="HOR_PERMISOS_EXTRA")
public class PermisoExtra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	//bi-directional many-to-one association to Departamento
    @ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private Departamento horDepartamento;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private Estudio horEstudio;

	//bi-directional many-to-one association to Persona
    @ManyToOne
	@JoinColumn(name="PERSONA_ID")
	private Persona horExtPersona;

	//bi-directional many-to-one association to TipoCargo
    @ManyToOne
	@JoinColumn(name="TIPO_CARGO_ID")
	private TipoCargo horTiposCargo;

    public PermisoExtra() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Departamento getHorDepartamento() {
		return this.horDepartamento;
	}

	public void setHorDepartamento(Departamento horDepartamento) {
		this.horDepartamento = horDepartamento;
	}
	
	public Estudio getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(Estudio horEstudio) {
		this.horEstudio = horEstudio;
	}
	
	public Persona getHorExtPersona() {
		return this.horExtPersona;
	}

	public void setHorExtPersona(Persona horExtPersona) {
		this.horExtPersona = horExtPersona;
	}
	
	public TipoCargo getHorTiposCargo() {
		return this.horTiposCargo;
	}

	public void setHorTiposCargo(TipoCargo horTiposCargo) {
		this.horTiposCargo = horTiposCargo;
	}
	
}