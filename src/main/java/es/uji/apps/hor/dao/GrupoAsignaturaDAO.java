package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.commons.db.BaseDAO;

public interface GrupoAsignaturaDAO extends BaseDAO
{
    List<GrupoAsignatura> getGruposAsignaturasSinAsignar(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds);
}
