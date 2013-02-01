package es.uji.apps.hor;

@SuppressWarnings("serial")
public class EventoNoDivisibleException extends GeneralHORException
{
    public EventoNoDivisibleException()
    {
        super("La classe sel·leccionada no es pot dividir perquè dura menys de 1 hora");
    }
}