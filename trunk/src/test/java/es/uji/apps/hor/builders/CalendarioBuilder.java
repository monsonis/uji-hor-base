package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.Calendario;

public class CalendarioBuilder
{
    private Calendario calendario;

    public CalendarioBuilder()
    {
        calendario = new Calendario();
    }

    public CalendarioBuilder withId(Long id)
    {
        calendario.setId(id);
        return this;
    }

    public CalendarioBuilder withNombre(String nombre)
    {
        calendario.setNombre(nombre);
        return this;
    }

    public Calendario build()
    {
        return calendario;
    }

}
