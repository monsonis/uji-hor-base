package es.uji.apps.hor;

@SuppressWarnings("serial")
public class AulaNoAsignadaAEstudioDelEventoException extends GeneralHORException
{
    public AulaNoAsignadaAEstudioDelEventoException()
    {
        super("L'aula seleccinada no està assignada a la titulació del event");
    }
}
