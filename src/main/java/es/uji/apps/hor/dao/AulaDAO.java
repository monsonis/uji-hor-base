package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Aula;
import es.uji.commons.db.BaseDAO;

public interface AulaDAO extends BaseDAO
{
    List<Aula> getAulas();

    List<Aula> getAulasByCentroIdAndestudioId(Long centroId, Long estudioId);

    List<Aula> getAulasByCentroId(Long centroId);
    
    
}
