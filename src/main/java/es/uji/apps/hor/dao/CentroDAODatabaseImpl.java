package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.AulaDTO;
import es.uji.apps.hor.db.CentroDTO;
import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.db.QCentroDTO;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.TipoAula;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class CentroDAODatabaseImpl extends BaseDAODatabaseImpl implements CentroDAO
{
    private final AulaDAO aulaDAO;
    
    @Autowired
    public CentroDAODatabaseImpl(AulaDAO aulaDAO)
    {
        this.aulaDAO = aulaDAO;
    }
    
    @Override
    public List<Centro> getCentros()
    {
        JPAQuery query = new JPAQuery(entityManager);

        QCentroDTO qCentro = QCentroDTO.centroDTO;

        query.from(qCentro);

        List<Centro> listaCentros = new ArrayList<Centro>();

        for (CentroDTO centroDTO : query.list(qCentro))
        {
            listaCentros.add(creaCentroDesdeCentroDTO(centroDTO));
        }

        return listaCentros;
    }

    private Centro creaCentroDesdeCentroDTO(CentroDTO centroDTO)
    {
        return new Centro(centroDTO.getId(), centroDTO.getNombre());
    }

    @Override
    public Centro getCentroById(Long centroId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);

        QAulaDTO qAula = QAulaDTO.aulaDTO;

        QCentroDTO qCentro = QCentroDTO.centroDTO;

        query.from(qCentro).where(qCentro.id.eq(centroId));

        List<CentroDTO> listaCentrosDTO = query.list(qCentro);
        
        if (listaCentrosDTO.size() == 1) {
            Centro centro = new Centro();
            centro.setId(centroId);
            centro.setNombre(listaCentrosDTO.get(0).getNombre());
            centro.setEdificios(creaEdificiosCompletosDesdeCentro(centro));
            return centro;

        } else {
            throw new RegistroNoEncontradoException();
        }
        
    }

    private List<Edificio> creaEdificiosCompletosDesdeCentro(Centro centro)
    {
        JPAQuery query = new JPAQuery(entityManager);
        
        QAulaDTO qAula = QAulaDTO.aulaDTO;
        query.from(qAula).where(qAula.centro.id.eq(centro.getId()));

        List<AulaDTO> listaAulasDTO = query.list(qAula);
        List<Edificio> listaEdificios = creaListaEdificiosDesdeListaAulasDTO(listaAulasDTO);

        for (Edificio edificio : listaEdificios)
        {
            edificio.setCentro(centro);
        }

        return listaEdificios;
    }

    private List<Edificio> creaListaEdificiosDesdeListaAulasDTO(List<AulaDTO> listaAulasDTO)
    {
        List<Edificio> listaEdificios = new ArrayList<Edificio>();

        Map<String, List<AulaDTO>> mapaEdificiosAulasDTO = new HashMap<String, List<AulaDTO>>();

        for (AulaDTO aulaDTO : listaAulasDTO)
        {
            if (!mapaEdificiosAulasDTO.containsKey(aulaDTO.getEdificio()))
            {
                mapaEdificiosAulasDTO.put(aulaDTO.getEdificio(), new ArrayList<AulaDTO>());
            }

            mapaEdificiosAulasDTO.get(aulaDTO.getEdificio()).add(aulaDTO);
        }

        for (List<AulaDTO> listaAulasDTOEdificio : mapaEdificiosAulasDTO.values())
        {
            listaEdificios.add(creaEdificioDesdeAulasDTO(listaAulasDTOEdificio));
        }

        return listaEdificios;

    }

    private Edificio creaEdificioDesdeAulasDTO(List<AulaDTO> listaAulasDTO)
    {
        Map<Aula, AulaDTO> mapaAulasYAulasDTO = creaMapaAulasYAulasDTO(listaAulasDTO);

        List<PlantaEdificio> listaPlantasEdificio = creaListaPlantasEdificioDesdeMapaAulasYAulasDTO(mapaAulasYAulasDTO);
        List<AreaEdificio> listaAreaEdificios = creaListaAreasEdificioDesdeMapaAulasYAulasDTO(mapaAulasYAulasDTO);
        List<TipoAula> listaTipoAulas = creaTipoAulasDesdeMapaAulasYAulasDTO(mapaAulasYAulasDTO);
        Edificio edificio = new Edificio();

        edificio.setNombre(listaAulasDTO.get(0).getEdificio());
        edificio.setPlantas(listaPlantasEdificio);
        for (PlantaEdificio plantaEdificio : listaPlantasEdificio)
        {
            plantaEdificio.setEdificio(edificio);
        }

        edificio.setAreas(listaAreaEdificios);
        for (AreaEdificio areaEdificio : listaAreaEdificios)
        {
            areaEdificio.setEdificio(edificio);
        }

        edificio.setTiposAulas(listaTipoAulas);
        for (TipoAula tipoAula : listaTipoAulas)
        {
            tipoAula.setEdificio(edificio);
        }

        return edificio;
    }

    private List<TipoAula> creaTipoAulasDesdeMapaAulasYAulasDTO(
            Map<Aula, AulaDTO> mapaAulasYAulasDTO)
    {
        Map<String, TipoAula> mapaTiposAulas = new HashMap<String, TipoAula>();

        for (Entry<Aula, AulaDTO> aulaEntry : mapaAulasYAulasDTO.entrySet())
        {
            AulaDTO aulaDTO = aulaEntry.getValue();
            Aula aula = aulaEntry.getKey();

            if (!mapaTiposAulas.keySet().contains(aulaDTO.getTipo()))
            {
                TipoAula tipoAula = new TipoAula();
                List<Aula> listaAulas = new ArrayList<Aula>();
                listaAulas.add(aula);
                tipoAula.setAulas(listaAulas);
                tipoAula.setNombre(aulaDTO.getTipo());
                aula.setTipo(tipoAula);
                mapaTiposAulas.put(aulaDTO.getTipo(), tipoAula);
            }
            else
            {
                TipoAula tipoAula = mapaTiposAulas.get(aulaDTO.getTipo());
                tipoAula.getAulas().add(aula);
                aula.setTipo(tipoAula);
            }
        }
        return new ArrayList<TipoAula>(mapaTiposAulas.values());

    }

    private List<AreaEdificio> creaListaAreasEdificioDesdeMapaAulasYAulasDTO(
            Map<Aula, AulaDTO> mapaAulasYAulasDTO)
    {
        Map<String, AreaEdificio> mapaAreaEdificios = new HashMap<String, AreaEdificio>();

        for (Entry<Aula, AulaDTO> aulaEntry : mapaAulasYAulasDTO.entrySet())
        {
            AulaDTO aulaDTO = aulaEntry.getValue();
            Aula aula = aulaEntry.getKey();

            if (!mapaAreaEdificios.keySet().contains(aulaDTO.getArea()))
            {
                AreaEdificio areaEdificio = new AreaEdificio();
                List<Aula> listaAulas = new ArrayList<Aula>();
                areaEdificio.setAulas(listaAulas);
                areaEdificio.setNombre(aulaDTO.getArea());
                areaEdificio.getAulas().add(aula);
                aula.setArea(areaEdificio);
                mapaAreaEdificios.put(aulaDTO.getArea(), areaEdificio);
            }
            else
            {
                AreaEdificio areaEdificio = mapaAreaEdificios.get(aulaDTO.getArea());
                areaEdificio.getAulas().add(aula);
                aula.setArea(areaEdificio);
            }
        }
        return new ArrayList<AreaEdificio>(mapaAreaEdificios.values());
    }

    private Map<Aula, AulaDTO> creaMapaAulasYAulasDTO(List<AulaDTO> listaAulasDTO)
    {
        Map<Aula, AulaDTO> mapaAulasYAulasDTO = new HashMap<Aula, AulaDTO>();

        for (AulaDTO aulaDTO : listaAulasDTO)
        {
            Aula aula = aulaDAO.creaAulaDesdeAulaDTO(aulaDTO);
            mapaAulasYAulasDTO.put(aula, aulaDTO);
        }

        return mapaAulasYAulasDTO;
    }

    private List<PlantaEdificio> creaListaPlantasEdificioDesdeMapaAulasYAulasDTO(
            Map<Aula, AulaDTO> mapaAulasYAulasDTO)
    {
        Map<String, PlantaEdificio> mapaPlantaEdificios = new HashMap<String, PlantaEdificio>();

        for (Entry<Aula, AulaDTO> aulaEntry : mapaAulasYAulasDTO.entrySet())
        {
            AulaDTO aulaDTO = aulaEntry.getValue();
            Aula aula = aulaEntry.getKey();

            if (!mapaPlantaEdificios.keySet().contains(aulaDTO.getPlanta()))
            {
                PlantaEdificio plantaEdificio = new PlantaEdificio();
                List<Aula> listaAulas = new ArrayList<Aula>();
                listaAulas.add(aula);
                plantaEdificio.setAulas(listaAulas);
                plantaEdificio.setNombre(aulaDTO.getPlanta());
                mapaPlantaEdificios.put(plantaEdificio.getNombre(), plantaEdificio);
                aula.setPlanta(plantaEdificio);
            }
            else
            {
                PlantaEdificio plantaEdificio = mapaPlantaEdificios.get(aulaDTO.getPlanta());
                plantaEdificio.getAulas().add(aula);
                aula.setPlanta(plantaEdificio);
            }
        }
        return new ArrayList<PlantaEdificio>(mapaPlantaEdificios.values());
    }

    @Override
    @Transactional
    public Centro insertCentro(Centro centro)
    {
        // Creamos un nuevo centro
        CentroDTO centroDTO = new CentroDTO();
        centroDTO.setNombre(centro.getNombre());
        centroDTO = insert(centroDTO);

        return this.creaCentroDesdeCentroDTO(centroDTO);
    }

}