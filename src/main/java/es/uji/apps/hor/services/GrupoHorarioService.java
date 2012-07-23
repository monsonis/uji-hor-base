package es.uji.apps.hor.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.RangoHorarioFueradeLimites;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.dao.GrupoHorarioDAO;
import es.uji.apps.hor.model.Evento;
import es.uji.apps.hor.model.GrupoHorario;

@Service
public class GrupoHorarioService
{
    private final GrupoHorarioDAO grupoHorarioDAO;
    private final EventosDAO eventosDAO;


    @Autowired
    public GrupoHorarioService(GrupoHorarioDAO grupoHorarioDAO, EventosDAO eventosDAO)
    {
        this.grupoHorarioDAO = grupoHorarioDAO;
        this.eventosDAO = eventosDAO;
    }

    public GrupoHorario getHorarioById(Long estudioId, Long cursoId, Long semestreId, String grupoId)
    {
        return grupoHorarioDAO.getGrupoHorarioById(estudioId, cursoId, semestreId, grupoId);
    }

    public GrupoHorario addHorario(Long estudioId, Long cursoId, Long semestreId, String grupoId,
            Date horaInicio, Date horaFin)
    {
        return grupoHorarioDAO.addHorario(estudioId, cursoId, semestreId, grupoId, horaInicio,
                horaFin);
    }

    public GrupoHorario updateHorario(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, Date horaInicio, Date horaFin)
    {
        return grupoHorarioDAO.updateHorario(estudioId, cursoId, semestreId, grupoId, horaInicio,
                horaFin);
    }

    public void compruebaValidezRangoHorario(Long estudioId, Long cursoId, Long semestreId,
            String grupoId, Date inicio, Date fin) throws RangoHorarioFueradeLimites
    {
        List <Evento> eventos = eventosDAO.getEventosDeUnCurso(estudioId, cursoId, semestreId, grupoId);
        
        Integer horaMin = 0;
        Integer horaMax = 0;
        
        for (Evento evento: eventos) {
            Calendar itemInicio = Calendar.getInstance();
            itemInicio.setTime(evento.getInicio());
            
            Calendar itemFinal = Calendar.getInstance();
            itemFinal.setTime(evento.getFin());
            
            Integer tempMin = itemInicio.get(Calendar.HOUR_OF_DAY)*100 + itemInicio.get(Calendar.MINUTE);
            Integer tempMax = itemFinal.get(Calendar.HOUR_OF_DAY)*100 + itemInicio.get(Calendar.MINUTE);
            
            if (horaMin == 0 || horaMin > tempMin) {
                horaMin = tempMin;
            }
            
            if (horaMax == 0 || horaMax < tempMax) {
                horaMax = tempMax;
            }
        }
        
        Calendar calendarioInicio = Calendar.getInstance();
        Calendar calendarioFin = Calendar.getInstance();
        
        calendarioInicio.setTime(inicio);
        calendarioFin.setTime(fin);
        
        Integer nuevaHoraMin =  calendarioInicio.get(Calendar.HOUR_OF_DAY)*100 + calendarioInicio.get(Calendar.MINUTE);
        Integer nuevaHoraMax = calendarioFin.get(Calendar.HOUR_OF_DAY)*100 + calendarioFin.get(Calendar.MINUTE);
        
        if (nuevaHoraMin > horaMin || nuevaHoraMax < horaMax) {
            throw new RangoHorarioFueradeLimites();
        }
    }
}
