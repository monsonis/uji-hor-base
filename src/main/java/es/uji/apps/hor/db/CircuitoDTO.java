package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the HOR_CIRCUITOS database table.
 * 
 */
@Entity
@Table(name = "HOR_CIRCUITOS")
@SuppressWarnings("serial")
public class CircuitoDTO implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "GRUPO_ID")
    private String grupoId;

    private String nombre;
    private Long plazas;

    @OneToMany(mappedBy = "estudio")
    private Set<CircuitoEstudioDTO> circuitosEstudios;

    @OneToMany(mappedBy = "item")
    private Set<ItemCircuitoDTO> itemsCircuitos;

    public CircuitoDTO()
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

    public String getGrupoId()
    {
        return this.grupoId;
    }

    public void setGrupoId(String grupoId)
    {
        this.grupoId = grupoId;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Long getPlazas()
    {
        return plazas;
    }

    public void setPlazas(Long plazas)
    {
        this.plazas = plazas;
    }

    public Set<CircuitoEstudioDTO> getCircuitosEstudios()
    {
        return circuitosEstudios;
    }

    public void setCircuitosEstudios(Set<CircuitoEstudioDTO> circuitosEstudios)
    {
        this.circuitosEstudios = circuitosEstudios;
    }

    public Set<ItemCircuitoDTO> getItemsCircuitos()
    {
        return itemsCircuitos;
    }

    public void setItemsCircuitos(Set<ItemCircuitoDTO> itemsCircuitos)
    {
        this.itemsCircuitos = itemsCircuitos;
    }
}