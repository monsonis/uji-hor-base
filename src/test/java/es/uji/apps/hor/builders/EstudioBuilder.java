package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.model.Estudio;

public class EstudioBuilder
{
    private Estudio estudio;
    private EstudiosDAO estudioDAO;

    public EstudioBuilder(EstudiosDAO estudioDAO)
    {
        this.estudioDAO = estudioDAO;
        estudio = new Estudio();
    }

    public EstudioBuilder()
    {
        this(null);
    }

    public EstudioBuilder withId(Long id)
    {
        estudio.setId(id);
        return this;
    }

    public EstudioBuilder withNombre(String nombre)
    {
        estudio.setNombre(nombre);
        return this;
    }

    public EstudioBuilder withTipoEstudioId(String tipoEstudioId)
    {
        estudio.setTipoEstudioId(tipoEstudioId);
        return this;
    }

    public EstudioBuilder withTipoEstudio(String tipoEstudio)
    {
        estudio.setTipoEstudio(tipoEstudio);
        return this;
    }

    public Estudio getEstudio()
    {
        return estudio;
    }

    public Estudio build()
    {
        if (estudioDAO != null)
        {
            estudio = estudioDAO.insert(estudio);
        }

        return estudio;
    }
}
