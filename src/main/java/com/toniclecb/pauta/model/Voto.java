package com.toniclecb.pauta.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"id_sessao", "id_associado"})
})
public class Voto implements Serializable {
	private static final long serialVersionUID = 3641101252355921103L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name ="id_sessao", referencedColumnName= "id")
	private Sessao sessao;

	@OneToOne
	@JoinColumn(name ="id_associado", referencedColumnName= "id")
	private Associado associado;
	
	@Column
	private boolean voto;

	@Column
	private Date dataVoto;
	
	public Voto() {
	}

	public Voto(Sessao sessao, Associado associado, boolean voto, Date dataVoto) {
		super();
		this.sessao = sessao;
		this.associado = associado;
		this.voto = voto;
		this.dataVoto = dataVoto;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	public boolean isVoto() {
		return voto;
	}

	public void setVoto(boolean voto) {
		this.voto = voto;
	}

	public Date getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(Date dataVoto) {
		this.dataVoto = dataVoto;
	}
}