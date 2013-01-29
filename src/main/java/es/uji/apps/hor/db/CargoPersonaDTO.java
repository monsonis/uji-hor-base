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
 * The persistent class for the HOR_EXT_CARGOS_PER database table.
 * 
 */
@Entity
@Table(name = "HOR_EXT_CARGOS_PER")
@SuppressWarnings("serial")
public class CargoPersonaDTO implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    
    @Column(name = "CENTRO")
    private String nombreCentro;
    
    @Column(name = "CARGO")
    private String nombreCargo;
    
    @Column(name = "ESTUDIO")
    private String nombreEstudio;
    
    // bi-directional many-to-one association to EstudioDTO
    @ManyToOne
    @JoinColumn(name = "ESTUDIO_ID")
    private EstudioDTO estudio;

    // bi-directional many-to-one association to EstudioDTO
    @ManyToOne
    @JoinColumn(name = "CENTRO_ID")
    private CentroDTO centro;

    // bi-directional many-to-one association to PersonaDTO
    @ManyToOne
    @JoinColumn(name = "PERSONA_ID")
    private PersonaDTO persona;

    // bi-directional many-to-one association to PersonaDTO
    @ManyToOne
    @JoinColumn(name = "CARGO_ID")
    private TipoCargoDTO cargo;

    public CargoPersonaDTO()
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

    public EstudioDTO getEstudio()
    {
        return this.estudio;
    }

    public void setEstudio(EstudioDTO estudio)
    {
        this.estudio = estudio;
    }

    public PersonaDTO getPersona()
    {
        return this.persona;
    }

    public void setPersona(PersonaDTO persona)
    {
        this.persona = persona;
    }

    public CentroDTO getCentro()
    {
        return centro;
    }

    public void setCentro(CentroDTO centro)
    {
        this.centro = centro;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public TipoCargoDTO getCargo()
    {
        return cargo;
    }

    public void setCargo(TipoCargoDTO cargo)
    {
        this.cargo = cargo;
    }

    public String getNombreCentro()
    {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro)
    {
        this.nombreCentro = nombreCentro;
    }

    public String getNombreCargo()
    {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo)
    {
        this.nombreCargo = nombreCargo;
    }

    public String getNombreEstudio()
    {
        return nombreEstudio;
    }

    public void setNombreEstudio(String nombreEstudio)
    {
        this.nombreEstudio = nombreEstudio;
    }

}