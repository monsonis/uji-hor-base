package es.uji.apps.hor.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the HOR_EXT_ASIGNATURAS_COMUNES database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HOR_EXT_ASIGNATURAS_COMUNES")
public class AsignaturaComunDTO implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ASIGNATURA_ID")
    private String asignaturaId;

    @Column(name = "GRUPO_COMUN_ID")
    private Long grupoComunId;

    private String nombre;

    public AsignaturaComunDTO()
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

    public String getAsignaturaId()
    {
        return this.asignaturaId;
    }

    public void setAsignaturaId(String asignaturaId)
    {
        this.asignaturaId = asignaturaId;
    }

    public Long getGrupoComunId()
    {
        return this.grupoComunId;
    }

    public void setGrupoComunId(Long grupoComunId)
    {
        this.grupoComunId = grupoComunId;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

}