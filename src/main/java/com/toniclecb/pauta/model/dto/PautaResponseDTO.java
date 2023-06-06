package com.toniclecb.pauta.model.dto;

import com.toniclecb.pauta.model.Pauta;

import io.swagger.v3.oas.annotations.media.Schema;

public class PautaResponseDTO {

	@Schema(description = "Código identificador da pauta", example = "2")
	private Long id;
	@Schema(description = "Nome da pauta", example = "Adicionar pizza como opção no café da manhã da empresa")
	private String nome;
	@Schema(description = "Descritiva da pauta", example = "Além de ser uma opção deliciosa, a pizza no café da manhã traz benefícios surpreendentes. Estudos mostram que a variedade de ingredientes, como queijos, vegetais e proteínas, fornece os nutrientes necessários para uma manhã energética e produtiva.")
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
