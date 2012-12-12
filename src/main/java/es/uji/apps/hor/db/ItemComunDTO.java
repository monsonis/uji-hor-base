package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the HOR_V_ITEMS_COMUNES database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@IdClass(ItemComunDTOId.class)
@Table(name = "HOR_V_ITEMS_COMUNES")
public class ItemComunDTO implements Serializable
{
    @Column(name = "ASIGNATURA_COMUN_ID")
    private String asignaturaComunId;

    @Column(name = "ASIGNATURA_ID")
    private String asignaturaId;

    @Id
    private Long id;

    @Id
    @Column(name = "ITEM_COMUN_ID")
    private Long itemComunId;

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

    public Long getItemComunId()
    {
        return this.itemComunId;
    }

    public void setItemComunId(Long itemComunId)
    {
        this.itemComunId = itemComunId;
    }

}