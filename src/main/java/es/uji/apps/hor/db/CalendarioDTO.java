package es.uji.apps.hor.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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

	private Long año;

	private Long dia;

	@Column(name="DIA_SEMANA")
	private String diaSemana;

	@Column(name="DIA_SEMANA_ID")
	private Long diaSemanaId;

    @Temporal( TemporalType.DATE)
	private Date fecha;

	private Long mes;

	@Column(name="TIPO_DIA")
	private String tipoDia;
	
	private Long vacaciones;

    public CalendarioDTO() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAño() {
		return this.año;
	}

	public void setAño(Long año) {
		this.año = año;
	}

	public Long getDia() {
		return this.dia;
	}

	public void setDia(Long dia) {
		this.dia = dia;
	}

	public String getDiaSemana() {
		return this.diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Long getDiaSemanaId() {
		return this.diaSemanaId;
	}

	public void setDiaSemanaId(Long diaSemanaId) {
		this.diaSemanaId = diaSemanaId;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getMes() {
		return this.mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public String getTipoDia() {
		return this.tipoDia;
	}

	public void setTipoDia(String tipoDia) {
		this.tipoDia = tipoDia;
	}

    public Long getVacaciones()
    {
        return vacaciones;
    }

    public void setVacaciones(Long vacaciones)
    {
        this.vacaciones = vacaciones;
    }

}