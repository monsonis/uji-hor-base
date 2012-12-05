package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.SemestresDetalleDAO;
import es.uji.apps.hor.model.SemestreDetalle;

@Service
public class SemestresDetalleService
{

    private final SemestresDetalleDAO semestresDetalleDAO;

    @Autowired
    public SemestresDetalleService(SemestresDetalleDAO semestresDetalleDAO)
    {
        this.semestresDetalleDAO = semestresDetalleDAO;
    }

    public List<SemestreDetalle> getSemestresDetallesTodos()
    {
        return semestresDetalleDAO.getSemestresDetalleTodos();
    }

    public List<SemestreDetalle> getSemestresDetallesPorEstudioIdYSemestreId(Long estudioId, Long semestreId)
    {
        return semestresDetalleDAO.getSemestresDetallesPorEstudioIdYSemestreId(estudioId, semestreId);
    }

}
