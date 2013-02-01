package es.uji.apps.hor.builders;

import java.util.Date;

import es.uji.apps.hor.dao.GrupoAsignaturaDAO;
import es.uji.apps.hor.model.GrupoAsignatura;

public class GrupoAsignaturaBuilder
{
    private GrupoAsignatura grupoAsignatura;
    private GrupoAsignaturaDAO grupoAsignaturaDAO;
    
    public GrupoAsignaturaBuilder(GrupoAsignaturaDAO grupoAsignaturaDAO)
    {
        this.grupoAsignaturaDAO = grupoAsignaturaDAO;
        this.grupoAsignatura = new GrupoAsignatura();
    }
    
    public GrupoAsignaturaBuilder()
    {
        this(null);
    }
    
    public GrupoAsignaturaBuilder withTitulo(String titulo)
    {
        grupoAsignatura.setTitulo(titulo);
        return this;
    }
    
    public GrupoAsignaturaBuilder withInicio(Date inicio)
    {
        grupoAsignatura.setInicio(inicio);
        return this;
    }

    public GrupoAsignaturaBuilder withFin(Date fin)
    {
        grupoAsignatura.setFin(fin);
        return this;
    }
    
    public GrupoAsignatura build()
    {
        if (grupoAsignaturaDAO != null)
        {
            // Insertaríamos grupo asignatura
        }
        
        return grupoAsignatura;
    }
}
