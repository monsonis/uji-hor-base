package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.model.AulaPlanificacion;

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

    public AulaPlanificacionBuilder withNombre(String nombre)
    {
        aulaPlanificacion.setNombre(nombre);
        return this;
    }

    public AulaPlanificacionBuilder withAulaId(Long aulaId)
    {
        aulaPlanificacion.setAulaId(aulaId);
        return this;
    }

    public AulaPlanificacionBuilder withEstudioId(Long estudioId)
    {
        aulaPlanificacion.setEstudioId(estudioId);
        return this;
    }

    public AulaPlanificacionBuilder withSemestreId(Long semestreId)
    {
        aulaPlanificacion.setSemestreId(semestreId);
        return this;
    }

    public AulaPlanificacionBuilder withEdificio(String edificio)
    {
        aulaPlanificacion.setEdificio(edificio);
        return this;
    }

    public AulaPlanificacionBuilder withTipo(String tipo)
    {
        aulaPlanificacion.setTipo(tipo);
        return this;
    }

    public AulaPlanificacionBuilder withPlanta(String planta)
    {
        aulaPlanificacion.setPlanta(planta);
        return this;
    }

    public AulaPlanificacionBuilder withCodigo(String codigo)
    {
        aulaPlanificacion.setCodigo(codigo);
        return this;
    }

    public AulaPlanificacion build()
    {
        if (aulaDAO != null)
        {
            aulaPlanificacion = aulaDAO.insertAulaPlanificacion(aulaPlanificacion);
        }

        return aulaPlanificacion;
    }
}
