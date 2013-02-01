package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.TipoAula;

public class TipoAulaBuilder
{
    private TipoAula tipoAula;

    public TipoAulaBuilder()
    {
        tipoAula = new TipoAula();
    }

    public TipoAulaBuilder withNombre(String nombre)
    {
        tipoAula.setNombre(nombre);
        return this;
    }

    public TipoAulaBuilder withEdificio(Edificio edificio)
    {
        tipoAula.setEdificio(edificio);
        return this;
    }

    public TipoAula build()
    {
        return tipoAula;
    }
}
