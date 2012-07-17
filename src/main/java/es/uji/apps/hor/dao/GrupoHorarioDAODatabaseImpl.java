package es.uji.apps.hor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.GrupoHorarioDTO;
import es.uji.apps.hor.db.QGrupoHorarioDTO;
import es.uji.apps.hor.model.GrupoHorario;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class GrupoHorarioDAODatabaseImpl extends BaseDAODatabaseImpl implements GrupoHorarioDAO
{

    @Override
    public GrupoHorario getGrupoHorarioById(String grupoId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QGrupoHorarioDTO qGrupoHorarioDTO = QGrupoHorarioDTO.grupoHorarioDTO;

        query.from(qGrupoHorarioDTO).where(qGrupoHorarioDTO.grupoId.eq(grupoId));
        List<GrupoHorarioDTO> listaGrupoHorarioDTO = query.list(qGrupoHorarioDTO);
        
        if (listaGrupoHorarioDTO.size() > 0) {
            GrupoHorarioDTO grupoHorarioDTO = listaGrupoHorarioDTO.get(0); 
            GrupoHorario grupoHorario = new GrupoHorario(grupoHorarioDTO.getGrupoId());
            grupoHorario.setHoraFin(grupoHorarioDTO.getHoraFin());
            grupoHorario.setHoraInicio(grupoHorarioDTO.getHoraInicio());
            
            return grupoHorario;
        } else {
            return new GrupoHorario(grupoId);
        }
    }

}
