package es.uji.apps.hor.dao;

import es.uji.apps.hor.model.GrupoHorario;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface GrupoHorarioDAO extends BaseDAO
{
    GrupoHorario getGrupoHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId)
            throws RegistroNoEncontradoException;

    GrupoHorario addHorario(GrupoHorario grupoHorario);

    GrupoHorario updateHorario(GrupoHorario grupoHorario);

}
