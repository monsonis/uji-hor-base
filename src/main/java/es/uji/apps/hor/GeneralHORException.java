package es.uji.apps.hor;

import es.uji.commons.rest.exceptions.CoreBaseException;

@SuppressWarnings("serial")
public class GeneralHORException extends CoreBaseException
{
    public GeneralHORException()
    {
        super("S'ha produït un error en l'operació");
    }
    
    public GeneralHORException(String message)
    {
        super(message);
    }
}