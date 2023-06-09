package com.toniclecb.pauta.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoResponseDTO {

	@Schema(description = "Código identificador do voto", example = "2")
	private Long idVoto;
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;
	@Schema(description = "Código identificador da sessão", example = "2")
	private Long idSessao;
	@Schema(description = "Código identificador do associado", example = "2")
	private Long idAssociado;
	@Schema(description = "Voto contabilizado", example = "NÃO")
	private String voto;
	@Schema(description = "Data do voto contabilizado", example = "2023-06-09T10:56:45.4765228")
	private LocalDateTime dataVoto;

}
