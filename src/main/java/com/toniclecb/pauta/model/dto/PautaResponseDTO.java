package com.toniclecb.pauta.model.dto;

import com.toniclecb.pauta.model.Pauta;

public class PautaResponseDTO {

	private Long id;
	private String nome;
	private String descricao;
	
	public PautaResponseDTO() {
	}

	public PautaResponseDTO(Pauta pauta) {
		this.id = pauta.getId();
		this.nome = pauta.getNome();
		this.descricao = pauta.getDescricao();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
