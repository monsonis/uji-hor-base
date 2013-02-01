package es.uji.apps.hor.model;

import java.util.List;

public class PlantaEdificio
{
    private Edificio edificio;
    private String nombre;
    private String valor;
    private List<Aula> aulas;

    public Edificio getEdificio()
    {
        return edificio;
    }

    public void setEdificio(Edificio edificio)
    {
        this.edificio = edificio;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
    }

    public List<Aula> getAulas()
    {
        return aulas;
    }

    public void setAulas(List<Aula> aulas)
    {
        this.aulas = aulas;
    }
}
