package es.uji.apps.hor.model;

import java.util.ArrayList;
import java.util.List;

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

    public static String getTipoSubgrupo(Long calendarioId)
    {
        switch (calendarioId.intValue())
        {
        case 1:
            return TipoSubgrupo.TE.name();
        case 2:
            return TipoSubgrupo.PR.name();
        case 3:
            return TipoSubgrupo.LA.name();
        case 4:
            return TipoSubgrupo.SE.name();
        case 5:
            return TipoSubgrupo.TU.name();
        case 6:
            return TipoSubgrupo.AV.name();
        default:
            return "";
        }
    }

    public static List<String> getTiposSubgrupos(List<Long> calendariosIds)
    {
        List<String> tiposSubgrupos = new ArrayList<String>();

        for (Long calendarioId : calendariosIds)
        {
            tiposSubgrupos.add(TipoSubgrupo.getTipoSubgrupo(calendarioId));
        }

        return tiposSubgrupos;
    }
}
