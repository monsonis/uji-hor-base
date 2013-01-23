package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.TipoAulaDAO;
import es.uji.apps.hor.model.TipoAula;

@Service
public class TipoAulaService
{
    private final TipoAulaDAO tipoAulaDAO;

    @Autowired
    public TipoAulaService(TipoAulaDAO tipoAulaDAO)
    {
        this.tipoAulaDAO = tipoAulaDAO;
    }

    public List<TipoAula> getTiposAulaByCentroAndEdificio(Long centroId, String edificio)
    {
        return tipoAulaDAO.getTiposAulaByCentroAndEdificio(centroId, edificio);
    }
}
