package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

import es.uji.apps.hor.db.DiaSemanaDTO;
import es.uji.apps.hor.db.ItemComunDTO;
import es.uji.apps.hor.db.ItemDTO;
import es.uji.apps.hor.db.QDiaSemanaDTO;
import es.uji.apps.hor.db.QItemComunDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.GrupoAsignatura;
import es.uji.apps.hor.model.TipoSubgrupo;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class GrupoAsignaturaDAODatabaseImpl extends BaseDAODatabaseImpl implements
        GrupoAsignaturaDAO
{
    @Override
    @Transactional
    public List<GrupoAsignatura> getGruposAsignaturasSinAsignar(Long estudioId, Long cursoId,
            Long semestreId, String grupoId, List<Long> calendariosIds)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<String> tiposCalendarios = TipoSubgrupo.getTiposSubgrupos(calendariosIds);

        List<ItemDTO> listaItemsDTO = query
                .from(item)
                .where(item.estudio.id.eq(estudioId).and(
                        item.cursoId.eq(cursoId).and(item.semestre.id.eq(semestreId))
                                .and(item.grupoId.eq(grupoId))
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
        GrupoAsignatura grupoAsignatura = new GrupoAsignatura(itemDTO.getId());

        String tipoSubgrupoId = itemDTO.getTipoSubgrupoId();
        TipoSubgrupo tipoSubgrupo = TipoSubgrupo.valueOf(tipoSubgrupoId);

        Calendario calendario = new Calendario(tipoSubgrupo.getCalendarioAsociado(),
                tipoSubgrupo.getNombre());

        Estudio estudio = new Estudio();
        estudio.setId(itemDTO.getEstudio().getId());
        estudio.setNombre(itemDTO.getEstudioDesc());
        estudio.setTipoEstudio(itemDTO.getTipoEstudio());
        estudio.setTipoEstudioId(itemDTO.getTipoEstudioId());

        Asignatura asignatura = new Asignatura();

        asignatura.setComun(itemDTO.getComun() == 1);
        if (itemDTO.getComun() > 0)
        {
            asignatura.setComunes(itemDTO.getComunes());
        }

        asignatura.setNombre(itemDTO.getAsignatura());
        asignatura.setId(itemDTO.getAsignaturaId());
        asignatura.setCursoId(itemDTO.getCursoId());
        asignatura.setCaracter(itemDTO.getCaracter());
        asignatura.setCaracterId(itemDTO.getCaracterId());
        asignatura.setEstudio(estudio);
        asignatura.setPorcentajeComun(itemDTO.getPorcentajeComun());
        asignatura.setTipoAsignatura(itemDTO.getTipoAsignatura());
        asignatura.setTipoAsignaturaId(itemDTO.getTipoAsignaturaId());

        grupoAsignatura.setAsignatura(asignatura);
        grupoAsignatura.setCalendario(calendario);
        grupoAsignatura.setSubgrupoId(itemDTO.getSubgrupoId());
        return grupoAsignatura;
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

        List<ItemComunDTO> comunes = getItemsComunes(item.getId());

        item.setDiaSemana(lunes);

        Calendar inicio = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();
        inicio.set(Calendar.HOUR_OF_DAY, 8);
        inicio.set(Calendar.MINUTE, 0);
        inicio.set(Calendar.SECOND, 0);
        fin.set(Calendar.HOUR_OF_DAY, 10);
        fin.set(Calendar.MINUTE, 0);
        fin.set(Calendar.SECOND, 0);

        item.setHoraInicio(inicio.getTime());
        item.setHoraFin(fin.getTime());
        update(item);

        if (item.getComun().equals(new Long(1)))
        {
            for (ItemComunDTO comun : comunes)
            {
                try
                {
                    ItemDTO itemComun = get(ItemDTO.class, comun.getItemComun().getId()).get(0);
                    itemComun.setHoraInicio(inicio.getTime());
                    itemComun.setHoraFin(fin.getTime());
                    itemComun.setDiaSemana(lunes);
                    update(itemComun);
                }
                catch (Exception e)
                {
                }
            }
        }

        return creaGrupoAsignaturaDesde(item);
    }

    private List<ItemComunDTO> getItemsComunes(Long itemId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QItemComunDTO itemComunDTO = QItemComunDTO.itemComunDTO;

        query.from(itemComunDTO).where(itemComunDTO.item.id.eq(itemId));

        return query.list(itemComunDTO);
    }

    @Override
    @Transactional
    public GrupoAsignatura getGrupoAsignaturaById(Long grupoAsignaturaId)
            throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<ItemDTO> listaItemsDTO = query.from(item).where(item.id.eq(grupoAsignaturaId))
                .list(item);

        if (listaItemsDTO.size() == 1)
        {
            return creaGrupoAsignaturaDesde(listaItemsDTO.get(0));
        }
        else
        {
            throw new RegistroNoEncontradoException();
        }
    }

    @Override
    @Transactional
    public void planificaGrupoAsignaturaSinAsignar(GrupoAsignatura grupoAsignatura)
            throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;

        List<ItemDTO> listaItemsDTO = query.from(item).where(item.id.eq(grupoAsignatura.getId()))
                .list(item);

        if (listaItemsDTO.size() == 0)
        {
            throw new RegistroNoEncontradoException();
        }

        query = new JPAQuery(entityManager);

        QDiaSemanaDTO diaSemana = QDiaSemanaDTO.diaSemanaDTO;
        query.from(diaSemana).where(diaSemana.nombre.eq(grupoAsignatura.getDiaSemana()));
        DiaSemanaDTO diaSemanaDTO = query.list(diaSemana).get(0);

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, item);
        updateClause.where(item.id.eq(grupoAsignatura.getId()))
                .set(item.horaInicio, grupoAsignatura.getInicio())
                .set(item.horaFin, grupoAsignatura.getFin()).set(item.diaSemana, diaSemanaDTO)
                .execute();
    }
}
