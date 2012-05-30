package es.uji.apps.hor.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the HOR_EXT_CALENDARIO database table.
 * 
 */
@Entity
@Table(name="HOR_EXT_CALENDARIO")
public class CalendarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private BigDecimal año;

	private BigDecimal dia;

	@Column(name="DIA_SEMANA")
	private String diaSemana;

	@Column(name="DIA_SEMANA_ID")
	private BigDecimal diaSemanaId;

    @Temporal( TemporalType.DATE)
	private Date fecha;

	private BigDecimal mes;

	@Column(name="TIPO_DIA")
	private String tipoDia;

    public CalendarioDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAño() {
		return this.año;
	}

	public void setAño(BigDecimal año) {
		this.año = año;
	}

	public BigDecimal getDia() {
		return this.dia;
	}

	public void setDia(BigDecimal dia) {
		this.dia = dia;
	}

	public String getDiaSemana() {
		return this.diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public BigDecimal getDiaSemanaId() {
		return this.diaSemanaId;
	}

	public void setDiaSemanaId(BigDecimal diaSemanaId) {
		this.diaSemanaId = diaSemanaId;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getMes() {
		return this.mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public String getTipoDia() {
		return this.tipoDia;
	}

	public void setTipoDia(String tipoDia) {
		this.tipoDia = tipoDia;
	}

}