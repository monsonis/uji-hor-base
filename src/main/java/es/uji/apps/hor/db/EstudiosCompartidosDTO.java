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
 * The persistent class for the HOR_ESTUDIOS_COMPARTIDOS database table.
 * 
 */
@Entity
@Table(name="HOR_ESTUDIOS_COMPARTIDOS")
@SuppressWarnings("serial")
public class EstudiosCompartidosDTO implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

    //bi-directional many-to-one association to EstudioDTO
    @ManyToOne
    @JoinColumn(name="ESTUDIO_ID")
    private EstudioDTO estudio;

    //bi-directional many-to-one association to EstudioDTO
    @ManyToOne
    @JoinColumn(name="ESTUDIO_ID_COMPARTIDO")
    private EstudioDTO estudioCompartido;

	public EstudiosCompartidosDTO() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public EstudioDTO getEstudio()
    {
        return estudio;
    }

    public void setEstudio(EstudioDTO estudio)
    {
        this.estudio = estudio;
    }

    public EstudioDTO getEstudioCompartido()
    {
        return estudioCompartido;
    }

    public void setEstudioCompartido(EstudioDTO estudioCompartido)
    {
        this.estudioCompartido = estudioCompartido;
    }

}