package com.toniclecb.pauta.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssociadoResponseDTO {

	@Schema(description = "Código identificador do associado", example = "2")
	private Long id;
	@Schema(description = "Nome do associado", example = "Maria da Silva")
	private String nome;
	@Schema(description = "CPF do associado", example = "36599334067")
	private String cpf;
}
