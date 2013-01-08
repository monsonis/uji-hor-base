package es.uji.apps.hor.model;

import java.util.List;

public class TipoAula
{
    private String nombre;
    private List<Aula> aulas;
    private Edificio edificio;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public List<Aula> getAulas()
    {
        return aulas;
    }

    public void setAulas(List<Aula> aulas)
    {
        this.aulas = aulas;
    }

    public Edificio getEdificio()
    {
        return edificio;
    }

    public void setEdificio(Edificio edificio)
    {
        this.edificio = edificio;
    }
}