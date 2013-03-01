package es.uji.apps.hor.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.apps.hor.dao.CalendarioAcademicoDAO;
import es.uji.apps.hor.model.CalendarioAcademico;
import es.uji.commons.rest.auth.Role;

@Service
public class CalendarioAcademicoService
{
    private SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
    private final CalendarioAcademicoDAO calendarioAcademicoDAO;

    @Autowired
    public CalendarioAcademicoService(CalendarioAcademicoDAO calendarioAcademicoDAO)
    {
        this.calendarioAcademicoDAO = calendarioAcademicoDAO;
    }

    @Role({ "ADMIN", "USUARIO" })
    public List<CalendarioAcademico> getCalendarioAcademicoFestivos(String fechaInicio,
            String fechaFin, Long connectedUserId) throws ParseException
    {
        return calendarioAcademicoDAO.getCalendarioAcademicoNoLectivos(
                formateadorFecha.parse(fechaInicio), formateadorFecha.parse(fechaFin));
    }
}
