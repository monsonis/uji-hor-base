package es.uji.apps.hor.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.Evento;

@Service
public class ConsultaEventosService
{
    private final EventosDAO eventosDAO;

    @Autowired
    public ConsultaEventosService(EventosDAO eventosDAO)
    {
        this.eventosDAO = eventosDAO;
    }

    public List<Evento> eventosDeUnEstudio(Long estudioId, Long cursoId, Date rangoFechasInicio,
            Date rangoFechasFin)
    {
        return eventosDAO.getEventosByEstudioAndCurso(estudioId, cursoId, rangoFechasInicio,
                rangoFechasFin);
    }

    public List<Evento> eventosSemanaGenericaDeUnEstudio(Long estudioId, Long cursoId,
            Long semestreId, String grupoId)
    {
        return eventosDAO.getEventosSemanaGenerica(estudioId, cursoId, semestreId, grupoId);
    }
}
