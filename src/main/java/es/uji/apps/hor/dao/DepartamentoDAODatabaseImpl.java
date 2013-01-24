package es.uji.apps.hor.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.DepartamentoDTO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Departamento;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class DepartamentoDAODatabaseImpl extends BaseDAODatabaseImpl implements DepartamentoDAO
{

    @Override
    @Transactional
    public Departamento insertDepartamento(Departamento departamento)
    {
        CentroDTO centroDTO = new CentroDTO();
        centroDTO.setId(departamento.getCentro().getId());
        
        DepartamentoDTO departamentoDTO = new DepartamentoDTO();
        departamentoDTO.setActivo(departamento.getActivo());
        departamentoDTO.setCentro(centroDTO);
        
        insert(departamentoDTO);
        
        return creaDepartamentoDesdeDepartamentoDTO(departamentoDTO);
    }

    private Departamento creaDepartamentoDesdeDepartamentoDTO(DepartamentoDTO departamentoDTO)
    {
        Departamento departamento = new Departamento();
        departamento.setId(departamentoDTO.getId());
        departamento.setNombre(departamentoDTO.getNombre());
        departamento.setActivo(departamentoDTO.getActivo());
        
        Centro centro = new Centro();
        centro.setId(departamentoDTO.getCentro().getId());
        departamento.setCentro(centro);
        
        return departamento;

    }
}
