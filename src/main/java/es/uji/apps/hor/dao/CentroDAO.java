package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Centro;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface CentroDAO extends BaseDAO
{
    List<Centro> getCentros();

    List<Centro> getCentrosVisiblesPorUsuario(Long connectedUserId);

    Centro getCentroById(Long centroId) throws RegistroNoEncontradoException;

    Centro insertCentro(Centro centro);

    Centro getCentroByAulaId(Long aulaId) throws RegistroNoEncontradoException;
    
    Centro getCentroGestionablePorUsuario(Long connectedUserId, List<Long> listaCargosIds);

}
