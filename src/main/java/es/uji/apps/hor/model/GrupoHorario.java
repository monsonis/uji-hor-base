package es.uji.apps.hor.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.uji.apps.hor.RangoHorarioFueradeLimites;

public class GrupoHorario
{
    private Long id;
    private Long estudioId;
    private Long cursoId;
    private Long semestreId;
    private String grupoId;
    private Date horaInicio;
    private Date horaFin;

    public GrupoHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId)
    {
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

    public void compruebaValidezRangoHorario(List<Evento> eventos)
            throws RangoHorarioFueradeLimites
    {
        Integer horaMin = 0;
        Integer horaMax = 0;

        for (Evento evento : eventos)
        {
            Calendar itemInicio = Calendar.getInstance();
            itemInicio.setTime(evento.getInicio());

            Calendar itemFinal = Calendar.getInstance();
            itemFinal.setTime(evento.getFin());

            Integer tempMin = itemInicio.get(Calendar.HOUR_OF_DAY) * 100
                    + itemInicio.get(Calendar.MINUTE);
            Integer tempMax = itemFinal.get(Calendar.HOUR_OF_DAY) * 100
                    + itemInicio.get(Calendar.MINUTE);

            if (horaMin == 0 || horaMin > tempMin)
            {
                horaMin = tempMin;
            }

            if (horaMax == 0 || horaMax < tempMax)
            {
                horaMax = tempMax;
            }
        }

        Calendar calendarioInicio = Calendar.getInstance();
        Calendar calendarioFin = Calendar.getInstance();

        calendarioInicio.setTime(horaInicio);
        calendarioFin.setTime(horaFin);

        Integer nuevaHoraMin = calendarioInicio.get(Calendar.HOUR_OF_DAY) * 100
                + calendarioInicio.get(Calendar.MINUTE);
        Integer nuevaHoraMax = calendarioFin.get(Calendar.HOUR_OF_DAY) * 100
                + calendarioFin.get(Calendar.MINUTE);

        if (nuevaHoraMin > horaMin || nuevaHoraMax < horaMax)
        {
            throw new RangoHorarioFueradeLimites();
        }
    }

    public void actualizaRangoHorario(Date inicio, Date fin)
    {
        this.horaInicio = inicio;
        this.horaFin = fin;
    }

    public static GrupoHorario creaNuevoRangoHorario(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, Date horaInicio, Date horaFin)
    {
        GrupoHorario grupoHorario = new GrupoHorario(estudioId, cursoId, semestreId, grupoId);
        grupoHorario.setHoraInicio(horaInicio);
        grupoHorario.setHoraFin(horaFin);

        return grupoHorario;
    }
}
