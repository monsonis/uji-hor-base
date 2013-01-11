package es.uji.apps.hor.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.uji.apps.hor.RangoHorarioFueradeLimites;

public class RangoHorario
{
    private static final int MINUTOS_EN_UNA_HORA = 60;
    private Long id;
    private Long estudioId;
    private Long cursoId;
    private Long semestreId;
    private String grupoId;
    private Calendar horaInicio = Calendar.getInstance();;
    private Calendar horaFin = Calendar.getInstance();;

    public RangoHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId)
    {
        this.estudioId = estudioId;
        this.cursoId = cursoId;
        this.semestreId = semestreId;
        this.grupoId = grupoId;
    }

    public RangoHorario()
    {

    }

    public Date getHoraInicio()
    {
        return horaInicio.getTime();
    }

    public void setHoraInicio(Date horaInicio)
    {
        this.horaInicio.setTime(horaInicio);
    }

    public Date getHoraFin()
    {
        return horaFin.getTime();
    }

    public void setHoraFin(Date horaFin)
    {
        this.horaFin.setTime(horaFin);
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

    public void compruebaSiLosEventosEstanDentroDelRangoHorario(List<Evento> eventos)
            throws RangoHorarioFueradeLimites
    {
        for (Evento evento : eventos)
        {
            if (!estaDentroDelRangoHorario(evento))
            {
                throw new RangoHorarioFueradeLimites();
            }
        }
    }

    private boolean estaDentroDelRangoHorario(Evento evento)
    {
        int minutosInicioEvento = calculaMinutosDeUnDate(evento.getInicio());
        int minutosFinEvento = calculaMinutosDeUnDate(evento.getFin());

        return minutosInicioEvento >= getHoraInicialDelGrupoEnMinutos()
                && minutosFinEvento <= getHoraFinalDelGrupoEnMinutos();
    }

    private Integer getHoraFinalDelGrupoEnMinutos()
    {
        return calculaMinutosDeUnCalendar(this.horaFin);
    }

    private Integer getHoraInicialDelGrupoEnMinutos()
    {
        return calculaMinutosDeUnCalendar(this.horaInicio);
    }

    private Integer calculaMinutosDeUnDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return calculaMinutosDeUnCalendar(cal);
    }

    private Integer calculaMinutosDeUnCalendar(Calendar calendar)
    {
        return calendar.get(Calendar.HOUR_OF_DAY) * MINUTOS_EN_UNA_HORA
                + calendar.get(Calendar.MINUTE);
    }

    public void actualizaRangoHorario(Date inicio, Date fin)
    {
        this.horaInicio.setTime(inicio);
        this.horaFin.setTime(fin);
    }

    public static RangoHorario creaNuevoRangoHorario(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, Date horaInicio, Date horaFin)
    {
        RangoHorario rangoHorario = new RangoHorario(estudioId, cursoId, semestreId, grupoId);
        rangoHorario.setHoraInicio(horaInicio);
        rangoHorario.setHoraFin(horaFin);

        return rangoHorario;
    }

    private static RangoHorario rangoPorDefecto = null;

    public static RangoHorario getRangoHorarioPorDefecto(Long estudioId, Long cursoId,
            Long semestreId, String grupoId)
    {
        if (rangoPorDefecto == null)
        {
            Calendar inicio = Calendar.getInstance();
            Calendar fin = Calendar.getInstance();

            inicio.set(Calendar.HOUR_OF_DAY, 8);
            inicio.set(Calendar.MINUTE, 0);
            inicio.set(Calendar.SECOND, 0);

            fin.set(Calendar.HOUR_OF_DAY, 22);
            fin.set(Calendar.MINUTE, 0);
            fin.set(Calendar.SECOND, 0);

            rangoPorDefecto = creaNuevoRangoHorario(estudioId, cursoId, semestreId, grupoId,
                    inicio.getTime(), fin.getTime());
        }

        return rangoPorDefecto;
    }
}
