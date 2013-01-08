package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.AreaEdificio;

public class AreaEdificioBuilder
{
    private AreaEdificio areaEdificio;

    public AreaEdificioBuilder()
    {
    }

    public AreaEdificioBuilder withNombre(String nombre)
    {
        areaEdificio.setNombre(nombre);
        return this;
    }

    public AreaEdificio build()
    {
        return areaEdificio;
    }
}
