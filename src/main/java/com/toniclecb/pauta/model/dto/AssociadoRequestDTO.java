package com.toniclecb.pauta.model.dto;

import com.toniclecb.pauta.model.Associado;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoRequestDTO {

	@NotBlank(message = "Nome é obrigatório!")
	@Schema(description = "Nome do associado", example = "Maria da Silva")
	private String nome;

	@NotBlank(message = "CPF é obrigatório!")
	@Schema(description = "CPF do associado", example = "36599334067")
	private String cpf;

	public Associado toEntity() {
		return new Associado(null, this.nome, this.cpf);
	}
}
