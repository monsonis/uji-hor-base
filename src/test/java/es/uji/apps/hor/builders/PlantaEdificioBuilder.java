package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.PlantaEdificio;

public class PlantaEdificioBuilder
{
    private PlantaEdificio plantaEdificio;

    public PlantaEdificioBuilder()
    {
    }

    public PlantaEdificioBuilder withNombre(String nombre)
    {
        plantaEdificio.setNombre(nombre);
        return this;
    }

    public PlantaEdificio build()
    {
        return plantaEdificio;
    }
}
