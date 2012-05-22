package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_ASIGNATURAS database table.
 * 
 */
@Entity
@Table(name="HOR_ASIGNATURAS")
public class Asignatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private BigDecimal comun;

	private String nombre;

	private BigDecimal porcentaje;

	//bi-directional many-to-one association to Caracter
    @ManyToOne
	@JoinColumn(name="CARACTER_ID")
	private Caracter horCaractere;

	//bi-directional many-to-one association to Curso
    @ManyToOne
	@JoinColumn(name="CURSO_ID")
	private Curso horCurso;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private Estudio horEstudio;

	//bi-directional many-to-one association to TipoAsignatura
    @ManyToOne
	@JoinColumn(name="TIPO_ASIGNATURA_ID")
	private TipoAsignatura horTiposAsignatura;

	//bi-directional many-to-one association to AsignaturaArea
	@OneToMany(mappedBy="horAsignatura")
	private Set<AsignaturaArea> horAsignaturasAreas;

	//bi-directional many-to-one association to GrupoAsignatura
	@OneToMany(mappedBy="horAsignatura")
	private Set<GrupoAsignatura> horGruposAsignaturas;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horAsignatura")
	private Set<Item> horItems;

    public Asignatura() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getComun() {
		return this.comun;
	}

	public void setComun(BigDecimal comun) {
		this.comun = comun;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Caracter getHorCaractere() {
		return this.horCaractere;
	}

	public void setHorCaractere(Caracter horCaractere) {
		this.horCaractere = horCaractere;
	}
	
	public Curso getHorCurso() {
		return this.horCurso;
	}

	public void setHorCurso(Curso horCurso) {
		this.horCurso = horCurso;
	}
	
	public Estudio getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(Estudio horEstudio) {
		this.horEstudio = horEstudio;
	}
	
	public TipoAsignatura getHorTiposAsignatura() {
		return this.horTiposAsignatura;
	}

	public void setHorTiposAsignatura(TipoAsignatura horTiposAsignatura) {
		this.horTiposAsignatura = horTiposAsignatura;
	}
	
	public Set<AsignaturaArea> getHorAsignaturasAreas() {
		return this.horAsignaturasAreas;
	}

	public void setHorAsignaturasAreas(Set<AsignaturaArea> horAsignaturasAreas) {
		this.horAsignaturasAreas = horAsignaturasAreas;
	}
	
	public Set<GrupoAsignatura> getHorGruposAsignaturas() {
		return this.horGruposAsignaturas;
	}

	public void setHorGruposAsignaturas(Set<GrupoAsignatura> horGruposAsignaturas) {
		this.horGruposAsignaturas = horGruposAsignaturas;
	}
	
	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
}