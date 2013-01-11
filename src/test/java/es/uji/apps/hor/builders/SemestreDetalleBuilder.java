package es.uji.apps.hor.builders;

import java.util.Date;

import es.uji.apps.hor.dao.SemestresDetalleDAO;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.apps.hor.model.TipoEstudio;

public class SemestreDetalleBuilder
{
    private SemestreDetalle semestreDetalle;
    private SemestresDetalleDAO semestresDetalleDAO;

    public SemestreDetalleBuilder(SemestresDetalleDAO semestresDetalleDAO)
    {
        this.semestresDetalleDAO = semestresDetalleDAO;
        semestreDetalle = new SemestreDetalle();
    }

    public SemestreDetalleBuilder()
    {
        this(null);
    }

    public SemestreDetalleBuilder withSemestre(Semestre semestre)
    {
        semestreDetalle.setSemestre(semestre);
        return this;
    }

    public SemestreDetalleBuilder withTipoEstudio(TipoEstudio tipoEstudio)
    {
        semestreDetalle.setTipoEstudio(tipoEstudio);
        return this;
    }

    public SemestreDetalleBuilder withFechaInicio(Date fechaInicio)
    {
        semestreDetalle.setFechaInicio(fechaInicio);
        return this;
    }

    public SemestreDetalleBuilder withFechaFin(Date fechaFin)
    {
        semestreDetalle.setFechaFin(fechaFin);
        return this;
    }

    public SemestreDetalleBuilder withFechaExamenesInicio(Date fechaExamenesInicio)
    {
        semestreDetalle.setFechaExamenesInicio(fechaExamenesInicio);
        return this;
    }

    public SemestreDetalleBuilder withFechaExamenesFin(Date fechaExamenesFin)
    {
        semestreDetalle.setFechaExamenesFin(fechaExamenesFin);
        return this;
    }

    public SemestreDetalleBuilder withNumeroSemanas(Long numeroSemanas)
    {
        semestreDetalle.setNumeroSemanas(numeroSemanas);
        return this;
    }
    
    /*public SemestreDetalleBuilder withCursoAcademico()
    {
        semestreDetalle.se
    }*/

    public SemestreDetalle build()
    {
        if (semestresDetalleDAO != null)
        {
            semestreDetalle = semestresDetalleDAO.insert(semestreDetalle);
        }
        return semestreDetalle;
    }
}
