package es.uji.apps.hor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.GrupoHorarioDTO;
import es.uji.apps.hor.db.QGrupoHorarioDTO;
import es.uji.apps.hor.model.GrupoHorario;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class GrupoHorarioDAODatabaseImpl extends BaseDAODatabaseImpl implements GrupoHorarioDAO
{

    @Override
    public GrupoHorario getGrupoHorario(Long estudioId, Long cursoId, Long semestreId,
            String grupoId) throws RegistroNoEncontradoException
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
            return creaGrupoHorario(grupoHorarioDTO);
        }
        else
        {
            /*
             * GrupoHorario grupoHorario = new GrupoHorario(estudioId, cursoId, semestreId,
             * grupoId); Calendar inicio = Calendar.getInstance(); Calendar fin =
             * Calendar.getInstance();
             * 
             * inicio.set(Calendar.HOUR_OF_DAY, 8); inicio.set(Calendar.MINUTE, 0);
             * inicio.set(Calendar.SECOND, 0);
             * 
             * fin.set(Calendar.HOUR_OF_DAY, 22); fin.set(Calendar.MINUTE, 0);
             * fin.set(Calendar.SECOND, 0); grupoHorario.setHoraInicio(inicio.getTime());
             * grupoHorario.setHoraFin(fin.getTime()); return grupoHorario;
             */
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    public GrupoHorario addHorario(GrupoHorario grupoHorario)
    {
        GrupoHorarioDTO grupoHorarioDTO = new GrupoHorarioDTO();
        grupoHorarioDTO.setCursoId(grupoHorario.getCursoId());
        grupoHorarioDTO.setEstudioId(grupoHorario.getEstudioId());
        grupoHorarioDTO.setSemestreId(grupoHorario.getSemestreId());
        grupoHorarioDTO.setGrupoId(grupoHorario.getGrupoId());
        grupoHorarioDTO.setHoraFin(grupoHorario.getHoraFin());
        grupoHorarioDTO.setHoraInicio(grupoHorario.getHoraInicio());

        grupoHorarioDTO = insert(grupoHorarioDTO);

        return creaGrupoHorario(grupoHorarioDTO);
    }

    private GrupoHorario creaGrupoHorario(GrupoHorarioDTO grupoHorarioDTO)
    {
        GrupoHorario grupoHorario = new GrupoHorario();

        grupoHorario.setId(grupoHorarioDTO.getId());
        grupoHorario.setCursoId(grupoHorarioDTO.getCursoId());
        grupoHorario.setEstudioId(grupoHorarioDTO.getEstudioId());
        grupoHorario.setSemestreId(grupoHorarioDTO.getSemestreId());
        grupoHorario.setGrupoId(grupoHorarioDTO.getGrupoId());
        grupoHorario.setHoraFin(grupoHorarioDTO.getHoraFin());
        grupoHorario.setHoraInicio(grupoHorarioDTO.getHoraInicio());

        return grupoHorario;
    }

    @Override
    public GrupoHorario updateHorario(GrupoHorario grupoHorario)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QGrupoHorarioDTO qGrupoHorarioDTO = QGrupoHorarioDTO.grupoHorarioDTO;

        query.from(qGrupoHorarioDTO).where(
                qGrupoHorarioDTO.estudioId.eq(grupoHorario.getEstudioId()).and(
                        qGrupoHorarioDTO.cursoId.eq(grupoHorario.getCursoId()).and(
                                qGrupoHorarioDTO.semestreId.eq(grupoHorario.getSemestreId()).and(
                                        qGrupoHorarioDTO.grupoId.eq(grupoHorario.getGrupoId())))));
        List<GrupoHorarioDTO> listaGrupoHorarioDTO = query.list(qGrupoHorarioDTO);

        GrupoHorarioDTO grupoHorarioDTO = listaGrupoHorarioDTO.get(0);
        grupoHorarioDTO.setHoraFin(grupoHorario.getHoraFin());
        grupoHorarioDTO.setHoraInicio(grupoHorario.getHoraInicio());
        grupoHorarioDTO = update(grupoHorarioDTO);

        return creaGrupoHorario(grupoHorarioDTO);
    }

}
