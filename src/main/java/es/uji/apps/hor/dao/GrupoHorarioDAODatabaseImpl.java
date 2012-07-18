package es.uji.apps.hor.dao;

import java.util.Calendar;
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
    public GrupoHorario getGrupoHorarioById(Long estudioId, Long cursoId, Long semestreId,
            String grupoId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QGrupoHorarioDTO qGrupoHorarioDTO = QGrupoHorarioDTO.grupoHorarioDTO;

        query.from(qGrupoHorarioDTO).where(
                qGrupoHorarioDTO.estudioId.eq(estudioId).and(
                        qGrupoHorarioDTO.cursoId.eq(cursoId).and(
                                qGrupoHorarioDTO.semestreId.eq(semestreId).and(
                                        qGrupoHorarioDTO.grupoId.eq(grupoId)))));
        List<GrupoHorarioDTO> listaGrupoHorarioDTO = query.list(qGrupoHorarioDTO);

        if (listaGrupoHorarioDTO.size() > 0)
        {
            GrupoHorarioDTO grupoHorarioDTO = listaGrupoHorarioDTO.get(0);
            GrupoHorario grupoHorario = new GrupoHorario(estudioId, cursoId, semestreId, grupoId);
            grupoHorario.setHoraFin(grupoHorarioDTO.getHoraFin());
            grupoHorario.setHoraInicio(grupoHorarioDTO.getHoraInicio());

            return grupoHorario;
        }
        else
        {
            GrupoHorario grupoHorario = new GrupoHorario(estudioId, cursoId, semestreId, grupoId);
            Calendar inicio = Calendar.getInstance();
            Calendar fin = Calendar.getInstance();

            inicio.set(Calendar.AM_PM, Calendar.AM);
            inicio.set(Calendar.HOUR, 8);
            inicio.set(Calendar.MINUTE, 0);
            inicio.set(Calendar.SECOND, 0);

            fin.set(Calendar.AM_PM, Calendar.PM);
            fin.set(Calendar.HOUR, 10);
            fin.set(Calendar.MINUTE, 0);
            fin.set(Calendar.SECOND, 0);
            grupoHorario.setHoraInicio(inicio.getTime());
            grupoHorario.setHoraFin(fin.getTime());
            return grupoHorario;
            
        }
    }

}
