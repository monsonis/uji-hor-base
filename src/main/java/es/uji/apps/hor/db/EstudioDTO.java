package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_ESTUDIOS database table.
 * 
 */
@Entity
@Table(name="HOR_ESTUDIOS")
public class EstudioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	private BigDecimal oficial;

	//bi-directional many-to-one association to AulaEstudioDTO
	@OneToMany(mappedBy="estudio")
	private Set<AulaEstudioDTO> aulasEstudios;

	//bi-directional many-to-one association to CircuitoDTO
	@OneToMany(mappedBy="estudio")
	private Set<CircuitoDTO> horCircuitos;

	//bi-directional many-to-one association to CentroDTO
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private CentroDTO centro;

	//bi-directional many-to-one association to TipoEstudioDTO
    @ManyToOne
	@JoinColumn(name="TIPO_ID")
	private TipoEstudioDTO horTiposEstudio;

	//bi-directional many-to-one association to CargoPersonaDTO
	@OneToMany(mappedBy="estudio")
	private Set<CargoPersonaDTO> horExtCargosPers;

	//bi-directional many-to-one association to ItemDTO
	@OneToMany(mappedBy="estudio")
	private Set<ItemDTO> horItems;

	//bi-directional many-to-one association to PermisoExtraDTO
	@OneToMany(mappedBy="horEstudio")
	private Set<PermisoExtraDTO> horPermisosExtras;

    public EstudioDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getOficial() {
		return this.oficial;
	}

	public void setOficial(BigDecimal oficial) {
		this.oficial = oficial;
	}

	public Set<AulaEstudioDTO> getAulasEstudios() {
		return this.aulasEstudios;
	}

	public void setAulasEstudios(Set<AulaEstudioDTO> aulasEstudios) {
		this.aulasEstudios = aulasEstudios;
	}
	
	public Set<CircuitoDTO> getHorCircuitos() {
		return this.horCircuitos;
	}

	public void setHorCircuitos(Set<CircuitoDTO> horCircuitos) {
		this.horCircuitos = horCircuitos;
	}
	
	public CentroDTO getCentro() {
		return this.centro;
	}

	public void setCentro(CentroDTO centro) {
		this.centro = centro;
	}
	
	public TipoEstudioDTO getHorTiposEstudio() {
		return this.horTiposEstudio;
	}

	public void setHorTiposEstudio(TipoEstudioDTO horTiposEstudio) {
		this.horTiposEstudio = horTiposEstudio;
	}
	
	public Set<CargoPersonaDTO> getHorExtCargosPers() {
		return this.horExtCargosPers;
	}

	public void setHorExtCargosPers(Set<CargoPersonaDTO> horExtCargosPers) {
		this.horExtCargosPers = horExtCargosPers;
	}
	
	public Set<ItemDTO> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<ItemDTO> horItems) {
		this.horItems = horItems;
	}
	
	public Set<PermisoExtraDTO> getHorPermisosExtras() {
		return this.horPermisosExtras;
	}

	public void setHorPermisosExtras(Set<PermisoExtraDTO> horPermisosExtras) {
		this.horPermisosExtras = horPermisosExtras;
	}
	
}