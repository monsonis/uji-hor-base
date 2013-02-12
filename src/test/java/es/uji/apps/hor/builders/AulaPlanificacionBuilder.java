package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Semestre;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;

public class AulaPlanificacionBuilder
{
    private AulaPlanificacion aulaPlanificacion;
    private AulaDAO aulaDAO;

    public AulaPlanificacionBuilder(AulaDAO aulaDAO)
    {
        this.aulaDAO = aulaDAO;
        aulaPlanificacion = new AulaPlanificacion();
    }

    public AulaPlanificacionBuilder()
    {
        this(null);
    }

    public AulaPlanificacionBuilder withAula(Aula aula)
    {
        aulaPlanificacion.setAula(aula);
        return this;
    }

    public AulaPlanificacionBuilder withEstudio(Estudio estudio)
    {
        aulaPlanificacion.setEstudio(estudio);
        return this;
    }

    public AulaPlanificacionBuilder withSemestre(Semestre semestre)
    {
        aulaPlanificacion.setSemestre(semestre);
        return this;
    }

    public AulaPlanificacion build() throws RegistroNoEncontradoException
    {
        if (aulaDAO != null)
        {
            aulaPlanificacion = aulaDAO.insertAulaPlanificacion(aulaPlanificacion);
        }

        return aulaPlanificacion;
    }
}
