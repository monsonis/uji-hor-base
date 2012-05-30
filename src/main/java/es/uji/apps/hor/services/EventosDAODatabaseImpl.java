package es.uji.apps.hor.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.db.DetalleItemDTO;
import es.uji.apps.hor.db.QDetalleItemDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.model.Calendario;
import es.uji.apps.hor.model.Evento;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class EventosDAODatabaseImpl extends BaseDAODatabaseImpl implements EventosDAO
{
    @Override
    public List<Evento> getEventosByEstudioAndCurso(Long estudioId, Long cursoId,
            Date rangoFechasInicio, Date rangoFechasFin)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO item = QItemDTO.itemDTO;
        QDetalleItemDTO detalleItem = QDetalleItemDTO.detalleItemDTO;

        List<DetalleItemDTO> listaItemsDTO = query
                .from(detalleItem)
                .join(detalleItem.horItem, item)
                .where(item.horEstudio.id.eq(estudioId)
                        .and(item.cursoId.eq(new BigDecimal(cursoId)))
                        .and(detalleItem.dia.goe(rangoFechasInicio))
                        .and(detalleItem.dia.loe(rangoFechasFin)))
                .list(detalleItem);

        List<Evento> eventos = new ArrayList<Evento>();

        for (DetalleItemDTO itemDTO : listaItemsDTO)
        {
            eventos.add(creaEventoDesde(itemDTO));
        }

        return eventos;
    }

    @SuppressWarnings("deprecation")
    private Evento creaEventoDesde(DetalleItemDTO itemDTO)
    {
        String titulo = itemDTO.getHorItem().getAsignaturaId() + " - "
                + itemDTO.getHorItem().getGrupoId() + " - "
                + itemDTO.getHorItem().getTipoSubgrupoId() + itemDTO.getHorItem().getSubgrupoId();
        
        Calendario calendario = checkCalendarioFromTipoSubgrupo(itemDTO.getHorItem().getTipoSubgrupoId());

        Calendar inicio = Calendar.getInstance();
        inicio.setTime(itemDTO.getDia());
        inicio.set(Calendar.HOUR, itemDTO.getHoraInicio().getHours());
        inicio.set(Calendar.MINUTE, itemDTO.getHoraInicio().getMinutes());

        Calendar fin = Calendar.getInstance();
        fin.setTime(itemDTO.getDia());
        fin.set(Calendar.HOUR, itemDTO.getHoraFin().getHours());
        fin.set(Calendar.MINUTE, itemDTO.getHoraFin().getMinutes());

        return new Evento(itemDTO.getHorItem().getId(), calendario, titulo, inicio.getTime(),
                fin.getTime());
    }

    private Calendario checkCalendarioFromTipoSubgrupo(String tipoSubgrupoId)
    {
        long calendarioId = 0L;
        
        if ("TE".equals(tipoSubgrupoId))
        {
            calendarioId = 1L;
        }
        
        if ("PR".equals(tipoSubgrupoId))
        {
            calendarioId = 2L;
        }

        if ("LA".equals(tipoSubgrupoId))
        {
            calendarioId = 3L;
        }

        if ("SE".equals(tipoSubgrupoId))
        {
            calendarioId = 4L;
        }
        
        if ("TU".equals(tipoSubgrupoId))
        {
            calendarioId = 5L;
        }        
        
        return new Calendario(calendarioId);
    }
}
