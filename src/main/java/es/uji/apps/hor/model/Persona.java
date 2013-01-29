package es.uji.apps.hor.model;

import java.util.ArrayList;
import java.util.List;

import javassist.expr.NewArray;

import es.uji.commons.sso.exceptions.UnauthorizedUserException;



public class Persona
{
    private Long id;
    private String nombre;
    private String email;
    private String actividadId;
    private Departamento departamento;
    private Centro centroAutorizado;
    private List<Estudio> estudiosAutorizados = new ArrayList<Estudio>();
    private List<Cargo> cargos = new ArrayList<Cargo>();

    public Persona() {
        
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

    public String getActividadId()
    {
        return actividadId;
    }

    public void setActividadId(String actividadId)
    {
        this.actividadId = actividadId;
    }

    public Departamento getDepartamento()
    {
        return departamento;
    }

    public void setDepartamento(Departamento departamento)
    {
        this.departamento = departamento;
    }
    
    public List<Estudio> getEstudiosAutorizados()
    {
        return estudiosAutorizados;
    }

    public void setEstudiosAutorizados(List<Estudio> estudios)
    {
        this.estudiosAutorizados = estudios;
    }

    public boolean puedeAccederACentro(Centro centro)
    {
        return puedeAccederACentro(centro.getId());
    }

    public boolean puedeAccederACentro(Long centroId)
    {
        Centro centroUsuario = getCentroAutorizado();
        return centroUsuario != null && centroId.equals(centroUsuario.getId());

    }

    public boolean puedeAccederAEstudio(Estudio estudio)
    {
        return puedeAccederAEstudio(estudio.getId());
    }

    public boolean puedeAccederAEstudio(Long estudioId)
    {
        for (Estudio estudioUsuario : getEstudiosAutorizados())
        {
            if (estudioId.equals(estudioUsuario.getId()))
            {
                return true;
            }
        }
        return false;
    }

    public Centro getCentroAutorizado()
    {
        return centroAutorizado;
    }

    public void setCentroAutorizado(Centro centro)
    {
        this.centroAutorizado = centro;
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public List<Cargo> getCargos()
    {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos)
    {
        this.cargos = cargos;
    }

}
