package es.uji.apps.hor.model;

public class Asignatura
{
    private String id;
    private String nombre;
    private Estudio estudio;
    private Long cursoId;
    private String caracterId;
    private String caracter;
    private Boolean comun;
    private String comunes;
    private Long porcentajeComun;
    private String tipoAsignaturaId;
    private String tipoAsignatura;

    public Asignatura()
    {
    }

    public Asignatura(String id)
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

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Estudio getEstudio()
    {
        return estudio;
    }

    public void setEstudio(Estudio estudio)
    {
        this.estudio = estudio;
    }

    public Long getCursoId()
    {
        return cursoId;
    }

    public void setCursoId(Long cursoId)
    {
        this.cursoId = cursoId;
    }

    public String getCaracterId()
    {
        return caracterId;
    }

    public void setCaracterId(String caracterId)
    {
        this.caracterId = caracterId;
    }

    public String getCaracter()
    {
        return caracter;
    }

    public void setCaracter(String caracter)
    {
        this.caracter = caracter;
    }

    public Boolean getComun()
    {
        return comun;
    }

    public void setComun(Boolean comun)
    {
        this.comun = comun;
    }

    public String getComunes()
    {
        return comunes;
    }

    public void setComunes(String comunes)
    {
        this.comunes = comunes;
    }

    public Long getPorcentajeComun()
    {
        return porcentajeComun;
    }

    public void setPorcentajeComun(Long porcentajeComun)
    {
        this.porcentajeComun = porcentajeComun;
    }

    public String getTipoAsignaturaId()
    {
        return tipoAsignaturaId;
    }

    public void setTipoAsignaturaId(String tipoAsignaturaId)
    {
        this.tipoAsignaturaId = tipoAsignaturaId;
    }

    public String getTipoAsignatura()
    {
        return tipoAsignatura;
    }

    public void setTipoAsignatura(String tipoAsignatura)
    {
        this.tipoAsignatura = tipoAsignatura;
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Asignatura))
        {
            return false;
        }

        Asignatura otraAsignatura = (Asignatura) obj;

        return this.id.equals(otraAsignatura.getId());
    }

}