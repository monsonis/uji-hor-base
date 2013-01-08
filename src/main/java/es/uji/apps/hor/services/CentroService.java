package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.model.Centro;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Service
public class CentroService
{
    private final CentroDAO centroDAO;

    @Autowired
    public CentroService(CentroDAO centroDAO)
    {
        this.centroDAO = centroDAO;
    }

    public List<Centro> getCentros()
    {
        return centroDAO.getCentros();
    }
    
    public Centro getCentroById(Long centroId) throws RegistroNoEncontradoException
    {
        Centro centro = centroDAO.getCentroById(centroId);
        
        return centro;
    }

}
