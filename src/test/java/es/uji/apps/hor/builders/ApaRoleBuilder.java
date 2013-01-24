package es.uji.apps.hor.builders;

import es.uji.commons.sso.dao.ApaDAO;
import es.uji.commons.sso.db.ApaRole;

public class ApaRoleBuilder
{
    private ApaDAO apaDAO;
    private ApaRole ApaRoleDTO;

    public ApaRoleBuilder(ApaDAO apaDAO)
    {
        this.apaDAO = apaDAO;
        this.ApaRoleDTO = new ApaRole();
    }

    public ApaRoleBuilder()
    {
        this(null);
    }

    public ApaRoleBuilder withId(Long id)
    {
        ApaRoleDTO.setId(id);
        return this;
    }

    public ApaRoleBuilder withNombre(String nombre)
    {
        ApaRoleDTO.setNombre(nombre);
        return this;
    }

    
    public ApaRole build()
    {
        if (apaDAO != null)
        {
            ApaRoleDTO = apaDAO.insert(ApaRoleDTO);
        }

        return ApaRoleDTO;
    }

}
