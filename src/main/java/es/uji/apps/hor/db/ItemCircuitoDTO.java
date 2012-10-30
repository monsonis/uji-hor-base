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
 * The persistent class for the HOR_ITEMS_CIRCUITOS database table.
 * 
 */
@Entity
@Table(name="HOR_ITEMS_CIRCUITOS")
public class ItemCircuitoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Long plazas;

	//bi-directional many-to-one association to CircuitoDTO
    @ManyToOne
	@JoinColumn(name="CIRCUITO_ID")
	private CircuitoDTO circuito;

	//bi-directional many-to-one association to ItemDTO
    @ManyToOne
	@JoinColumn(name="ITEM_ID")
	private ItemDTO item;

    public ItemCircuitoDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlazas() {
		return this.plazas;
	}

	public void setPlazas(Long plazas) {
		this.plazas = plazas;
	}

	public CircuitoDTO getCircuito() {
		return this.circuito;
	}

	public void setCircuito(CircuitoDTO circuito) {
		this.circuito = circuito;
	}
	
	public ItemDTO getItem() {
		return this.item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}
	
}