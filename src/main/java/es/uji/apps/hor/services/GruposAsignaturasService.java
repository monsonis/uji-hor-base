package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.GrupoAsignaturaDAO;
import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Service
public class GruposAsignaturasService
{
    private final GrupoAsignaturaDAO grupoAsignaturaDAO;

    @Autowired
    public GruposAsignaturasService(GrupoAsignaturaDAO grupoAsignaturaDAO)
    {
        this.grupoAsignaturaDAO = grupoAsignaturaDAO;
    }

    public List<GrupoAsignatura> getGruposAsignaturasSinAsignar(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds)
    {
        return grupoAsignaturaDAO.getGruposAsignaturasSinAsignar(estudioId, cursoId, semestreId,
                grupoId, calendariosIds);
    }

    public GrupoAsignatura planificaGrupoAsignaturaSinAsignar(Long grupoAsignaturaId)
            throws RegistroNoEncontradoException
    {
        GrupoAsignatura grupoAsignatura = grupoAsignaturaDAO
                .getGrupoAsignaturaById(grupoAsignaturaId);
        grupoAsignatura.planificaGrupoAsignaturaSinAsignar();
        grupoAsignaturaDAO.updateGrupoAsignaturaPlanificado(grupoAsignatura);

        return grupoAsignatura;
    }
}
