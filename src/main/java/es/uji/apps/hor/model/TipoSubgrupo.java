package es.uji.apps.hor.model;

import java.util.ArrayList;
import java.util.List;

public enum TipoSubgrupo
{
    TE(1L), PR(2L), LA(3L), SE(4L), TU(5L), AV(6L);
    
    private final static int TEORIA = 1;
    private final static int PROBLEMAS = 2;
    private final static int LABORATORIOS = 3;
    private final static int SEMINARIOS = 4;
    private final static int TUTORIAS = 5;
    private final static int EVALUACION = 6;

    private final Long calendarioId;

    TipoSubgrupo(Long calendarioId)
    {
        this.calendarioId = calendarioId;
    }

    public Long getCalendarioAsociado()
    {
        return this.calendarioId;
    }

    public String getNombre()
    {
        switch (calendarioId.intValue())
        {
        case TEORIA:
            return "Teoria";
        case PROBLEMAS:
            return "Problemes";
        case LABORATORIOS:
            return "Laboratoris";
        case SEMINARIOS:
            return "Seminaris";
        case TUTORIAS:
            return "Tutories";
        case EVALUACION:
            return "Avaluació";
        default:
            return "";
        }
    }
    
    public static String getTipoSubgrupo(Long calendarioId)
    {
        switch (calendarioId.intValue())
        {
        case TEORIA:
            return TipoSubgrupo.TE.name();
        case PROBLEMAS:
            return TipoSubgrupo.PR.name();
        case LABORATORIOS:
            return TipoSubgrupo.LA.name();
        case SEMINARIOS:
            return TipoSubgrupo.SE.name();
        case TUTORIAS:
            return TipoSubgrupo.TU.name();
        case EVALUACION:
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
