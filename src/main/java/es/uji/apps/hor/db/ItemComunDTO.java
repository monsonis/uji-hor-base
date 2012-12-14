package es.uji.apps.hor.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the HOR_V_ITEMS_COMUNES database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HOR_ITEMS_COMUNES")
public class ItemComunDTO implements Serializable
{
    @Column(name = "ASIGNATURA_COMUN_ID")
    private String asignaturaComunId;

    @Column(name = "ASIGNATURA_ID")
    private String asignaturaId;

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private ItemDTO item;

    @ManyToOne
    @JoinColumn(name = "ITEM_COMUN_ID")
    private ItemDTO itemComun;

    public ItemComunDTO()
    {
    }

    public String getAsignaturaComunId()
    {
        return this.asignaturaComunId;
    }

    public void setAsignaturaComunId(String asignaturaComunId)
    {
        this.asignaturaComunId = asignaturaComunId;
    }

    public String getAsignaturaId()
    {
        return this.asignaturaId;
    }

    public void setAsignaturaId(String asignaturaId)
    {
        this.asignaturaId = asignaturaId;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ItemDTO getItem()
    {
        return item;
    }

    public void setItem(ItemDTO item)
    {
        this.item = item;
    }

    public ItemDTO getItemComun()
    {
        return itemComun;
    }

    public void setItemComun(ItemDTO itemComun)
    {
        this.itemComun = itemComun;
    }

}