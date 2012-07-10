package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CalendariosDAO;
import es.uji.apps.hor.model.Calendario;

@Service
public class ConsultaCalendariosService
{
    private final CalendariosDAO calendariosDAO;

    @Autowired
    public ConsultaCalendariosService(CalendariosDAO calendariosDAO)
    {
        this.calendariosDAO = calendariosDAO;
    }

    public List<Calendario> getCalendarios()
    {
        return calendariosDAO.getCalendarios();
    }
}
