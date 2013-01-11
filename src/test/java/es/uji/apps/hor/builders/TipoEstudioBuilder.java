package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.TipoEstudio;

public class TipoEstudioBuilder
{
    private TipoEstudio tipoEstudio;

    public TipoEstudioBuilder()
    {
        tipoEstudio = new TipoEstudio();
    }

    public TipoEstudioBuilder withNombre(String nombre)
    {
        tipoEstudio.setNombre(nombre);
        return this;
    }

    public TipoEstudioBuilder withOrden(Integer orden)
    {
        tipoEstudio.setOrden(orden);
        return this;
    }

    public TipoEstudio build()
    {
        return tipoEstudio;
    }
}
