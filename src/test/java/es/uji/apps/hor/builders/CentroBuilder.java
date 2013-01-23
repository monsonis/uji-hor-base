package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.model.Centro;

public class CentroBuilder
{
    private Centro centro;
    private CentroDAO centroDAO;

    public CentroBuilder(CentroDAO centroDAO)
    {
        this.centroDAO = centroDAO;
        centro = new Centro();
    }

    public CentroBuilder()
    {
        this(null);
    }

    public CentroBuilder withNombre(String nombre)
    {
        centro.setNombre(nombre);
        return this;
    }

    public CentroBuilder withId(Long id)
    {
        centro.setId(id);
        return this;
    }

    public Centro build()
    {
        if (centroDAO != null)
        {
            centro = centroDAO.insertCentro(centro);
        }

        return centro;
    }
}
