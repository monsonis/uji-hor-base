package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface AulaDAO extends BaseDAO
{
    List<Aula> getAulas();

    List<Aula> getAulasByCentroIdAndestudioId(Long centroId, Long estudioId);

    List<Aula> getAulasByCentroId(Long centroId);

    List<Aula> getAulasAsignadasToEstudio(Long estudioId, Long semestreId, Long cursoId);

    AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId, Long cursoId)
            throws RegistroNoEncontradoException;
}
