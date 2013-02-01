package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the HOR_DEPARTAMENTOS database table.
 * 
 */
@Entity
@Table(name = "HOR_DEPARTAMENTOS")
public class DepartamentoDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long activo;

    private String nombre;

    // bi-directional many-to-one association to CentroDTO
    @ManyToOne
    @JoinColumn(name = "CENTRO_ID")
    private CentroDTO centro;

    // bi-directional many-to-one association to PersonaDTO
//    @OneToMany(mappedBy = "departamento")
//    private Set<PersonaDTO> personas;

    // bi-directional many-to-one association to PermisoExtraDTO
    @OneToMany(mappedBy = "departamento")
    private Set<PermisoExtraDTO> permisosExtras;

    public DepartamentoDTO()
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

    public Long getActivo()
    {
        return this.activo;
    }

    public void setActivo(Long activo)
    {
        this.activo = activo;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public CentroDTO getCentro()
    {
        return this.centro;
    }

    public void setCentro(CentroDTO centro)
    {
        this.centro = centro;
    }
//
//    public Set<PersonaDTO> getPersonas()
//    {
//        return this.personas;
//    }
//
//    public void setPersonas(Set<PersonaDTO> personas)
//    {
//        this.personas = personas;
//    }

    public Set<PermisoExtraDTO> getPermisosExtras()
    {
        return this.permisosExtras;
    }

    public void setPermisosExtras(Set<PermisoExtraDTO> permisosExtras)
    {
        this.permisosExtras = permisosExtras;
    }

}