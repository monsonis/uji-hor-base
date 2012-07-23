package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the HOR_ITEMS database table.
 * 
 */
@Entity
@Table(name = "HOR_ITEMS")
public class ItemDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String asignatura;

    @Column(name = "ASIGNATURA_ID")
    private String asignaturaId;

    private String caracter;

    @Column(name = "CARACTER_ID")
    private String caracterId;

    private BigDecimal comun;

    @Column(name = "CURSO_ID")
    private BigDecimal cursoId;

    @Temporal(TemporalType.DATE)
    @Column(name = "DESDE_EL_DIA")
    private Date desdeElDia;

    @Column(name = "ESTUDIO")
    private String estudioDesc;

    @Column(name = "GRUPO_ID")
    private String grupoId;

    @Temporal(TemporalType.DATE)
    @Column(name = "HASTA_EL_DIA")
    private Date hastaElDia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "HORA_FIN")
    private Date horaFin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "HORA_INICIO")
    private Date horaInicio;

    @Column(name = "MODIFICA_DETALLE")
    private String modificaDetalle;

    private BigDecimal plazas;

    @Column(name = "PORCENTAJE_COMUN")
    private BigDecimal porcentajeComun;

    @Column(name = "SUBGRUPO_ID")
    private BigDecimal subgrupoId;

    @Column(name = "TIPO_ASIGNATURA")
    private String tipoAsignatura;

    @Column(name = "TIPO_ASIGNATURA_ID")
    private String tipoAsignaturaId;

    @Column(name = "TIPO_ESTUDIO")
    private String tipoEstudio;

    @Column(name = "TIPO_ESTUDIO_ID")
    private String tipoEstudioId;

    @Column(name = "TIPO_SUBGRUPO")
    private String tipoSubgrupo;

    @Column(name = "TIPO_SUBGRUPO_ID")
    private String tipoSubgrupoId;

    // bi-directional many-to-one association to AulaPlanificacionDTO
    @ManyToOne
    @JoinColumn(name = "AULA_PLANIFICACION_ID")
    private AulaPlanificacionDTO aulasPlanificacion;

    // bi-directional many-to-one association to DiaSemanaDTO
    @ManyToOne
    @JoinColumn(name = "DIA_SEMANA_ID")
    private DiaSemanaDTO diaSemana;

    // bi-directional many-to-one association to EstudioDTO
    @ManyToOne
    @JoinColumn(name = "ESTUDIO_ID")
    private EstudioDTO estudio;

    // bi-directional many-to-one association to ProfesorDTO
    @ManyToOne
    @JoinColumn(name = "PROFESOR_ID")
    private ProfesorDTO profesor;

    // bi-directional many-to-one association to SemestreDTO
    @ManyToOne
    @JoinColumn(name = "SEMESTRE_ID")
    private SemestreDTO semestre;

    // bi-directional many-to-one association to HorItemsCircuito
    @OneToMany(mappedBy = "horItem")
    private Set<ItemCircuitoDTO> itemsCircuitos;

    // bi-directional many-to-one association to ItemDetalleDTO
    @OneToMany(mappedBy = "horItem")
    private Set<ItemDetalleDTO> itemsDetalles;

    public ItemDTO()
    {
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAsignatura()
    {
        return this.asignatura;
    }

    public void setAsignatura(String asignatura)
    {
        this.asignatura = asignatura;
    }

    public String getAsignaturaId()
    {
        return this.asignaturaId;
    }

    public void setAsignaturaId(String asignaturaId)
    {
        this.asignaturaId = asignaturaId;
    }

    public String getCaracter()
    {
        return this.caracter;
    }

    public void setCaracter(String caracter)
    {
        this.caracter = caracter;
    }

    public String getCaracterId()
    {
        return this.caracterId;
    }

    public void setCaracterId(String caracterId)
    {
        this.caracterId = caracterId;
    }

    public BigDecimal getComun()
    {
        return this.comun;
    }

    public void setComun(BigDecimal comun)
    {
        this.comun = comun;
    }

    public BigDecimal getCursoId()
    {
        return this.cursoId;
    }

    public void setCursoId(BigDecimal cursoId)
    {
        this.cursoId = cursoId;
    }

    public Date getDesdeElDia()
    {
        return this.desdeElDia;
    }

    public void setDesdeElDia(Date desdeElDia)
    {
        this.desdeElDia = desdeElDia;
    }

    public String getEstudioDesc()
    {
        return this.estudioDesc;
    }

    public void setEstudioDesc(String estudioDesc)
    {
        this.estudioDesc = estudioDesc;
    }

    public String getGrupoId()
    {
        return this.grupoId;
    }

    public void setGrupoId(String grupoId)
    {
        this.grupoId = grupoId;
    }

    public Date getHastaElDia()
    {
        return this.hastaElDia;
    }

    public void setHastaElDia(Date hastaElDia)
    {
        this.hastaElDia = hastaElDia;
    }

    public Date getHoraFin()
    {
        return this.horaFin;
    }

    public void setHoraFin(Date horaFin)
    {
        this.horaFin = horaFin;
    }

    public Date getHoraInicio()
    {
        return this.horaInicio;
    }

    public void setHoraInicio(Date horaInicio)
    {
        this.horaInicio = horaInicio;
    }

    public String getModificaDetalle()
    {
        return this.modificaDetalle;
    }

    public void setModificaDetalle(String modificaDetalle)
    {
        this.modificaDetalle = modificaDetalle;
    }

    public BigDecimal getPlazas()
    {
        return this.plazas;
    }

    public void setPlazas(BigDecimal plazas)
    {
        this.plazas = plazas;
    }

    public BigDecimal getPorcentajeComun()
    {
        return this.porcentajeComun;
    }

    public void setPorcentajeComun(BigDecimal porcentajeComun)
    {
        this.porcentajeComun = porcentajeComun;
    }

    public BigDecimal getSubgrupoId()
    {
        return this.subgrupoId;
    }

    public void setSubgrupoId(BigDecimal subgrupoId)
    {
        this.subgrupoId = subgrupoId;
    }

    public String getTipoAsignatura()
    {
        return this.tipoAsignatura;
    }

    public void setTipoAsignatura(String tipoAsignatura)
    {
        this.tipoAsignatura = tipoAsignatura;
    }

    public String getTipoAsignaturaId()
    {
        return this.tipoAsignaturaId;
    }

    public void setTipoAsignaturaId(String tipoAsignaturaId)
    {
        this.tipoAsignaturaId = tipoAsignaturaId;
    }

    public String getTipoEstudio()
    {
        return this.tipoEstudio;
    }

    public void setTipoEstudio(String tipoEstudio)
    {
        this.tipoEstudio = tipoEstudio;
    }

    public String getTipoEstudioId()
    {
        return this.tipoEstudioId;
    }

    public void setTipoEstudioId(String tipoEstudioId)
    {
        this.tipoEstudioId = tipoEstudioId;
    }

    public String getTipoSubgrupo()
    {
        return this.tipoSubgrupo;
    }

    public void setTipoSubgrupo(String tipoSubgrupo)
    {
        this.tipoSubgrupo = tipoSubgrupo;
    }

    public String getTipoSubgrupoId()
    {
        return this.tipoSubgrupoId;
    }

    public void setTipoSubgrupoId(String tipoSubgrupoId)
    {
        this.tipoSubgrupoId = tipoSubgrupoId;
    }

    public AulaPlanificacionDTO getAulasPlanificacion()
    {
        return this.aulasPlanificacion;
    }

    public void setAulasPlanificacion(AulaPlanificacionDTO aulasPlanificacion)
    {
        this.aulasPlanificacion = aulasPlanificacion;
    }

    public DiaSemanaDTO getDiaSemana()
    {
        return this.diaSemana;
    }

    public void setDiaSemana(DiaSemanaDTO diaSemana)
    {
        this.diaSemana = diaSemana;
    }

    public EstudioDTO getEstudio()
    {
        return this.estudio;
    }

    public void setEstudio(EstudioDTO estudio)
    {
        this.estudio = estudio;
    }

    public ProfesorDTO getProfesor()
    {
        return this.profesor;
    }

    public void setProfesor(ProfesorDTO profesor)
    {
        this.profesor = profesor;
    }

    public SemestreDTO getSemestre()
    {
        return this.semestre;
    }

    public void setSemestre(SemestreDTO semestre)
    {
        this.semestre = semestre;
    }

    public Set<ItemCircuitoDTO> getItemsCircuitos()
    {
        return this.itemsCircuitos;
    }

    public void setItemsCircuitos(Set<ItemCircuitoDTO> itemsCircuitos)
    {
        this.itemsCircuitos = itemsCircuitos;
    }

    public Set<ItemDetalleDTO> getItemsDetalles()
    {
        return this.itemsDetalles;
    }

    public void setItemsDetalles(Set<ItemDetalleDTO> itemsDetalles)
    {
        this.itemsDetalles = itemsDetalles;
    }

    @Override
    public String toString()
    {
        return MessageFormat.format("{0} - {1}{2}", asignaturaId, tipoSubgrupoId, subgrupoId);
    }
}