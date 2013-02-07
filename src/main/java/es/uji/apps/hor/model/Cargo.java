package es.uji.apps.hor.model;

public class Cargo
{
    private Long id;
    private String nombre;

    public static final Long DIRECTOR_ESTUDIO = new Long(1);
    public static final Long COORDINADOR_CURSO = new Long(2);
    public static final Long DIRECTOR_CENTRO = new Long(3);
    public static final Long PAS_CENTRO = new Long(4);
    public static final Long PAS_DEPARTAMENTO = new Long(5);

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
