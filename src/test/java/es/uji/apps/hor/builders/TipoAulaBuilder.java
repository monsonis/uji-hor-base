package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.TipoAula;

public class TipoAulaBuilder
{
    private TipoAula tipoAula;

    public TipoAulaBuilder()
    {
    }

    public TipoAulaBuilder withNombre(String nombre)
    {
        tipoAula.setNombre(nombre);
        return this;
    }

    public TipoAula build()
    {
        return tipoAula;
    }
}
