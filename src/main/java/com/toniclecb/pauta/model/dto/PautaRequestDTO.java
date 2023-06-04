package com.toniclecb.pauta.model.dto;

import com.toniclecb.pauta.model.Pauta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class PautaRequestDTO {

	@NotBlank(message = "Nome é obrigatório!")
	@Schema(description = "Nome da pauta", example = "Adicionar pizza como opção no café da manhã da empresa")
	private String nome;

	@Schema(description = "Descritiva da pauta", example = "Além de ser uma opção deliciosa, a pizza no café da manhã traz benefícios surpreendentes. Estudos mostram que a variedade de ingredientes, como queijos, vegetais e proteínas, fornece os nutrientes necessários para uma manhã energética e produtiva.")
	private String descricao;
	
	public PautaRequestDTO() {
	}
	
	public PautaRequestDTO(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}

	public Pauta toEntity() {
		return new Pauta(null, this.nome, this.descricao);
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
