package es.uji.apps.hor.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the HOR_ITEMS_ASIGNATURAS database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HOR_ITEMS_ASIGNATURAS")
public class ItemsAsignaturaDTO implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String asignatura;

    @ManyToOne
    @JoinColumn(name = "ASIGNATURA_ID")
    private ItemsAsignaturaDTO asignaturaDTO;

    private String estudio;

    @Column(name = "ESTUDIO_ID")
    private Long estudioId;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private ItemDTO item;

    public ItemsAsignaturaDTO()
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

    public String getNombreAsignatura()
    {
        return this.asignatura;
    }

    public void setNombreAsignatura(String asignatura)
    {
        this.asignatura = asignatura;
    }

    public ItemsAsignaturaDTO getAsignatura()
    {
        return this.asignaturaDTO;
    }

    public void setAsignatura(ItemsAsignaturaDTO asignaturaId)
    {
        this.asignaturaDTO = asignaturaId;
    }

    public String getEstudio()
    {
        return this.estudio;
    }

    public void setEstudio(String estudio)
    {
        this.estudio = estudio;
    }

    public Long getEstudioId()
    {
        return this.estudioId;
    }

    public void setEstudioId(Long estudioId)
    {
        this.estudioId = estudioId;
    }

    public ItemDTO getItem()
    {
        return this.item;
    }

    public void setItem(ItemDTO item)
    {
        this.item = item;
    }

}