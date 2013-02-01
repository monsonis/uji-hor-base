package es.uji.apps.hor.model;



public class PermisoExtra
{
    private Long id;
    private Persona persona;
    private Estudio estudio;
    private Departamento departamento;
    private Cargo cargo;

    public PermisoExtra() {
        
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Cargo getCargo()
    {
        return cargo;
    }

    public void setCargo(Cargo cargo)
    {
        this.cargo = cargo;
    }

    public Persona getPersona()
    {
        return persona;
    }

    public void setPersona(Persona persona)
    {
        this.persona = persona;
    }

    public Estudio getEstudio()
    {
        return estudio;
    }

    public void setEstudio(Estudio estudio)
    {
        this.estudio = estudio;
    }

    public Departamento getDepartamento()
    {
        return departamento;
    }

    public void setDepartamento(Departamento departamento)
    {
        this.departamento = departamento;
    }
}
