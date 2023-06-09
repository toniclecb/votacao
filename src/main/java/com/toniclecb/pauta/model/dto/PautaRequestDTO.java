package com.toniclecb.pauta.model.dto;

import com.toniclecb.pauta.model.Pauta;

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
public class PautaRequestDTO {

	@NotBlank(message = "Nome é obrigatório!")
	@Schema(description = "Nome da pauta", example = "Adicionar pizza como opção no café da manhã da empresa")
	private String nome;

	@Schema(description = "Descritiva da pauta", example = "Além de ser uma opção deliciosa, a pizza no café da manhã traz benefícios surpreendentes. Estudos mostram que a variedade de ingredientes, como queijos, vegetais e proteínas, fornece os nutrientes necessários para uma manhã energética e produtiva.")
	private String descricao;

	public Pauta toEntity() {
		return new Pauta(null, this.nome, this.descricao);
	}
}
