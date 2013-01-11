package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.TipoEstudioDAO;
import es.uji.apps.hor.model.TipoEstudio;

public class TipoEstudioBuilder
{
    private TipoEstudio tipoEstudio;
    private TipoEstudioDAO tipoEstudioDAO;


    public TipoEstudioBuilder(TipoEstudioDAO tipoEstudioDAO)
    {
        this.tipoEstudioDAO = tipoEstudioDAO;
        tipoEstudio = new TipoEstudio();
    }

    public TipoEstudioBuilder()
    {
        this(null);
    }
    
    public TipoEstudioBuilder withId(String id)
    {
        tipoEstudio.setId(id);
        return this;
    }

    public TipoEstudioBuilder withNombre(String nombre)
    {
        tipoEstudio.setNombre(nombre);
        return this;
    }

    public TipoEstudioBuilder withOrden(Integer orden) {
        tipoEstudio.setOrden(orden);
        return this;
    }
    
    public TipoEstudio build()
    {
        if (tipoEstudioDAO != null)
        {
            tipoEstudio = tipoEstudioDAO.insert(tipoEstudio);
        }

        return tipoEstudio;
    }
}
