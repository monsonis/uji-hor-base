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
public class EstudioDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private Long oficial;

    // bi-directional many-to-one association to CircuitoDTO
    @OneToMany(mappedBy = "estudio")
    private Set<CircuitoDTO> circuitos;

    // bi-directional many-to-one association to CentroDTO
    @ManyToOne
    @JoinColumn(name = "CENTRO_ID")
    private CentroDTO centro;

    // bi-directional many-to-one association to TipoEstudioDTO
    @ManyToOne
    @JoinColumn(name = "TIPO_ID")
    private TipoEstudioDTO tiposEstudio;

    // bi-directional many-to-one association to CargoPersonaDTO
    @OneToMany(mappedBy = "estudio")
    private Set<CargoPersonaDTO> cargosPersona;

    // bi-directional many-to-one association to ItemDTO
    @OneToMany(mappedBy = "estudio")
    private Set<ItemDTO> items;

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

    public Set<CircuitoDTO> getCircuitos()
    {
        return this.circuitos;
    }

    public void setCircuitos(Set<CircuitoDTO> circuitos)
    {
        this.circuitos = circuitos;
    }

    public CentroDTO getCentro()
    {
        return this.centro;
    }

    public void setCentro(CentroDTO centro)
    {
        this.centro = centro;
    }

    public TipoEstudioDTO getTiposEstudio()
    {
        return this.tiposEstudio;
    }

    public void setTiposEstudio(TipoEstudioDTO tiposEstudio)
    {
        this.tiposEstudio = tiposEstudio;
    }

    public Set<CargoPersonaDTO> getCargosPersona()
    {
        return this.cargosPersona;
    }

    public void setCargosPersona(Set<CargoPersonaDTO> cargosPersona)
    {
        this.cargosPersona = cargosPersona;
    }

    public Set<ItemDTO> getItems()
    {
        return this.items;
    }

    public void setItems(Set<ItemDTO> items)
    {
        this.items = items;
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

}