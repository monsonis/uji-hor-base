package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.QDiaSemanaDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.QItemsAsignaturaDTO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class GrupoAsignaturaDAODatabaseImpl extends BaseDAODatabaseImpl implements
        GrupoAsignaturaDAO
{
    @Autowired
    EventosDAO eventosDAO;

    @Override
    @Transactional
    public List<GrupoAsignatura> getGruposAsignaturasSinAsignar(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;
        QItemsAsignaturaDTO asignatura = QItemsAsignaturaDTO.itemsAsignaturaDTO;

        List<String> tiposCalendarios = TipoSubgrupo.getTiposSubgrupos(calendariosIds);

        List<ItemDTO> listaItemsDTO = query
                .from(asignatura)
                .join(asignatura.item, item)
                .where(asignatura.estudioId.eq(estudioId).and(
                        item.cursoId.eq(cursoId).and(item.semestre.id.eq(semestreId))
                                .and(item.grupoId.eq(grupoId))
                                .and(item.tipoSubgrupoId.in(tiposCalendarios))
                                .and(item.diaSemana.isNull()))).list(item);

        List<GrupoAsignatura> gruposAsignaturas = new ArrayList<GrupoAsignatura>();

        for (ItemDTO itemDTO : listaItemsDTO)
        {
            gruposAsignaturas.add(creaGrupoAsignaturaDesde(itemDTO, estudioId));
        }

        return gruposAsignaturas;
    }

    private GrupoAsignatura creaGrupoAsignaturaDesde(ItemDTO itemDTO, Long estudioId)
    {
        GrupoAsignatura grupoAsignatura = new GrupoAsignatura(itemDTO.getId());

        String tipoSubgrupoId = itemDTO.getTipoSubgrupoId();
        TipoSubgrupo tipoSubgrupo = TipoSubgrupo.valueOf(tipoSubgrupoId);

        Calendario calendario = new Calendario(tipoSubgrupo.getCalendarioAsociado(),
                tipoSubgrupo.getNombre());

        Asignatura asignatura = eventosDAO.creaAsignaturasDesdeItemDTOParaUnEstudio(itemDTO,
                estudioId);

        grupoAsignatura.setAsignatura(asignatura);
        grupoAsignatura.setCalendario(calendario);
        grupoAsignatura.setSubgrupoId(itemDTO.getSubgrupoId());
        return grupoAsignatura;
    }

    @Override
    @Transactional
    public GrupoAsignatura getGrupoAsignaturaById(Long grupoAsignaturaId, Long estudioId)
            throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<ItemDTO> listaItemsDTO = query.from(item).where(item.id.eq(grupoAsignaturaId))
                .list(item);

        if (listaItemsDTO.size() == 1)
        {
            return creaGrupoAsignaturaDesde(listaItemsDTO.get(0), estudioId);
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    @Transactional
    public void updateGrupoAsignaturaPlanificado(GrupoAsignatura grupoAsignatura)
            throws RegistroNoEncontradoException
    {

        if (!existeElGrupoAsignatura(grupoAsignatura))
        {
            throw new RegistroNoEncontradoException();
        }

        JPAQuery query = new JPAQuery(entityManager);
        QItemDTO item = QItemDTO.itemDTO;

        QDiaSemanaDTO diaSemana = QDiaSemanaDTO.diaSemanaDTO;
        query.from(diaSemana).where(diaSemana.nombre.eq(grupoAsignatura.getDiaSemana()));
        DiaSemanaDTO diaSemanaDTO = query.list(diaSemana).get(0);

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, item);
        updateClause.where(item.id.eq(grupoAsignatura.getId()))
                .set(item.horaInicio, grupoAsignatura.getInicio())
                .set(item.horaFin, grupoAsignatura.getFin()).set(item.diaSemana, diaSemanaDTO)
                .execute();
    }

    private boolean existeElGrupoAsignatura(GrupoAsignatura grupoAsignatura)
    {
        try
        {
            get(QItemDTO.class, grupoAsignatura.getId());
        }
        catch (PersistenceException e)
        {
            return false;
        }

        return true;
    }
}
