package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.SemestresDAO;
import es.uji.apps.hor.model.Semestre;
import es.uji.commons.rest.Role;

@Service
public class SemestresService
{
    private final SemestresDAO semestresDAO;

    @Autowired
    public SemestresService(SemestresDAO semestresDAO)
    {
        this.semestresDAO = semestresDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Semestre> getSemestres(Long cursoId, Long estudioId, Long connectedUserId)
    {
        return semestresDAO.getSemestres(cursoId, estudioId);
    }
}
