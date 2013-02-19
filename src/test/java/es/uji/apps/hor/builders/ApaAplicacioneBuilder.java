package es.uji.apps.hor.builders;

import es.uji.commons.sso.dao.ApaDAO;
import es.uji.commons.sso.db.ApaAplicacione;

public class ApaAplicacioneBuilder
{
    private ApaDAO apaDAO;
    private ApaAplicacione apaAplicacioneDTO;

    public ApaAplicacioneBuilder(ApaDAO apaDAO)
    {
        this.apaDAO = apaDAO;
        this.apaAplicacioneDTO = new ApaAplicacione();
    }

    public ApaAplicacioneBuilder()
    {
        this(null);
    }

    public ApaAplicacioneBuilder withId(Long id)
    {
        apaAplicacioneDTO.setId(id);
        return this;
    }

    public ApaAplicacioneBuilder withNombre(String nombre)
    {
        apaAplicacioneDTO.setNombre(nombre);
        return this;
    }

    
    public ApaAplicacione build()
    {
        if (apaDAO != null)
        {
            apaAplicacioneDTO = apaDAO.insert(apaAplicacioneDTO);
        }

        return apaAplicacioneDTO;
    }

}
