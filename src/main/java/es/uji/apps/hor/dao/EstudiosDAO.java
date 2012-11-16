package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Estudio;
import es.uji.commons.db.BaseDAO;

public interface EstudiosDAO extends BaseDAO
{
    List<Estudio> getEstudios();

    List<Estudio> getEstudiosByCentroId(Long centroId);
    
}
