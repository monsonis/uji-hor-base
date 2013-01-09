package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.RangoHorario;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface RangoHorarioDAO extends BaseDAO
{
    RangoHorario getRangoHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId)
            throws RegistroNoEncontradoException;

    RangoHorario addHorario(RangoHorario rangoHorario);

    RangoHorario updateHorario(RangoHorario rangoHorario);

}
