package es.uji.apps.hor.model;

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
public class Estudio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String nombre;

	private BigDecimal oficial;

	//bi-directional many-to-one association to Asignatura
	@OneToMany(mappedBy="horEstudio")
	private Set<Asignatura> horAsignaturas;

	//bi-directional many-to-one association to AulaEstudio
	@OneToMany(mappedBy="horEstudio")
	private Set<AulaEstudio> horAulasEstudios;

	//bi-directional many-to-one association to Circuito
	@OneToMany(mappedBy="horEstudio")
	private Set<Circuito> horCircuitos;

	//bi-directional many-to-one association to EstudioCurso
	@OneToMany(mappedBy="horEstudio")
	private Set<EstudioCurso> horCursosEstudios;

	//bi-directional many-to-one association to Centro
    @ManyToOne
	@JoinColumn(name="CENTRO_ID")
	private Centro horCentro;

	//bi-directional many-to-one association to TipoEstudio
    @ManyToOne
	@JoinColumn(name="TIPO_ID")
	private TipoEstudio horTiposEstudio;

	//bi-directional many-to-one association to CargoPersona
	@OneToMany(mappedBy="horEstudio")
	private Set<CargoPersona> horExtCargosPers;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horEstudio")
	private Set<Item> horItems;

	//bi-directional many-to-one association to PermisoExtra
	@OneToMany(mappedBy="horEstudio")
	private Set<PermisoExtra> horPermisosExtras;

    public Estudio() {
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

	public Set<Asignatura> getHorAsignaturas() {
		return this.horAsignaturas;
	}

	public void setHorAsignaturas(Set<Asignatura> horAsignaturas) {
		this.horAsignaturas = horAsignaturas;
	}
	
	public Set<AulaEstudio> getHorAulasEstudios() {
		return this.horAulasEstudios;
	}

	public void setHorAulasEstudios(Set<AulaEstudio> horAulasEstudios) {
		this.horAulasEstudios = horAulasEstudios;
	}
	
	public Set<Circuito> getHorCircuitos() {
		return this.horCircuitos;
	}

	public void setHorCircuitos(Set<Circuito> horCircuitos) {
		this.horCircuitos = horCircuitos;
	}
	
	public Set<EstudioCurso> getHorCursosEstudios() {
		return this.horCursosEstudios;
	}

	public void setHorCursosEstudios(Set<EstudioCurso> horCursosEstudios) {
		this.horCursosEstudios = horCursosEstudios;
	}
	
	public Centro getHorCentro() {
		return this.horCentro;
	}

	public void setHorCentro(Centro horCentro) {
		this.horCentro = horCentro;
	}
	
	public TipoEstudio getHorTiposEstudio() {
		return this.horTiposEstudio;
	}

	public void setHorTiposEstudio(TipoEstudio horTiposEstudio) {
		this.horTiposEstudio = horTiposEstudio;
	}
	
	public Set<CargoPersona> getHorExtCargosPers() {
		return this.horExtCargosPers;
	}

	public void setHorExtCargosPers(Set<CargoPersona> horExtCargosPers) {
		this.horExtCargosPers = horExtCargosPers;
	}
	
	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
	public Set<PermisoExtra> getHorPermisosExtras() {
		return this.horPermisosExtras;
	}

	public void setHorPermisosExtras(Set<PermisoExtra> horPermisosExtras) {
		this.horPermisosExtras = horPermisosExtras;
	}
	
}