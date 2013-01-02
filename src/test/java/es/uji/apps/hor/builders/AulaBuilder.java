package es.uji.apps.hor.builders;

import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.Centro;

public class AulaBuilder
{
    private Aula aula;
    private AulaDAO aulaDAO;
    
    public AulaBuilder(AulaDAO aulaDAO)
    {
        this.aulaDAO = aulaDAO;
        aula = new Aula();
    }
    
    public AulaBuilder()
    {
        this(null);
    }
    
    public AulaBuilder withNombre(String nombre)
    {
        aula.setNombre(nombre);
        return this;
    }
    
    public AulaBuilder withCentro(Centro centro)
    {
        aula.setCentro(centro);
        return this;
    }
    
    public AulaBuilder withTipo(String tipo)
    {
        aula.setTipo(tipo);
        return this;
    }
    
    public AulaBuilder withPlazas(Long plazas)
    {
        aula.setPlazas(plazas);
        return this;
    }
    
    public AulaBuilder withCodigo(String codigo)
    {
        aula.setCodigo(codigo);
        return this;
    }
    
    public AulaBuilder withArea(String area)
    {
        aula.setArea(area);
        return this;
    }
    
    public AulaBuilder withEdificio(String edificio)
    {
        aula.setEdificio(edificio);
        return this;
    }
    
    public AulaBuilder withPlanta(String planta)
    {
        aula.setPlanta(planta);
        return this;
    }
    
    public Aula build()
    {
        if (aulaDAO != null)
        {
            // Insertar aula
        }
        
        return aula;
    }
    
}
