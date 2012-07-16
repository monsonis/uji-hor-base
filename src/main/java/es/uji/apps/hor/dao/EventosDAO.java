package es.uji.apps.hor.dao;

import java.util.Date;
import java.util.List;

import es.uji.apps.hor.model.Evento;
import es.uji.commons.db.BaseDAO;

public interface EventosDAO extends BaseDAO
{
    List<Evento> getEventosByEstudioAndCurso(Long estudioId, Long cursoId, Date rangoFechasInicio,
            Date rangoFechasFin);

    List<Evento> getEventosSemanaGenerica(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, List<Long> calendariosIds);

    Evento modificaDiaYHoraGrupoAsignatura(Long grupoAsignaturaId, Date inicio, Date fin);
}
