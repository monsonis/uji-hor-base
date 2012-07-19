package es.uji.apps.hor.dao;

import java.util.Date;

import es.uji.apps.hor.model.GrupoHorario;
import es.uji.commons.db.BaseDAO;

public interface GrupoHorarioDAO extends BaseDAO
{
    GrupoHorario getGrupoHorarioById(Long estudioId, Long cursoId, Long semestreId, String grupoId);

    GrupoHorario addHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId,
            Date horaInicio, Date horaFin);

    GrupoHorario updateHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId,
            Date horaInicio, Date horaFin);
    
}
