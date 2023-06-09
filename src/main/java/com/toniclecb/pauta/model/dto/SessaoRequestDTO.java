package com.toniclecb.pauta.model.dto;

import java.time.LocalDateTime;

import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;

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
public class SessaoRequestDTO {

	@NotNull(message = "Pauta é obrigatória!")
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;

	@Schema(description = "Duração, em segundos, da votação", example = "3600")
	private Integer duracao = 60;

	public Sessao toEntity(Pauta pauta) {
		Sessao sessao = new Sessao();
		LocalDateTime inicio = LocalDateTime.now();
		sessao.setInicioVotacao(inicio);

		LocalDateTime fim = inicio.plusSeconds(duracao);
		sessao.setFimVotacao(fim);
		
		sessao.setPauta(pauta);
		return sessao;
	}
}
