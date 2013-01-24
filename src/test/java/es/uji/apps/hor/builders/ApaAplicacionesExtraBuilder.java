package es.uji.apps.hor.builders;

import java.math.BigDecimal;

import es.uji.commons.sso.dao.ApaDAO;
import es.uji.commons.sso.db.ApaAplicacione;
import es.uji.commons.sso.db.ApaAplicacionesExtra;
import es.uji.commons.sso.db.ApaRole;

public class ApaAplicacionesExtraBuilder
{
    private ApaDAO apaDAO;
    private ApaAplicacionesExtra ApaAplicacionesExtraDTO;

    public ApaAplicacionesExtraBuilder(ApaDAO apaDAO)
    {
        this.apaDAO = apaDAO;
        this.ApaAplicacionesExtraDTO = new ApaAplicacionesExtra();
    }

    public ApaAplicacionesExtraBuilder()
    {
        this(null);
    }

    public ApaAplicacionesExtraBuilder withId(Long id)
    {
        ApaAplicacionesExtraDTO.setId(id);
        return this;
    }

    public ApaAplicacionesExtraBuilder withPersonaId(Long personaId)
    {
        ApaAplicacionesExtraDTO.setPersonaId(new BigDecimal(personaId));
        return this;
    }

    public ApaAplicacionesExtraBuilder withAplicacion(ApaAplicacione aplicacion)
    {
        ApaAplicacionesExtraDTO.setApaAplicacione(aplicacion);
        return this;
    }

    public ApaAplicacionesExtraBuilder withRole(ApaRole rol)
    {
        ApaAplicacionesExtraDTO.setApaRole(rol);
        return this;
    }

    public ApaAplicacionesExtra build()
    {
        if (apaDAO != null)
        {
            ApaAplicacionesExtraDTO = apaDAO.insert(ApaAplicacionesExtraDTO);
        }

        return ApaAplicacionesExtraDTO;
    }

}
