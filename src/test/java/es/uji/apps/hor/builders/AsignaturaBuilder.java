package es.uji.apps.hor.builders;

import es.uji.apps.hor.model.Asignatura;
import es.uji.apps.hor.model.Estudio;

public class AsignaturaBuilder
{
    private Asignatura asignatura;

    public AsignaturaBuilder()
    {
        asignatura = new Asignatura();
    }

    public AsignaturaBuilder withId(String id)
    {
        asignatura.setId(id);
        return this;
    }

    public AsignaturaBuilder withNombre(String nombre)
    {
        asignatura.setNombre(nombre);
        return this;
    }

    public AsignaturaBuilder withEstudio(Estudio estudio)
    {
        asignatura.setEstudio(estudio);
        return this;
    }

    public AsignaturaBuilder withCursoId(Long cursoId)
    {
        asignatura.setCursoId(cursoId);
        return this;
    }

    public AsignaturaBuilder withCaracterId(String caracterId)
    {
        asignatura.setCaracterId(caracterId);
        return this;
    }

    public AsignaturaBuilder withCaracter(String caracter)
    {
        asignatura.setCaracter(caracter);
        return this;
    }

    public AsignaturaBuilder withComun(Boolean comun)
    {
        asignatura.setComun(comun);
        return this;
    }

    public AsignaturaBuilder withComunes(String comunes)
    {
        asignatura.setComunes(comunes);
        return this;
    }

    public AsignaturaBuilder withPorcentajeComun(Long porcentajeComun)
    {
        asignatura.setPorcentajeComun(porcentajeComun);
        return this;
    }

    public AsignaturaBuilder withTipoAsignaturaId(String tipoAsignaturaId)
    {
        asignatura.setTipoAsignaturaId(tipoAsignaturaId);
        return this;
    }

    public AsignaturaBuilder withTipoAsignatura(String tipoAsignatura)
    {
        asignatura.setTipoAsignatura(tipoAsignatura);
        return this;
    }

    public Asignatura build()
    {
        return asignatura;
    }

}
