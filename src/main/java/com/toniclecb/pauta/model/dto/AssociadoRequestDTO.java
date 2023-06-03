package com.toniclecb.pauta.model.dto;

import com.toniclecb.pauta.model.Associado;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AssociadoRequestDTO {

	@NotBlank(message = "Nome é obrigatório!")
	@Schema(description = "Nome do associado", example = "Maria da Silva")
	private String nome;

	@NotBlank(message = "CPF é obrigatório!")
	@Schema(description = "CPF do associado", example = "36599334067")
	private String cpf;
	
	public AssociadoRequestDTO() {
	}
	
	public AssociadoRequestDTO(@NotBlank(message = "Nome é obrigatório!") String nome,
			@NotBlank(message = "CPF é obrigatório!") String cpf) {
		super();
		this.nome = nome;
		this.cpf = cpf;
	}


	public Associado toEntity() {
		return new Associado(null, this.nome, this.cpf);
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
