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

@Entity
@Table
public class Sessao implements Serializable {
	private static final long serialVersionUID = -6464712698086179660L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name ="id_pauta", referencedColumnName= "id")
	private Pauta pauta;

	@Column
	private Date inicioVotacao;
	
	@Column
	private Date fimVotacao;
	
	public Sessao() {
	}
	
	public Sessao(Pauta pauta) {
		super();
		this.pauta = pauta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInicioVotacao() {
		return inicioVotacao;
	}

	public void setInicioVotacao(Date inicioVotacao) {
		this.inicioVotacao = inicioVotacao;
	}

	public Date getFimVotacao() {
		return fimVotacao;
	}

	public void setFimVotacao(Date fimVotacao) {
		this.fimVotacao = fimVotacao;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}
}
