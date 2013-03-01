package es.uji.apps.hor.dao;

import java.util.Date;
import java.util.List;

import es.uji.apps.hor.model.CalendarioAcademico;
import es.uji.commons.db.BaseDAO;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public interface CalendarioAcademicoDAO extends BaseDAO
{
    public CalendarioAcademico getCalendarioAcademicoByFecha(Date fecha)
            throws RegistroNoEncontradoException;

    public List<CalendarioAcademico> getCalendarioAcademicoNoLectivos(Date fechaInicio, Date fechaFin);
}
