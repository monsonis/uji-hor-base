package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.model.Aula;

@Service
public class AulaService
{
    private final AulaDAO aulaDAO;

    @Autowired
    public AulaService(AulaDAO aulaDAO)
    {
        this.aulaDAO = aulaDAO;
    }

    public List<Aula> getAulas()
    {
        return aulaDAO.getAulas();
    }

    public List<Aula> getAulasByCentroId(Long centroId)
    {
        return aulaDAO.getAulasByCentroId(centroId);
    }

    public List<Aula> getAulasByCentroIdAndestudioId(Long centroId, Long estudioId)
    {
        return aulaDAO.getAulasByCentroIdAndestudioId(centroId, estudioId);
    }
}
