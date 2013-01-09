package es.uji.apps.hor.builders;

import java.util.Date;

import es.uji.apps.hor.dao.RangoHorarioDAO;
import es.uji.apps.hor.model.RangoHorario;

public class RangoHorarioBuilder
{
    private RangoHorario rangoHorario;
    private RangoHorarioDAO rangoHorarioDAO;

    public RangoHorarioBuilder(RangoHorarioDAO rangoHorarioDAO)
    {
        this.rangoHorarioDAO = rangoHorarioDAO;
        rangoHorario = new RangoHorario();
    }

    public RangoHorarioBuilder()
    {
        this(null);
    }

    public RangoHorarioBuilder withEstudioId(Long estudioId)
    {
        rangoHorario.setEstudioId(estudioId);
        return this;
    }

    public RangoHorarioBuilder withCursoId(Long cursoId)
    {
        rangoHorario.setCursoId(cursoId);
        return this;
    }

    public RangoHorarioBuilder withSemestreId(Long semestreId)
    {
        rangoHorario.setSemestreId(semestreId);
        return this;
    }

    public RangoHorarioBuilder withGrupoId(String grupoId)
    {
        rangoHorario.setGrupoId(grupoId);
        return this;
    }

    public RangoHorarioBuilder withHoraInicio(Date horaInicio)
    {
        rangoHorario.setHoraInicio(horaInicio);
        return this;
    }

    public RangoHorarioBuilder withHoraFin(Date horaFin)
    {
        rangoHorario.setHoraFin(horaFin);
        return this;
    }

    public RangoHorario build()
    {
        if (rangoHorarioDAO != null)
        {
            rangoHorario = rangoHorarioDAO.addHorario(rangoHorario);
        }

        return rangoHorario;
    }
}
