package es.uji.apps.hor.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.uji.apps.hor.db.TipoEstudioDTO;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class TipoEstudioDAODatabaseImpl extends BaseDAODatabaseImpl implements TipoEstudioDAO
{

    @Override
    @Transactional
    public TipoEstudio insert(TipoEstudio tipoEstudio)
    {
        TipoEstudioDTO tipoEstudioDTO = new TipoEstudioDTO();
        tipoEstudioDTO.setNombre(tipoEstudio.getNombre());
        tipoEstudioDTO.setId(tipoEstudio.getId());
        tipoEstudioDTO = insert(tipoEstudioDTO);

        return creaTipoEstudioDesdeTipoEstudioDTO(tipoEstudioDTO);
    }

    private TipoEstudio creaTipoEstudioDesdeTipoEstudioDTO(TipoEstudioDTO tipoEstudioDTO)
    {
        return new TipoEstudio(tipoEstudioDTO.getId(), tipoEstudioDTO.getNombre());
    }
}