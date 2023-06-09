package com.toniclecb.pauta.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessaoResponseDTO {

	@Schema(description = "Código identificador da sessão", example = "2")
	private Long id;
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;
	@Schema(description = "Data de inicio da votação", example = "2023-06-09T10:56:45.4765228")
	private LocalDateTime inicioVotacao;
	@Schema(description = "Data do fim da votação", example = "2023-06-09T10:56:45.4765228")
	private LocalDateTime fimVotacao;
}
