package es.uji.apps.hor.model;

import java.util.ArrayList;
import java.util.List;

import es.uji.commons.sso.exceptions.UnauthorizedUserException;

public class Usuario
{
    private Long id;
    private String nombre;
    private Centro centro = null;
    private List<Estudio> estudios = new ArrayList<Estudio>();

    public Usuario()
    {

    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public List<Estudio> getEstudios()
    {
        return estudios;
    }

    public void setEstudios(List<Estudio> estudios)
    {
        this.estudios = estudios;
    }

    public boolean puedeAccederACentro(Centro centro)
    {
        return puedeAccederACentro(centro.getId());
    }

    public boolean puedeAccederACentro(Long centroId)
    {
        Centro centroUsuario = getCentro();
        return centroUsuario != null && centroId.equals(centroUsuario.getId());

    }

    public boolean puedeAccederAEstudio(Estudio estudio)
    {
        return puedeAccederAEstudio(estudio.getId());
    }

    public boolean puedeAccederAEstudio(Long estudioId)
    {
        for (Estudio estudioUsuario : getEstudios())
        {
            if (estudioId.equals(estudioUsuario.getId()))
            {
                return true;
            }
        }
        return false;
    }

    public Centro getCentro()
    {
        return centro;
    }

    public void setCentro(Centro centro)
    {
        this.centro = centro;
    }

    public void compruebaAccesoACentro(Centro centro) throws UnauthorizedUserException
    {
        compruebaAccesoACentro(centro.getId());
    }

    public void compruebaAccesoACentro(Long centroId) throws UnauthorizedUserException
    {
        if (!puedeAccederACentro(centroId))
        {
            throw new UnauthorizedUserException();
        }

    }

    public void compruebaAccesoAEstudio(Estudio estudio) throws UnauthorizedUserException
    {
        compruebaAccesoAEstudio(estudio.getId());
    }

    public void compruebaAccesoAEstudio(Long estudioId) throws UnauthorizedUserException
    {
        if (!puedeAccederAEstudio(estudioId))
        {
            throw new UnauthorizedUserException();
        }

    }

    public void compruebaAccesoAEvento(Evento evento) throws UnauthorizedUserException
    {
        if (!puedeAccederAEvento(evento))
        {
            throw new UnauthorizedUserException();
        }

    }

    public boolean puedeAccederAEvento(Evento evento)
    {
        for (Asignatura asignatura : evento.getAsignaturas())
        {
            if (puedeAccederAEstudio(asignatura.getEstudio()))
            {
                return true;
            }
        }

        return false;
    }
}
