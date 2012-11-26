package es.uji.apps.hor;


@SuppressWarnings("serial")
public class DuracionEventoIncorrectaException extends GeneralHORException
{
    public DuracionEventoIncorrectaException(String msg)
    {
        super(msg);
    }

    public DuracionEventoIncorrectaException()
    {
        super();
    }
}