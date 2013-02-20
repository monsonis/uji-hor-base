package es.uji.apps.hor;

@SuppressWarnings("serial")
public class DiaNoLectivoException extends GeneralHORException
{
    public DiaNoLectivoException()
    {
        super("No es pot donar classe en un dia festiu");
    }
}
