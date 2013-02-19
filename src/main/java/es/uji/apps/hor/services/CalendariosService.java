package es.uji.apps.hor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CalendariosDAO;
import es.uji.apps.hor.model.Calendario;
import es.uji.commons.rest.auth.Role;

@Service
public class CalendariosService
{
    private final CalendariosDAO calendariosDAO;

    @Autowired
    public CalendariosService(CalendariosDAO calendariosDAO)
    {
        this.calendariosDAO = calendariosDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<Calendario> getCalendarios(Long connectedUserId)
    {
        return calendariosDAO.getCalendarios();
    }
}
