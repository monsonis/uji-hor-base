package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CursosDAO;
import es.uji.apps.hor.model.Curso;
import es.uji.commons.rest.Role;

@Service
public class CursosService
{
    private final CursosDAO cursosDAO;

    @Autowired
    public CursosService(CursosDAO cursosDAO)
    {
        this.cursosDAO = cursosDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Curso> getCursos(Long estudioId, Long connectedUserId)
    {
        return cursosDAO.getCursos(estudioId);
    }
}
