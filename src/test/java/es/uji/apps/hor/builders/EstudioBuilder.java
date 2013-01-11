package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.EstudiosDAO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.TipoEstudio;

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

    public EstudioBuilder withTipoEstudio(TipoEstudio tipoEstudio)
    {
        estudio.setTipoEstudio(tipoEstudio);
        return this;
    }

    public EstudioBuilder withCentro(Centro centro)
    {
        estudio.setCentro(centro);
        return this;
    }

    public EstudioBuilder withOficial(Boolean oficial)
    {
        estudio.setOficial(oficial);
        return this;
    }

    public EstudioBuilder withNumeroCursos(Integer numeroCursos)
    {
        estudio.setNumeroCursos(numeroCursos);
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
