package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Circuito;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroConHijosException;

public interface CircuitoDAO extends BaseDAO
{

    List<Circuito> getCircuitosByEstudioIdAndSemestreIdAndGrupoId(Long estudioId, Long semestreId, String grupoId);
    void deleteCircuitoById(Long circuitoId) throws RegistroConHijosException;
    Circuito addCircuito(Circuito circuito);
    Circuito getCircuitoById(Long circuitoId, Long estudioId);
    Circuito insertNuevoCircuitoEnEstudio(Circuito circuito);
    
}
