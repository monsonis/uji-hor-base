package es.uji.apps.hor;

@SuppressWarnings("serial")
public class EventoMasDeUnaRepeticionException extends GeneralHORException
{
    public EventoMasDeUnaRepeticionException()
    {
        super(
                "La classe té més d'una repetició. En aquesta vista només es poden moure les classes amb una repetició.");
    }
}
