package com.toniclecb.pauta.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AssociadoResponseDTO {

	@Schema(description = "Código identificador do associado", example = "2")
	private Long id;
	@Schema(description = "Nome do associado", example = "Maria da Silva")
	private String nome;
	@Schema(description = "CPF do associado", example = "36599334067")
	private String cpf;
	
	public AssociadoResponseDTO() {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
