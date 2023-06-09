package com.toniclecb.pauta.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequestDTO {

	@NotNull(message = "Pauta é obrigatória!")
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;

	@NotNull(message = "Associado é obrigatório!")
	@Schema(description = "Código identificador do associado", example = "3")
	private Long idAssociado;
	
	@NotNull(message = "Voto do associado é obrigatório!")
	@Schema(description = "Voto do associado para a pauta (Qualquer texto diferente de 'SIM' é considerado 'NÃO'!", example = "SIM")
	private String voto;

}
