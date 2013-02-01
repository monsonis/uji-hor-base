package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the HOR_ITEMS_DETALLE database table.
 * 
 */
@Entity
@Table(name="HOR_ITEMS_DETALLE")
public class ItemDetalleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String descripcion;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fin;

    @Temporal( TemporalType.TIMESTAMP)
	private Date inicio;

	//bi-directional many-to-one association to ItemDTO
    @ManyToOne
	@JoinColumn(name="ITEM_ID")
	private ItemDTO item;

    public ItemDetalleDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFin() {
		return this.fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public Date getInicio() {
		return this.inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public ItemDTO getItem() {
		return this.item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}
	
}