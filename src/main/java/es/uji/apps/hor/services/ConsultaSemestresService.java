package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.SemestresDAO;
import es.uji.apps.hor.model.Semestre;

@Service
public class ConsultaSemestresService
{
    private final SemestresDAO semestresDAO;

    @Autowired
    public ConsultaSemestresService(SemestresDAO semestresDAO)
    {
        this.semestresDAO = semestresDAO;
    }

    public List<Semestre> getSemestres(Long cursoId, Long estudioId)
    {
        return semestresDAO.getSemestres(cursoId, estudioId);
    }
}
