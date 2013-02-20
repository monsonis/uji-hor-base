package es.uji.apps.hor.model;

import java.util.Calendar;
import java.util.Date;

import es.uji.apps.hor.DiaNoLectivoException;

public class CalendarioAcademico
{
    private Long id;

    private Long dia;

    private Long mes;

    private Long anyo;

    private String diaSemana;

    private Long diaSemanaId;

    private String tipoDia;

    private Date fecha;

    private Long vacaciones;

    final private static String DIA_EXAMENES = "E";

    final private static String DIA_LECTIVO = "L";

    final private static Long SABADO = new Long(6);

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getDia()
    {
        return dia;
    }

    public void setDia(Long dia)
    {
        this.dia = dia;
    }

    public Long getMes()
    {
        return mes;
    }

    public void setMes(Long mes)
    {
        this.mes = mes;
    }

    public Long getAnyo()
    {
        return anyo;
    }

    public void setAnyo(Long anyo)
    {
        this.anyo = anyo;
    }

    public String getDiaSemana()
    {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana)
    {
        this.diaSemana = diaSemana;
    }

    public Long getDiaSemanaId()
    {
        return diaSemanaId;
    }

    public void setDiaSemanaId(Long diaSemanaId)
    {
        this.diaSemanaId = diaSemanaId;
    }

    public String getTipoDia()
    {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia)
    {
        this.tipoDia = tipoDia;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Long getVacaciones()
    {
        return vacaciones;
    }

    public void setVacaciones(Long vacaciones)
    {
        this.vacaciones = vacaciones;
    }

    public void compruebaDiaLectivo() throws DiaNoLectivoException
    {
        if (!(tipoDia.equals(DIA_LECTIVO) || (tipoDia.equals(DIA_EXAMENES) && !diaSemanaId
                .equals(SABADO))))
        {
            throw new DiaNoLectivoException();
        }
    }

    public static Date getFechaSinHoraEstablecida(Date fecha)
    {
        Calendar calendarioOrig = Calendar.getInstance();
        calendarioOrig.setTime(fecha);

        Calendar calendario = Calendar.getInstance();
        calendario.set(calendarioOrig.get(Calendar.YEAR), calendarioOrig.get(Calendar.MONTH),
                calendarioOrig.get(Calendar.DAY_OF_MONTH));

        return calendario.getTime();
    }
}
