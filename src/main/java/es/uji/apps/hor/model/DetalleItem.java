package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the HOR_ITEMS_DETALLE database table.
 * 
 */
@Entity
@Table(name="HOR_ITEMS_DETALLE")
public class DetalleItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String descripcion;

    @Temporal( TemporalType.DATE)
	private Date dia;

	private BigDecimal duracion;

    @Temporal( TemporalType.DATE)
	private Date hora;

	//bi-directional many-to-one association to Item
    @ManyToOne
	@JoinColumn(name="ITEM_ID")
	private Item horItem;

    public DetalleItem() {
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

	public Date getDia() {
		return this.dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public BigDecimal getDuracion() {
		return this.duracion;
	}

	public void setDuracion(BigDecimal duracion) {
		this.duracion = duracion;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Item getHorItem() {
		return this.horItem;
	}

	public void setHorItem(Item horItem) {
		this.horItem = horItem;
	}
	
}