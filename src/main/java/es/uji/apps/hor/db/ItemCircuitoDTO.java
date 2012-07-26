package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


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
	private long id;

	private BigDecimal plazas;

	//bi-directional many-to-one association to CircuitoDTO
    @ManyToOne
	@JoinColumn(name="CIRCUITO_ID")
	private CircuitoDTO horCircuito;

	//bi-directional many-to-one association to ItemDTO
    @ManyToOne
	@JoinColumn(name="ITEM_ID")
	private ItemDTO horItem;

    public ItemCircuitoDTO() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPlazas() {
		return this.plazas;
	}

	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}

	public CircuitoDTO getHorCircuito() {
		return this.horCircuito;
	}

	public void setHorCircuito(CircuitoDTO horCircuito) {
		this.horCircuito = horCircuito;
	}
	
	public ItemDTO getHorItem() {
		return this.horItem;
	}

	public void setHorItem(ItemDTO horItem) {
		this.horItem = horItem;
	}
	
}