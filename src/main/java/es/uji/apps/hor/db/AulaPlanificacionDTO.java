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
 * The persistent class for the HOR_AULAS_PLANIFICACION database table.
 * 
 */
@Entity
@Table(name = "HOR_AULAS_PLANIFICACION")
public class AulaPlanificacionDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ESTUDIO_ID")
    private EstudioDTO estudio;

    @Column(name = "SEMESTRE_ID")
    private Long semestre;

    // bi-directional many-to-one association to AulaDTO
    @ManyToOne
    @JoinColumn(name = "AULA_ID")
    private AulaDTO aula;

    public AulaPlanificacionDTO()
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

    public AulaDTO getAula()
    {
        return this.aula;
    }

    public void setAula(AulaDTO aula)
    {
        this.aula = aula;
    }

    public EstudioDTO getEstudio()
    {
        return estudio;
    }

    public void setEstudio(EstudioDTO estudio)
    {
        this.estudio = estudio;
    }

    public Long getSemestre()
    {
        return semestre;
    }

    public void setSemestre(Long semestre)
    {
        this.semestre = semestre;
    }

}