package es.uji.apps.hor.builders;

import java.util.Date;

import es.uji.apps.hor.dao.GrupoHorarioDAO;
import es.uji.apps.hor.model.GrupoHorario;

public class GrupoHorarioBuilder
{
    private GrupoHorario grupoHorario;
    private GrupoHorarioDAO grupoHorarioDAO;

    public GrupoHorarioBuilder(GrupoHorarioDAO grupoHorarioDAO)
    {
        this.grupoHorarioDAO = grupoHorarioDAO;
        grupoHorario = new GrupoHorario();
    }

    public GrupoHorarioBuilder()
    {
        this(null);
    }

    public GrupoHorarioBuilder withEstudioId(Long estudioId)
    {
        grupoHorario.setEstudioId(estudioId);
        return this;
    }

    public GrupoHorarioBuilder withCursoId(Long cursoId)
    {
        grupoHorario.setCursoId(cursoId);
        return this;
    }

    public GrupoHorarioBuilder withSemestreId(Long semestreId)
    {
        grupoHorario.setSemestreId(semestreId);
        return this;
    }

    public GrupoHorarioBuilder withGrupoId(String grupoId)
    {
        grupoHorario.setGrupoId(grupoId);
        return this;
    }

    public GrupoHorarioBuilder withHoraInicio(Date horaInicio)
    {
        grupoHorario.setHoraInicio(horaInicio);
        return this;
    }

    public GrupoHorarioBuilder withHoraFin(Date horaFin)
    {
        grupoHorario.setHoraFin(horaFin);
        return this;
    }

    public GrupoHorario build()
    {
        if (grupoHorarioDAO != null)
        {
            grupoHorario = grupoHorarioDAO.addHorario(grupoHorario);
        }

        return grupoHorario;
    }
}
