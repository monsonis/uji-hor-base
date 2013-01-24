package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EdificiosDAO;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;

@Service
public class EdificiosService
{
    private final EdificiosDAO edificiosDAO;

    @Autowired
    public EdificiosService(EdificiosDAO edificiosDAO)
    {
        this.edificiosDAO = edificiosDAO;
    }

    public List<Edificio> getEdificiosByCentroId(Long centroId)
    {
        return edificiosDAO.getEdificiosByCentroId(centroId);
    }

    public List<PlantaEdificio> getPlantasEdificioByCentroAndEdificio(Long centroId, String edificio)
    {
        return edificiosDAO.getPlantasEdificioByCentroAndEdificio(centroId, edificio);
    }
}
