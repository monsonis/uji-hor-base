package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface AulaDAO extends BaseDAO
{
    List<Aula> getAulas();

    List<Aula> getAulasByCentroIdAndestudioId(Long centroId, Long estudioId);

    List<Aula> getAulasByCentroId(Long centroId);

    List<AulaPlanificacion> getAulasAsignadasToEstudio(Long estudioId, Long semestreId);

    AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId)
            throws RegistroNoEncontradoException, AulaYaAsignadaAEstudioException;

    void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId) throws RegistroConHijosException;
    
    AulaPlanificacion getAulaById(Long aulaId) throws RegistroNoEncontradoException;
    
    Aula insertAula(Aula aula);
    
    AulaPlanificacion insertAulaPlanificacion(AulaPlanificacion aulaPlanificacion);
}
