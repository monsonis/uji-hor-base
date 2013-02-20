package es.uji.apps.hor;

@SuppressWarnings("serial")
public class EventoFueraDeRangoException extends GeneralHORException
{
    public EventoFueraDeRangoException()
    {
        super(
                "La classe no està dintre del rang horari permès en algun dels estudis als quals pertany");
    }

    public EventoFueraDeRangoException(String grupo, String rangoHorario)
    {
        super("La classe del grup " + grupo + " no està dintre del rang horari de " + rangoHorario
                + " en algun dels estudis als que pertany");
    }
}
