package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the HOR_TIPOS_ESTUDIOS database table.
 * 
 */
@Entity
@Table(name = "HOR_TIPOS_ESTUDIOS")
public class TipoEstudioDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    // @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private String id;

    private String nombre;

    private Long orden;

    // bi-directional many-to-one association to EstudioDTO
    @OneToMany(mappedBy = "tiposEstudio")
    private Set<EstudioDTO> estudios;

    // bi-directional many-to-one association to DetalleSemestreDTO
    @OneToMany(mappedBy = "tiposEstudio")
    private Set<DetalleSemestreDTO> detalleSemestres;

    public TipoEstudioDTO()
    {
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
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

    public Long getOrden()
    {
        return this.orden;
    }

    public void setOrden(Long orden)
    {
        this.orden = orden;
    }

    public Set<EstudioDTO> getEstudios()
    {
        return this.estudios;
    }

    public void setEstudios(Set<EstudioDTO> estudios)
    {
        this.estudios = estudios;
    }

    public Set<DetalleSemestreDTO> getDetalleSemestres()
    {
        return this.detalleSemestres;
    }

    public void setDetalleSemestres(Set<DetalleSemestreDTO> detalleSemestres)
    {
        this.detalleSemestres = detalleSemestres;
    }

}