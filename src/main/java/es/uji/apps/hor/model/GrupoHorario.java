package es.uji.apps.hor.model;

import java.util.Date;

public class GrupoHorario
{
    private Long id;
    private Long estudioId;
    private Long cursoId;
    private Long semestreId;
    private String grupoId;
    private Date horaInicio;
    private Date horaFin;

    public GrupoHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId) {
        this.estudioId = estudioId;
        this.cursoId = cursoId;
        this.semestreId = semestreId;
        this.grupoId = grupoId;
    }
    
    public GrupoHorario()
    {
        // TODO Auto-generated constructor stub
    }

    public Date getHoraInicio()
    {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio)
    {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin()
    {
        return horaFin;
    }

    public void setHoraFin(Date horaFin)
    {
        this.horaFin = horaFin;
    }

    public Long getEstudioId()
    {
        return estudioId;
    }

    public void setEstudioId(Long estudioId)
    {
        this.estudioId = estudioId;
    }

    public Long getCursoId()
    {
        return cursoId;
    }

    public void setCursoId(Long cursoId)
    {
        this.cursoId = cursoId;
    }

    public Long getSemestreId()
    {
        return semestreId;
    }

    public void setSemestreId(Long semestreId)
    {
        this.semestreId = semestreId;
    }

    public String getGrupoId()
    {
        return grupoId;
    }

    public void setGrupoId(String grupoId)
    {
        this.grupoId = grupoId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

}
