package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

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

    public List<AulaPlanificacion> getAulasAsignadasToEstudio(Long estudioId, Long semestreId)
    {
        return aulaDAO.getAulasAsignadasToEstudio(estudioId, semestreId);
    }

    public AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId)
            throws RegistroNoEncontradoException, AulaYaAsignadaAEstudioException
    {
        return aulaDAO.asignaAulaToEstudio(estudioId, aulaId, semestreId);
    }

    public void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId)
            throws RegistroConHijosException
    {
        aulaDAO.deleteAulaAsignadaToEstudio(aulaPlanificacionId);
    }

}
