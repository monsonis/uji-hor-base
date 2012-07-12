package es.uji.apps.hor.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class GrupoAsignaturaDAODatabaseImpl extends BaseDAODatabaseImpl implements
        GrupoAsignaturaDAO
{

    @Override
    public List<GrupoAsignatura> getGruposAsignaturasSinAsignar(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<String> tiposCalendarios = TipoSubgrupo.getTiposSubgrupos(calendariosIds);

        List<ItemDTO> listaItemsDTO = query
                .from(item)
                .where(item.estudio.id.eq(estudioId).and(
                        item.cursoId.eq(new BigDecimal(cursoId))
                                .and(item.semestre.id.eq(semestreId)).and(item.grupoId.eq(grupoId))
                                .and(item.tipoSubgrupoId.in(tiposCalendarios))
                                .and(item.diasSemana.isNull()))).list(item);

        List<GrupoAsignatura> gruposAsignaturas = new ArrayList<GrupoAsignatura>();

        for (ItemDTO itemDTO : listaItemsDTO)
        {
            gruposAsignaturas.add(creaGrupoAsignaturaDesde(itemDTO));
        }

        return gruposAsignaturas;
    }

    private GrupoAsignatura creaGrupoAsignaturaDesde(ItemDTO itemDTO)
    {
        return new GrupoAsignatura(itemDTO.getId(), itemDTO.toString());
    }

}
