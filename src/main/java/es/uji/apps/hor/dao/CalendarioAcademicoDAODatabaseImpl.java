package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static final long DIA_SEMANA_DOMINGO = 7L;
    private static final long DIA_SEMANA_SABADO = 6L;
    private static final String TIPO_DIA_LECTIVO = "L";

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

    @Override
    public List<CalendarioAcademico> getCalendarioAcademicoNoLectivos(Date fechaInicio,
            Date fechaFin)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QCalendarioDTO qCalendarioDTO = QCalendarioDTO.calendarioDTO;

        query.from(qCalendarioDTO).where(
                qCalendarioDTO.tipoDia
                        .ne(TIPO_DIA_LECTIVO)
                        .and(qCalendarioDTO.diaSemanaId
                                .notIn(DIA_SEMANA_SABADO, DIA_SEMANA_DOMINGO))
                        .and(qCalendarioDTO.fecha.between(fechaInicio, fechaFin)));

        List<CalendarioAcademico> listaCalendarioAcademico = new ArrayList<CalendarioAcademico>();
        for (CalendarioDTO calendario : query.list(qCalendarioDTO))
        {
            listaCalendarioAcademico.add(creaCalendarioAcademicoDesdeCalendarioDTO(calendario));
        }

        return listaCalendarioAcademico;
    }

    private CalendarioAcademico creaCalendarioAcademicoDesdeCalendarioDTO(CalendarioDTO calendario)
    {
        CalendarioAcademico calendarioAcademico = new CalendarioAcademico();
        calendarioAcademico.setAnyo(calendario.getAño());
        calendarioAcademico.setDia(calendario.getDia());
        calendarioAcademico.setDiaSemana(calendario.getDiaSemana());
        calendarioAcademico.setDiaSemanaId(calendario.getDiaSemanaId());
        calendarioAcademico.setFecha(calendario.getFecha());
        calendarioAcademico.setId(calendario.getId());
        calendarioAcademico.setMes(calendario.getMes());
        calendarioAcademico.setTipoDia(calendario.getTipoDia());
        calendarioAcademico.setVacaciones(calendario.getVacaciones());

        return calendarioAcademico;
    }
}
