package es.uji.apps.hor.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the HOR_GRUPOS database table.
 * 
 */
@Entity
@Table(name="HOR_GRUPOS")
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private BigDecimal especial;

	//bi-directional many-to-one association to Circuito
	@OneToMany(mappedBy="horGrupo")
	private Set<Circuito> horCircuitos;

	//bi-directional many-to-one association to GrupoAsignatura
	@OneToMany(mappedBy="horGrupo")
	private Set<GrupoAsignatura> horGruposAsignaturas;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="horGrupo")
	private Set<Item> horItems;

    public Grupo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getEspecial() {
		return this.especial;
	}

	public void setEspecial(BigDecimal especial) {
		this.especial = especial;
	}

	public Set<Circuito> getHorCircuitos() {
		return this.horCircuitos;
	}

	public void setHorCircuitos(Set<Circuito> horCircuitos) {
		this.horCircuitos = horCircuitos;
	}
	
	public Set<GrupoAsignatura> getHorGruposAsignaturas() {
		return this.horGruposAsignaturas;
	}

	public void setHorGruposAsignaturas(Set<GrupoAsignatura> horGruposAsignaturas) {
		this.horGruposAsignaturas = horGruposAsignaturas;
	}
	
	public Set<Item> getHorItems() {
		return this.horItems;
	}

	public void setHorItems(Set<Item> horItems) {
		this.horItems = horItems;
	}
	
}