package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the HOR_V_AULAS_PERSONAS database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@IdClass(AulaPersonaDTOId.class)
@Table(name = "HOR_V_AULAS_PERSONAS")
public class AulaPersonaDTO implements Serializable
{
    @Id
    @Column(name = "AULA_ID")
    private Long aulaId;

    private String centro;

    @Column(name = "CENTRO_ID")
    private Long centroId;

    private String codigo;

    private String nombre;

    @Id
    @Column(name = "PERSONA_ID")
    private Long personaId;

    private String tipo;

    public AulaPersonaDTO()
    {
    }

    public Long getAulaId()
    {
        return this.aulaId;
    }

    public void setAulaId(Long aulaId)
    {
        this.aulaId = aulaId;
    }

    public String getCentro()
    {
        return this.centro;
    }

    public void setCentro(String centro)
    {
        this.centro = centro;
    }

    public Long getCentroId()
    {
        return this.centroId;
    }

    public void setCentroId(Long centroId)
    {
        this.centroId = centroId;
    }

    public String getCodigo()
    {
        return this.codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Long getPersonaId()
    {
        return this.personaId;
    }

    public void setPersonaId(Long personaId)
    {
        this.personaId = personaId;
    }

    public String getTipo()
    {
        return this.tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

}