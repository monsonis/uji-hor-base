package es.uji.apps.hor.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.CalendarioDTO;
import es.uji.apps.hor.db.QCalendarioDTO;
import es.uji.apps.hor.model.CalendarioAcademico;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

@Repository
public class CalendarioAcademicoDAODatabaseImpl extends BaseDAODatabaseImpl implements
        CalendarioAcademicoDAO
{
    @Override
    public CalendarioAcademico getCalendarioAcademicoByFecha(Date fecha)
            throws RegistroNoEncontradoException
    {
        JPAQuery query = new JPAQuery(entityManager);
        QCalendarioDTO qCalendario = QCalendarioDTO.calendarioDTO;

        CalendarioDTO calendarioDTO = query.from(qCalendario).where(qCalendario.fecha.eq(fecha))
                .uniqueResult(qCalendario);

        if (calendarioDTO == null)
        {
            throw new RegistroNoEncontradoException();
        }

        return creaCalendarioAcademicoFrom(calendarioDTO);
    }

    private CalendarioAcademico creaCalendarioAcademicoFrom(CalendarioDTO calendarioDTO)
    {
        CalendarioAcademico calendario = new CalendarioAcademico();
        calendario.setId(calendarioDTO.getId());
        calendario.setAnyo(calendarioDTO.getAño());
        calendario.setDia(calendarioDTO.getDia());
        calendario.setDiaSemana(calendarioDTO.getDiaSemana());
        calendario.setDiaSemanaId(calendarioDTO.getDiaSemanaId());
        calendario.setFecha(calendarioDTO.getFecha());
        calendario.setMes(calendarioDTO.getMes());
        calendario.setTipoDia(calendarioDTO.getTipoDia());
        calendario.setVacaciones(calendarioDTO.getVacaciones());

        return calendario;
    }
}
