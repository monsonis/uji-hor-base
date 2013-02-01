package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.DepartamentoDAO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Departamento;


public class DepartamentoBuilder
{
    private Departamento departamento;
    private DepartamentoDAO departamentoDAO;

    public DepartamentoBuilder(DepartamentoDAO departamentoDAO)
    {
        this.departamentoDAO = departamentoDAO;
        departamento = new Departamento();
    }

    public DepartamentoBuilder()
    {
        this(null);
    }

    public DepartamentoBuilder withNombre(String nombre)
    {
        departamento.setNombre(nombre);
        return this;
    }

    public DepartamentoBuilder withId(Long id)
    {
        departamento.setId(id);
        return this;
    }

    public DepartamentoBuilder withCentro(Centro centro)
    {
        departamento.setCentro(centro);
        return this;
    }

    public Departamento build()
    {
        if (departamentoDAO != null)
        {
            departamento = departamentoDAO.insertDepartamento(departamento);
        }

        return departamento;
    }
}
