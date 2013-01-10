package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;

public class PlantaEdificioBuilder
{
    private PlantaEdificio plantaEdificio;

    public PlantaEdificioBuilder()
    {
        plantaEdificio = new PlantaEdificio();
    }

    public PlantaEdificioBuilder withNombre(String nombre)
    {
        plantaEdificio.setNombre(nombre);
        return this;
    }

    public PlantaEdificioBuilder withEdificio(Edificio edificio)
    {
        plantaEdificio.setEdificio(edificio);
        return this;
    }

    public PlantaEdificio build()
    {
        return plantaEdificio;
    }
}
