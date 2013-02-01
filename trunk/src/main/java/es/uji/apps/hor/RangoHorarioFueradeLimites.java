package es.uji.apps.hor;

@SuppressWarnings("serial")
public class RangoHorarioFueradeLimites extends GeneralHORException
{
    public RangoHorarioFueradeLimites()
    {
        super("Les hores sel·leccionades no son vàlides, ja que existeixen registres en el calendari que s'en ixen de la franja horària");
    }
}
