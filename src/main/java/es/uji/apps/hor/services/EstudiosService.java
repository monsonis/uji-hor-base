package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.model.Estudio;
import es.uji.commons.rest.Role;

@Service
public class EstudiosService
{
    private final EstudiosDAO estudiosDAO;

    @Autowired
    public EstudiosService(EstudiosDAO estudiosDAO)
    {
        this.estudiosDAO = estudiosDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Estudio> getEstudios(Long connectedUserId)
    {
        return estudiosDAO.getEstudios();
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Estudio> getEstudiosByCentroId(Long centroId, Long connectedUserId)
    {
        return estudiosDAO.getEstudiosByCentroId(centroId);
    }

}
