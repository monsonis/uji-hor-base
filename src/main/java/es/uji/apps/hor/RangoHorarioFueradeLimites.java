package es.uji.apps.hor;


@SuppressWarnings("serial")
public class RangoHorarioFueradeLimites extends GeneralHORException
{
    public RangoHorarioFueradeLimites(String msg)
    {
        super("Les hores sel·leccionades no son vàlides, ja que existeixen registres en el calendari que s'ixen de la franja horària");
    }

    public RangoHorarioFueradeLimites()
    {
        super();
    }
}
