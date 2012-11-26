package es.uji.apps.hor;

@SuppressWarnings("serial")
public class AulaYaAsignadaAEstudioException extends GeneralHORException
{
    public AulaYaAsignadaAEstudioException(String msg)
    {
        super(msg);
    }

    public AulaYaAsignadaAEstudioException()
    {
        super("Aquesta aula ja està assignada a l'estudi seleccionat amb les mateixes condicions");
    }
}
