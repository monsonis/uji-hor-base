package es.uji.apps.hor.dao;

import java.util.List;

import es.uji.apps.hor.model.Calendario;
import es.uji.commons.db.BaseDAO;

public interface CalendariosDAO extends BaseDAO
{
    public List<Calendario> getCalendarios();
}
