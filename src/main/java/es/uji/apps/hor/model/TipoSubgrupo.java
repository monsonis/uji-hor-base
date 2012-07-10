package es.uji.apps.hor.model;

public enum TipoSubgrupo
{
    TE(1L), PR(2L), LA(3L), SE(4L), TU(5L), AV(6L);
    
    private final Long calendarioId;

    TipoSubgrupo(Long calendarioId)
    {
        this.calendarioId = calendarioId;
    }
    
    public Long getCalendarioAsociado()
    {
        return this.calendarioId;
    }
}
