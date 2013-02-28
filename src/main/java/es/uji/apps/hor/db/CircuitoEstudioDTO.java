package es.uji.apps.hor.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the HOR_CIRCUITOS_ESTUDIOS database table.
 * 
 */
@Entity
@Table(name="HOR_CIRCUITOS_ESTUDIOS")
@SuppressWarnings("serial")
public class CircuitoEstudioDTO implements Serializable {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

    @ManyToOne
    @JoinColumn(name = "CIRCUITO_ID")
	private CircuitoDTO circuito;

    @ManyToOne
    @JoinColumn(name = "ESTUDIO_ID")
	private EstudioDTO estudio;

	public CircuitoEstudioDTO() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public CircuitoDTO getCircuito()
    {
        return circuito;
    }

    public void setCircuito(CircuitoDTO circuito)
    {
        this.circuito = circuito;
    }

    public EstudioDTO getEstudio()
    {
        return estudio;
    }

    public void setEstudio(EstudioDTO estudio)
    {
        this.estudio = estudio;
    }

}