package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Edificio;

public class AreaEdificioBuilder
{
    private AreaEdificio areaEdificio;

    public AreaEdificioBuilder()
    {
        areaEdificio = new AreaEdificio();
    }

    public AreaEdificioBuilder withNombre(String nombre)
    {
        areaEdificio.setNombre(nombre);
        return this;
    }

    public AreaEdificioBuilder withEdificio(Edificio edificio)
    {
        areaEdificio.setEdificio(edificio);
        return this;
    }

    public AreaEdificio build()
    {
        return areaEdificio;
    }
}
