package es.uji.apps.hor.db;

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
public class ItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String asignatura;

	@Column(name="ASIGNATURA_ID")
	private String asignaturaId;

	private String caracter;

	@Column(name="CARACTER_ID")
	private String caracterId;

	private BigDecimal comun;

	@Column(name="CURSO_ID")
	private BigDecimal cursoId;

    @Temporal( TemporalType.DATE)
	@Column(name="DESDE_EL_DIA")
	private Date desdeElDia;

	private String estudio;

	@Column(name="GRUPO_ID")
	private String grupoId;

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

	@Column(name="PORCENTAJE_COMUN")
	private BigDecimal porcentajeComun;

	@Column(name="SUBGRUPO_ID")
	private BigDecimal subgrupoId;

	@Column(name="TIPO_ASIGNATURA")
	private String tipoAsignatura;

	@Column(name="TIPO_ASIGNATURA_ID")
	private String tipoAsignaturaId;

	@Column(name="TIPO_ESTUDIO")
	private String tipoEstudio;

	@Column(name="TIPO_ESTUDIO_ID")
	private String tipoEstudioId;

	@Column(name="TIPO_SUBGRUPO")
	private String tipoSubgrupo;

	@Column(name="TIPO_SUBGRUPO_ID")
	private String tipoSubgrupoId;

	//bi-directional many-to-one association to AulaPlanificacionDTO
    @ManyToOne
	@JoinColumn(name="AULA_PLANIFICACION_ID")
	private AulaPlanificacionDTO horAulasPlanificacion;

	//bi-directional many-to-one association to CircuitoDTO
    @ManyToOne
	@JoinColumn(name="CIRCUITO_ID")
	private CircuitoDTO horCircuito;

	//bi-directional many-to-one association to DiaSemanaDTO
    @ManyToOne
	@JoinColumn(name="DIA_SEMANA_ID")
	private DiaSemanaDTO horDiasSemana;

	//bi-directional many-to-one association to EstudioDTO
    @ManyToOne
	@JoinColumn(name="ESTUDIO_ID")
	private EstudioDTO horEstudio;

	//bi-directional many-to-one association to ProfesorDTO
    @ManyToOne
	@JoinColumn(name="PROFESOR_ID")
	private ProfesorDTO horProfesore;

	//bi-directional many-to-one association to SemestreDTO
    @ManyToOne
	@JoinColumn(name="SEMESTRE_ID")
	private SemestreDTO horSemestre;

	//bi-directional many-to-one association to ItemDetalleDTO
	@OneToMany(mappedBy="horItem")
	private Set<ItemDetalleDTO> horItemsDetalles;

    public ItemDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAsignatura() {
		return this.asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getAsignaturaId() {
		return this.asignaturaId;
	}

	public void setAsignaturaId(String asignaturaId) {
		this.asignaturaId = asignaturaId;
	}

	public String getCaracter() {
		return this.caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public String getCaracterId() {
		return this.caracterId;
	}

	public void setCaracterId(String caracterId) {
		this.caracterId = caracterId;
	}

	public BigDecimal getComun() {
		return this.comun;
	}

	public void setComun(BigDecimal comun) {
		this.comun = comun;
	}

	public BigDecimal getCursoId() {
		return this.cursoId;
	}

	public void setCursoId(BigDecimal cursoId) {
		this.cursoId = cursoId;
	}

	public Date getDesdeElDia() {
		return this.desdeElDia;
	}

	public void setDesdeElDia(Date desdeElDia) {
		this.desdeElDia = desdeElDia;
	}

	public String getEstudio() {
		return this.estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public String getGrupoId() {
		return this.grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
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

	public BigDecimal getPorcentajeComun() {
		return this.porcentajeComun;
	}

	public void setPorcentajeComun(BigDecimal porcentajeComun) {
		this.porcentajeComun = porcentajeComun;
	}

	public BigDecimal getSubgrupoId() {
		return this.subgrupoId;
	}

	public void setSubgrupoId(BigDecimal subgrupoId) {
		this.subgrupoId = subgrupoId;
	}

	public String getTipoAsignatura() {
		return this.tipoAsignatura;
	}

	public void setTipoAsignatura(String tipoAsignatura) {
		this.tipoAsignatura = tipoAsignatura;
	}

	public String getTipoAsignaturaId() {
		return this.tipoAsignaturaId;
	}

	public void setTipoAsignaturaId(String tipoAsignaturaId) {
		this.tipoAsignaturaId = tipoAsignaturaId;
	}

	public String getTipoEstudio() {
		return this.tipoEstudio;
	}

	public void setTipoEstudio(String tipoEstudio) {
		this.tipoEstudio = tipoEstudio;
	}

	public String getTipoEstudioId() {
		return this.tipoEstudioId;
	}

	public void setTipoEstudioId(String tipoEstudioId) {
		this.tipoEstudioId = tipoEstudioId;
	}

	public String getTipoSubgrupo() {
		return this.tipoSubgrupo;
	}

	public void setTipoSubgrupo(String tipoSubgrupo) {
		this.tipoSubgrupo = tipoSubgrupo;
	}

	public String getTipoSubgrupoId() {
		return this.tipoSubgrupoId;
	}

	public void setTipoSubgrupoId(String tipoSubgrupoId) {
		this.tipoSubgrupoId = tipoSubgrupoId;
	}

	public AulaPlanificacionDTO getHorAulasPlanificacion() {
		return this.horAulasPlanificacion;
	}

	public void setHorAulasPlanificacion(AulaPlanificacionDTO horAulasPlanificacion) {
		this.horAulasPlanificacion = horAulasPlanificacion;
	}
	
	public CircuitoDTO getHorCircuito() {
		return this.horCircuito;
	}

	public void setHorCircuito(CircuitoDTO horCircuito) {
		this.horCircuito = horCircuito;
	}
	
	public DiaSemanaDTO getHorDiasSemana() {
		return this.horDiasSemana;
	}

	public void setHorDiasSemana(DiaSemanaDTO horDiasSemana) {
		this.horDiasSemana = horDiasSemana;
	}
	
	public EstudioDTO getHorEstudio() {
		return this.horEstudio;
	}

	public void setHorEstudio(EstudioDTO horEstudio) {
		this.horEstudio = horEstudio;
	}
	
	public ProfesorDTO getHorProfesore() {
		return this.horProfesore;
	}

	public void setHorProfesore(ProfesorDTO horProfesore) {
		this.horProfesore = horProfesore;
	}
	
	public SemestreDTO getHorSemestre() {
		return this.horSemestre;
	}

	public void setHorSemestre(SemestreDTO horSemestre) {
		this.horSemestre = horSemestre;
	}
	
	public Set<ItemDetalleDTO> getHorItemsDetalles() {
		return this.horItemsDetalles;
	}

	public void setHorItemsDetalles(Set<ItemDetalleDTO> horItemsDetalles) {
		this.horItemsDetalles = horItemsDetalles;
	}
	
}