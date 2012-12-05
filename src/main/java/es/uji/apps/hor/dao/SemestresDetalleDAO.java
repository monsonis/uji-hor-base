package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.commons.db.BaseDAO;

public interface SemestresDetalleDAO extends BaseDAO
{
    List<SemestreDetalle> getSemestresDetalleTodos();

    List<SemestreDetalle> getSemestresDetallesPorEstudioIdYSemestreId(Long estudioId, Long semestreId);
}
