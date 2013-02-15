package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.commons.db.BaseDAO;

public interface EdificiosDAO extends BaseDAO
{
    public List<Edificio> getEdificiosByCentroId(Long centroId);

    public List<Edificio> getEdificiosVisiblesPorUsuarioByCentroId(Long centroId, Long semestreId,
            Long connectedUserId);

    public List<PlantaEdificio> getPlantasEdificioByCentroAndEdificio(Long centroId, String edificio);

    public List<PlantaEdificio> getPlantasEdificioVisiblesPorUsuarioByCentroAndSemestreAndEdificio(
            Long centroId, Long semestreId, String edificio, Long connectedUserId);
}
