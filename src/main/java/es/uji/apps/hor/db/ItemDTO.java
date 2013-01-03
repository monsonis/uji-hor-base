package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the HOR_ITEMS database table.
 * 
 */
@Entity
@Table(name = "HOR_ITEMS")
@SuppressWarnings("serial")
public class ItemDTO implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String asignatura;

    @Column(name = "ASIGNATURA_ID")
    private String asignaturaId;

    private String caracter;

    @Column(name = "CARACTER_ID")
    private String caracterId;

    private Long comun;

    @Column(name = "CURSO_ID")
    private Long cursoId;

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

    private Long plazas;

    @Column(name = "PORCENTAJE_COMUN")
    private Long porcentajeComun;

    @Column(name = "SUBGRUPO_ID")
    private Long subgrupoId;

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
    private AulaPlanificacionDTO aulaPlanificacion;

    @Column(name = "AULA_PLANIFICACION_NOMBRE")
    private String aulaPlanificacionNombre;

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

    // bi-directional many-to-one association to ItemCircuitoDTO
    @OneToMany(mappedBy = "item")
    private Set<ItemCircuitoDTO> itemsCircuitos;

    // bi-directional many-to-one association to ItemDetalleDTO
    @OneToMany(mappedBy = "item")
    private Set<ItemDetalleDTO> itemsDetalles;

    @Column(name = "DETALLE_MANUAL")
    private Boolean detalleManual;

    @Column(name = "NUMERO_ITERACIONES")
    private Integer numeroIteraciones;

    @Column(name = "REPETIR_CADA_SEMANAS")
    private Integer repetirCadaSemanas;

    @Column(name = "COMUN_TEXTO")
    private String comunes;

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

    public Long getComun()
    {
        return this.comun;
    }

    public void setComun(Long comun)
    {
        this.comun = comun;
    }

    public Long getCursoId()
    {
        return this.cursoId;
    }

    public void setCursoId(Long cursoId)
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

    public Long getPlazas()
    {
        return this.plazas;
    }

    public void setPlazas(Long plazas)
    {
        this.plazas = plazas;
    }

    public Long getPorcentajeComun()
    {
        return this.porcentajeComun;
    }

    public void setPorcentajeComun(Long porcentajeComun)
    {
        this.porcentajeComun = porcentajeComun;
    }

    public Long getSubgrupoId()
    {
        return this.subgrupoId;
    }

    public void setSubgrupoId(Long subgrupoId)
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

    public AulaPlanificacionDTO getAulaPlanificacion()
    {
        return this.aulaPlanificacion;
    }

    public void setAulaPlanificacion(AulaPlanificacionDTO aulaPlanificacion)
    {
        this.aulaPlanificacion = aulaPlanificacion;
    }

    public String getAulaPlanificacionNombre()
    {
        return aulaPlanificacionNombre;
    }

    public void setAulaPlanificacionNombre(String aulaPlanificacionNombre)
    {
        this.aulaPlanificacionNombre = aulaPlanificacionNombre;
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

    public Boolean getDetalleManual()
    {
        return detalleManual;
    }

    public void setDetalleManual(Boolean detalleManual)
    {
        this.detalleManual = detalleManual;
    }

    public Integer getNumeroIteraciones()
    {
        return numeroIteraciones;
    }

    public void setNumeroIteraciones(Integer numeroIteraciones)
    {
        this.numeroIteraciones = numeroIteraciones;
    }

    public Integer getRepetirCadaSemanas()
    {
        return repetirCadaSemanas;
    }

    public void setRepetirCadaSemanas(Integer repetirCadaSemanas)
    {
        this.repetirCadaSemanas = repetirCadaSemanas;
    }

    public String getComunes()
    {
        return comunes;
    }

    public void setComunes(String comunes)
    {
        this.comunes = comunes;
    }
}