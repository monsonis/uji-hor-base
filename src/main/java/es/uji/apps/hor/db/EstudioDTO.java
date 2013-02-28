package es.uji.apps.hor.db;

import java.io.Serializable;
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

/**
 * The persistent class for the HOR_ESTUDIOS database table.
 * 
 */
@Entity
@Table(name = "HOR_ESTUDIOS")
@SuppressWarnings("serial")
public class EstudioDTO implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private Long oficial;

    // bi-directional many-to-one association to CircuitoEstudioDTO
    @OneToMany(mappedBy = "circuito")
    private Set<CircuitoEstudioDTO> circuitosEstudio;

    // bi-directional many-to-one association to EstudiosCompartidosDTO
    @OneToMany(mappedBy = "estudioCompartido")
    private Set<EstudiosCompartidosDTO> estudiosCompartidos;

    // bi-directional many-to-one association to CentroDTO
    @ManyToOne
    @JoinColumn(name = "CENTRO_ID")
    private CentroDTO centro;

    // bi-directional many-to-one association to TipoEstudioDTO
    @ManyToOne
    @JoinColumn(name = "TIPO_ID")
    private TipoEstudioDTO tipoEstudio;

    // bi-directional many-to-one association to CargoPersonaDTO
    @OneToMany(mappedBy = "estudio")
    private Set<CargoPersonaDTO> cargosPersona;

    // bi-directional many-to-one association to PermisoExtraDTO
    @OneToMany(mappedBy = "estudio")
    private Set<PermisoExtraDTO> permisosExtras;

    @Column(name = "NUMERO_CURSOS")
    private Long numeroCursos;

    public EstudioDTO()
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

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Long getOficial()
    {
        return this.oficial;
    }

    public void setOficial(Long oficial)
    {
        this.oficial = oficial;
    }

    public CentroDTO getCentro()
    {
        return this.centro;
    }

    public void setCentro(CentroDTO centro)
    {
        this.centro = centro;
    }

    public TipoEstudioDTO getTipoEstudio()
    {
        return this.tipoEstudio;
    }

    public void setTipoEstudio(TipoEstudioDTO tipoEstudio)
    {
        this.tipoEstudio = tipoEstudio;
    }

    public Set<CargoPersonaDTO> getCargosPersona()
    {
        return this.cargosPersona;
    }

    public void setCargosPersona(Set<CargoPersonaDTO> cargosPersona)
    {
        this.cargosPersona = cargosPersona;
    }

    public Set<PermisoExtraDTO> getPermisosExtras()
    {
        return this.permisosExtras;
    }

    public void setPermisosExtras(Set<PermisoExtraDTO> permisosExtras)
    {
        this.permisosExtras = permisosExtras;
    }

    public Long getNumeroCursos()
    {
        return numeroCursos;
    }

    public void setNumeroCursos(Long numeroCursos)
    {
        this.numeroCursos = numeroCursos;
    }

    public Set<EstudiosCompartidosDTO> getEstudiosCompartidos()
    {
        return estudiosCompartidos;
    }

    public void setEstudiosCompartidos(Set<EstudiosCompartidosDTO> estudiosCompartidos)
    {
        this.estudiosCompartidos = estudiosCompartidos;
    }

    public Set<CircuitoEstudioDTO> getCircuitosEstudio()
    {
        return circuitosEstudio;
    }

    public void setCircuitosEstudio(Set<CircuitoEstudioDTO> circuitosEstudio)
    {
        this.circuitosEstudio = circuitosEstudio;
    }

}