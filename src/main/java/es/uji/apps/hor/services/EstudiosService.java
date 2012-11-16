package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.model.Estudio;

@Service
public class EstudiosService
{
    private final EstudiosDAO estudiosDAO;

    @Autowired
    public EstudiosService(EstudiosDAO estudiosDAO)
    {
        this.estudiosDAO = estudiosDAO;
    }

    public List<Estudio> getEstudios()
    {
        return estudiosDAO.getEstudios();
    }

    public List<Estudio> getEstudiosByCentroId(Long centroId)
    {
        return estudiosDAO.getEstudiosByCentroId(centroId);
    }

}
