package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.AulaYaAsignadaAEstudioException;
import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroConHijosException;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface AulaDAO extends BaseDAO
{
    List<Aula> getAulasByCentroIdAndestudioId(Long centroId, Long estudioId);

    List<Aula> getAulasByCentroId(Long centroId);

    List<AulaPlanificacion> getAulasAsignadasToEstudio(Long estudioId, Long semestreId);

    AulaPlanificacion asignaAulaToEstudio(Long estudioId, Long aulaId, Long semestreId)
            throws RegistroNoEncontradoException, AulaYaAsignadaAEstudioException;

    void deleteAulaAsignadaToEstudio(Long aulaPlanificacionId) throws RegistroConHijosException,
            RegistroNoEncontradoException;

    AulaPlanificacion getAulaPlanificacionByAulaEstudioSemestre(Long aulaId, Long estudioId,
            Long semestreId) throws RegistroNoEncontradoException;

    Aula getAulaById(Long aulaId) throws RegistroNoEncontradoException;

    Aula getAulaYPlanificacionesByAulaId(Long aulaId) throws RegistroNoEncontradoException;

    Aula insertAula(Aula aula);

    AulaPlanificacion insertAulaPlanificacion(AulaPlanificacion aulaPlanificacion);

    Aula creaAulaDesdeAulaDTO(AulaDTO aulaDTO);

    List<TipoAula> getTiposAulaByCentroAndEdificio(Long centroId, String edificio);

    List<TipoAula> getTiposAulaVisiblesPorUsuarioByCentroAndSemestreAndEdificio(Long centroId,
            Long semestreId, String edificio, Long connectedUserId);

    Aula getAulaConEventosById(Long aulaId) throws RegistroNoEncontradoException;

    List<Aula> getAulasFiltradasPor(Long centroId, String edificio, String tipoAula, String planta);

    List<Aula> getAulasVisiblesPorUsuarioFiltradasPor(Long centroId, Long semestreId,
            String edificio, String tipoAula, String planta, Long connectedUserId);

    AulaPlanificacion getAulaPlanificacionById(Long aulaPlanificacionId)
            throws RegistroNoEncontradoException;
}
