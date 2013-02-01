package es.uji.apps.hor;

@SuppressWarnings("serial")
public class EventoDetalleSinEventoException extends GeneralHORException
{
    public EventoDetalleSinEventoException()
    {
        super("La informació detallada de l'event no està relacionada a un event correcte");
    }
}
