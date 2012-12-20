package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.QTuple;

import es.uji.apps.hor.db.EstudioDTO;
import es.uji.apps.hor.db.QEstudioDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.TipoEstudioDTO;
import es.uji.apps.hor.model.Estudio;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class EstudiosDAODatabaseImpl extends BaseDAODatabaseImpl implements EstudiosDAO
{
    @Override
    public List<Estudio> getEstudios()
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<Tuple> listaEstudiosTuples = query.from(item).orderBy(item.estudioDesc.asc())
                .listDistinct(new QTuple(item.estudioDesc, item.estudio.id));

        List<Estudio> estudios = new ArrayList<Estudio>();

        for (Tuple tuple : listaEstudiosTuples)
        {
            estudios.add(creaEstudioDesde(tuple, item));
        }

        return estudios;
    }

    private Estudio creaEstudioDesde(Tuple tuple, QItemDTO item)
    {
        Estudio estudio = new Estudio(tuple.get(item.estudio.id), tuple.get(item.estudioDesc));

        return estudio;
    }

    @Override
    public List<Estudio> getEstudiosByCentroId(Long centroId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QEstudioDTO qEstudio = QEstudioDTO.estudioDTO;

        query.from(qEstudio).where(qEstudio.centro.id.eq(centroId));

        List<Estudio> listaEstudios = new ArrayList<Estudio>();

        for (EstudioDTO estudioDTO : query.list(qEstudio))
        {
            listaEstudios.add(creaEstudioDesdeEstudioDTO(estudioDTO));
        }

        return listaEstudios;
    }

    private Estudio creaEstudioDesdeEstudioDTO(EstudioDTO estudioDTO)
    {
        Estudio estudio = new Estudio(estudioDTO.getId(), estudioDTO.getNombre());
        estudio.setTipoEstudio(estudioDTO.getTipoEstudio().getNombre());
        estudio.setTipoEstudioId(estudioDTO.getTipoEstudio().getId());
        return estudio;
    }

    private EstudioDTO convierteEstudioAEstudioDTO(Estudio estudio)
    {
        TipoEstudioDTO tipoEstudioDTO = new TipoEstudioDTO();
        tipoEstudioDTO.setId(estudio.getTipoEstudioId());
        tipoEstudioDTO.setNombre(estudio.getTipoEstudio());

        EstudioDTO estudioDTO = new EstudioDTO();
        estudioDTO.setId(estudio.getId());
        estudioDTO.setNombre(estudio.getNombre());
        estudioDTO.setTipoEstudio(tipoEstudioDTO);
        return estudioDTO;
    }

    @Override
    public Estudio insert(Estudio estudio)
    {
        EstudioDTO estudioDTO = convierteEstudioAEstudioDTO(estudio);
        EstudioDTO nuevoEstudioDTO = this.insert(estudioDTO);
        return creaEstudioDesdeEstudioDTO(nuevoEstudioDTO);
    }
}