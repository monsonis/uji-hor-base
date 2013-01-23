package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Estudio;
import es.uji.commons.db.BaseDAO;

public interface EstudiosDAO extends BaseDAO
{
    List<Estudio> getEstudios();

    List<Estudio> getEstudiosVisiblesPorUsuario(Long userId);

    List<Estudio> getEstudiosByCentroId(Long centroId);

    Estudio insert(Estudio estudio);

    List<Estudio> getEstudiosByCentroIdVisiblesPorUsuario(Long centroId, Long connectedUserId);

}
