package es.uji.apps.hor;

@SuppressWarnings("serial")
public class EventoFueraDeFechasSemestreException extends GeneralHORException
{
    public EventoFueraDeFechasSemestreException()
    {
        super("La classe no està dintre de les dates del semestre");
    }
}
