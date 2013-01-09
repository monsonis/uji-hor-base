package es.uji.apps.hor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.QRangoHorarioDTO;
import es.uji.apps.hor.db.RangoHorarioDTO;
import es.uji.apps.hor.model.RangoHorario;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class RangoHorarioDAODatabaseImpl extends BaseDAODatabaseImpl implements RangoHorarioDAO
{

    @Override
    public RangoHorario getRangoHorario(Long estudioId, Long cursoId, Long semestreId,
            String grupoId) throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);

        QRangoHorarioDTO qRangoHorarioDTO = QRangoHorarioDTO.rangoHorarioDTO;

        query.from(qRangoHorarioDTO).where(
                qRangoHorarioDTO.estudioId.eq(estudioId).and(
                        qRangoHorarioDTO.cursoId.eq(cursoId).and(
                                qRangoHorarioDTO.semestreId.eq(semestreId).and(
                                        qRangoHorarioDTO.grupoId.eq(grupoId)))));
        List<RangoHorarioDTO> listaRangoHorarioDTO = query.list(qRangoHorarioDTO);

        if (listaRangoHorarioDTO.size() > 0)
        {
            RangoHorarioDTO rangoHorarioDTO = listaRangoHorarioDTO.get(0);
            return creaRangoHorario(rangoHorarioDTO);
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    public RangoHorario addHorario(RangoHorario rangoHorario)
    {
        RangoHorarioDTO rangoHorarioDTO = new RangoHorarioDTO();
        rangoHorarioDTO.setCursoId(rangoHorario.getCursoId());
        rangoHorarioDTO.setEstudioId(rangoHorario.getEstudioId());
        rangoHorarioDTO.setSemestreId(rangoHorario.getSemestreId());
        rangoHorarioDTO.setGrupoId(rangoHorario.getGrupoId());
        rangoHorarioDTO.setHoraFin(rangoHorario.getHoraFin());
        rangoHorarioDTO.setHoraInicio(rangoHorario.getHoraInicio());

        rangoHorarioDTO = insert(rangoHorarioDTO);

        return creaRangoHorario(rangoHorarioDTO);
    }

    private RangoHorario creaRangoHorario(RangoHorarioDTO rangoHorarioDTO)
    {
        RangoHorario rangoHorario = new RangoHorario();

        rangoHorario.setId(rangoHorarioDTO.getId());
        rangoHorario.setCursoId(rangoHorarioDTO.getCursoId());
        rangoHorario.setEstudioId(rangoHorarioDTO.getEstudioId());
        rangoHorario.setSemestreId(rangoHorarioDTO.getSemestreId());
        rangoHorario.setGrupoId(rangoHorarioDTO.getGrupoId());
        rangoHorario.setHoraFin(rangoHorarioDTO.getHoraFin());
        rangoHorario.setHoraInicio(rangoHorarioDTO.getHoraInicio());

        return rangoHorario;
    }

    @Override
    public RangoHorario updateHorario(RangoHorario rangoHorario)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QRangoHorarioDTO qRangoHorarioDTO = QRangoHorarioDTO.rangoHorarioDTO;

        query.from(qRangoHorarioDTO).where(
                qRangoHorarioDTO.estudioId.eq(rangoHorario.getEstudioId()).and(
                        qRangoHorarioDTO.cursoId.eq(rangoHorario.getCursoId()).and(
                                qRangoHorarioDTO.semestreId.eq(rangoHorario.getSemestreId()).and(
                                        qRangoHorarioDTO.grupoId.eq(rangoHorario.getGrupoId())))));
        List<RangoHorarioDTO> listaRangoHorarioDTO = query.list(qRangoHorarioDTO);

        RangoHorarioDTO rangoHorarioDTO = listaRangoHorarioDTO.get(0);
        rangoHorarioDTO.setHoraFin(rangoHorario.getHoraFin());
        rangoHorarioDTO.setHoraInicio(rangoHorario.getHoraInicio());
        rangoHorarioDTO = update(rangoHorarioDTO);

        return creaRangoHorario(rangoHorarioDTO);
    }

}
