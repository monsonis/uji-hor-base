package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.model.Centro;

@Service
public class CentrosService
{
    private final CentroDAO centroDAO;

    @Autowired
    public CentrosService(CentroDAO centroDAO)
    {
        this.centroDAO = centroDAO;
    }

    public List<Centro> getCentros()
    {
        return centroDAO.getCentros();
    }
}
