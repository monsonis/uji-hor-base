package es.uji.apps.hor.builders;

import java.util.List;

import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Usuario;

public class UsuarioBuilder
{
    private Usuario usuario;

    public UsuarioBuilder()
    {
        usuario = new Usuario();
    }

    public UsuarioBuilder withId(Long id)
    {
        usuario.setId(id);
        return this;
    }

    public UsuarioBuilder withNombre(String nombre)
    {
        usuario.setNombre(nombre);
        return this;
    }

    public UsuarioBuilder withCentro(Centro centro)
    {
        usuario.setCentro(centro);
        return this;
    }

    public UsuarioBuilder withEstudios(List<Estudio> estudios)
    {
        usuario.setEstudios(estudios);
        return this;
    }

    public UsuarioBuilder withEstudio(Estudio estudio)
    {
        usuario.getEstudios().add(estudio);
        return this;
    }

    public Usuario build()
    {
        return usuario;
    }

}
