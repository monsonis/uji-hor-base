package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the HOR_TIPOS_CARGOS database table.
 * 
 */
@Entity
@Table(name = "HOR_TIPOS_CARGOS")
public class TipoCargoDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    // bi-directional many-to-one association to CargoPersonaDTO
    @OneToMany(mappedBy = "cargo")
    private Set<CargoPersonaDTO> cargosPersona;

    // bi-directional many-to-one association to PermisoExtraDTO
    @OneToMany(mappedBy = "tiposCargo")
    private Set<PermisoExtraDTO> permisosExtras;

    public TipoCargoDTO()
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

}