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

    @Temporal( TemporalType.DATE)
	private Date dia;

    @Temporal( TemporalType.DATE)
	@Column(name="HORA_FIN")
	private Date horaFin;

    @Temporal( TemporalType.DATE)
	@Column(name="HORA_INICIO")
	private Date horaInicio;

	//bi-directional many-to-one association to ItemDTO
    @ManyToOne
	@JoinColumn(name="ITEM_ID")
	private ItemDTO horItem;

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

	public Date getDia() {
		return this.dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Date getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public Date getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public ItemDTO getHorItem() {
		return this.horItem;
	}

	public void setHorItem(ItemDTO horItem) {
		this.horItem = horItem;
	}
	
}