package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.Edificio;

public class EdificioBuilder
{
    private Edificio edificio;

    public EdificioBuilder()
    {
    }

    public EdificioBuilder withNombre(String nombre)
    {
        edificio.setNombre(nombre);
        return this;
    }

    public Edificio build()
    {
        return edificio;
    }
}
