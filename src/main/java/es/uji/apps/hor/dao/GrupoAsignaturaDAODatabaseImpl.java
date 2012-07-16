package es.uji.apps.hor.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.QDiaSemanaDTO;
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
                                .and(item.diaSemana.isNull()))).list(item);

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

    @Override
    public GrupoAsignatura asignaDiaYHoraPorDefecto(Long grupoAsignaturaId)
    {
        QItemDTO qItem = QItemDTO.itemDTO;
        JPAQuery query = new JPAQuery(entityManager);
        JPAQuery query2 = new JPAQuery(entityManager);

        query.from(qItem).where(qItem.id.eq(grupoAsignaturaId));
        ItemDTO item = query.list(qItem).get(0);

        QDiaSemanaDTO qDiaSemana = QDiaSemanaDTO.diaSemanaDTO;
        query2.from(qDiaSemana).where(qDiaSemana.nombre.eq("Dilluns"));
        DiaSemanaDTO lunes = query2.list(qDiaSemana).get(0);

        item.setDiaSemana(lunes);
        Calendar inicio = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();
        inicio.set(Calendar.HOUR, 8);
        inicio.set(Calendar.MINUTE, 0);
        inicio.set(Calendar.SECOND, 0);
        inicio.set(Calendar.AM_PM, Calendar.AM);
        fin.set(Calendar.HOUR, 10);
        fin.set(Calendar.MINUTE, 0);
        fin.set(Calendar.SECOND, 0);
        fin.set(Calendar.AM_PM, Calendar.AM);
        item.setHoraInicio(inicio.getTime());
        item.setHoraFin(fin.getTime());
        update(item);

        return creaGrupoAsignaturaDesde(item);
    }
}
