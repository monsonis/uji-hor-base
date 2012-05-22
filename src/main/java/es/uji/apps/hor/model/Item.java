package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the HOR_ITEMS database table.
 * 
 */
@Entity
@Table(name="HOR_ITEMS")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private BigDecimal comun;

    @Temporal( TemporalType.DATE)
	@Column(name="DESDE_EL_DIA")
	private Date desdeElDia;

    @Temporal( TemporalType.DATE)
	@Column(name="HASTA_EL_DIA")
	private Date hastaElDia;

    @Temporal( TemporalType.DATE)
	@Column(name="HORA_FIN")
	private Date horaFin;

    @Temporal( TemporalType.DATE)
	@Column(name="HORA_INICIO")
	private Date horaInicio;

	@Column(name="MODIFICA_DETALLE")
	private String modificaDetalle;

	@Column(name="SUBGRUPO_ID")
	private BigDecimal subgrupoId;

	//bi-directional many-to-one association to Asignatura
    @ManyToOne
	@JoinColumn(name="ASIGNATURA_ID")
	private Asignatura horAsignatura;

	//bi-directional many-to-one association to AulaPlanificacion
    @ManyToOne
	@JoinColumn(name="AULA_PLANIFICACION_ID")
	private AulaPlanificacion horAulasPlanificacion;

	//bi-directional many-to-one association to Caracter
    @ManyToOne
	@JoinColumn(name="CARACTER_ID")
	private Caracter horCaractere;

	//bi-directional many-to-one association to Circuito
    @ManyToOne
	@JoinColumn(name="CIRCUITO_ID")
	private Circuito horCircuito;

	//bi-directional many-to-one association to Curso
    @ManyToOne
	@JoinColumn(name="CURSO_ID")
	private Curso horCurso;

	//bi-directional many-to-one association to DiaSemana
    @ManyToOne
	@JoinColumn(name="DIA_SEMANA_ID")
	private DiaSemana horDiasSemana;

	//bi-directional many-to-one association to Estudio
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private Estudio horEstudio;

	//bi-directional many-to-one association to Grupo
    @ManyToOne
	@JoinColumn(name="GRUPO_ID")
	private Grupo horGrupo;

	//bi-directional many-to-one association to Profesor
    @ManyToOne
	@JoinColumn(name="PROFESOR_ID")
	private Profesor horProfesore;

	//bi-directional many-to-one association to Semestre
    @ManyToOne
	@JoinColumn(name="SEMESTRE_ID")
	private Semestre horSemestre;

	//bi-directional many-to-one association to TipoSubgrupo
    @ManyToOne
	@JoinColumn(name="TIPO_SUBGRUPO_ID")
	private TipoSubgrupo horTiposSubgrupo;

	//bi-directional many-to-one association to DetalleItem
	@OneToMany(mappedBy="horItem")
	private Set<DetalleItem> horItemsDetalles;

    public Item() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getComun() {
		return this.comun;
	}

	public void setComun(BigDecimal comun) {
		this.comun = comun;
	}

	public Date getDesdeElDia() {
		return this.desdeElDia;
	}

	public void setDesdeElDia(Date desdeElDia) {
		this.desdeElDia = desdeElDia;
	}

	public Date getHastaElDia() {
		return this.hastaElDia;
	}

	public void setHastaElDia(Date hastaElDia) {
		this.hastaElDia = hastaElDia;
	}

	public Date getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public Date getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getModificaDetalle() {
		return this.modificaDetalle;
	}

	public void setModificaDetalle(String modificaDetalle) {
		this.modificaDetalle = modificaDetalle;
	}

	public BigDecimal getSubgrupoId() {
		return this.subgrupoId;
	}

	public void setSubgrupoId(BigDecimal subgrupoId) {
		this.subgrupoId = subgrupoId;
	}

	public Asignatura getHorAsignatura() {
		return this.horAsignatura;
	}

	public void setHorAsignatura(Asignatura horAsignatura) {
		this.horAsignatura = horAsignatura;
	}
	
	public AulaPlanificacion getHorAulasPlanificacion() {
		return this.horAulasPlanificacion;
	}

	public void setHorAulasPlanificacion(AulaPlanificacion horAulasPlanificacion) {
		this.horAulasPlanificacion = horAulasPlanificacion;
	}
	
	public Caracter getHorCaractere() {
		return this.horCaractere;
	}

	public void setHorCaractere(Caracter horCaractere) {
		this.horCaractere = horCaractere;
	}
	
	public Circuito getHorCircuito() {
		return this.horCircuito;
	}

	public void setHorCircuito(Circuito horCircuito) {
		this.horCircuito = horCircuito;
	}
	
	public Curso getHorCurso() {
		return this.horCurso;
	}

	public void setHorCurso(Curso horCurso) {
		this.horCurso = horCurso;
	}
	
	public DiaSemana getHorDiasSemana() {
		return this.horDiasSemana;
	}

	public void setHorDiasSemana(DiaSemana horDiasSemana) {
		this.horDiasSemana = horDiasSemana;
	}
	
	public Estudio getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(Estudio horEstudio) {
		this.horEstudio = horEstudio;
	}
	
	public Grupo getHorGrupo() {
		return this.horGrupo;
	}

	public void setHorGrupo(Grupo horGrupo) {
		this.horGrupo = horGrupo;
	}
	
	public Profesor getHorProfesore() {
		return this.horProfesore;
	}

	public void setHorProfesore(Profesor horProfesore) {
		this.horProfesore = horProfesore;
	}
	
	public Semestre getHorSemestre() {
		return this.horSemestre;
	}

	public void setHorSemestre(Semestre horSemestre) {
		this.horSemestre = horSemestre;
	}
	
	public TipoSubgrupo getHorTiposSubgrupo() {
		return this.horTiposSubgrupo;
	}

	public void setHorTiposSubgrupo(TipoSubgrupo horTiposSubgrupo) {
		this.horTiposSubgrupo = horTiposSubgrupo;
	}
	
	public Set<DetalleItem> getHorItemsDetalles() {
		return this.horItemsDetalles;
	}

	public void setHorItemsDetalles(Set<DetalleItem> horItemsDetalles) {
		this.horItemsDetalles = horItemsDetalles;
	}
	
}