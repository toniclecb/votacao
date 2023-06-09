package com.toniclecb.pauta.model.dto;

import java.time.LocalDateTime;

import com.toniclecb.pauta.model.Sessao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessaoResultadoResponseDTO {

	@Schema(description = "Código identificador da sessão", example = "2")
	private Long id;
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;
	@Schema(description = "Data de inicio da votação", example = "2023-06-09T10:56:45.4765228")
	private LocalDateTime inicioVotacao;
	@Schema(description = "Data do fim da votação", example = "2023-06-09T10:56:45.4765228")
	private LocalDateTime fimVotacao;
	@Schema(description = "Quantidade de votos contabilizados como SIM", example = "32")
	private int votosSim;
	@Schema(description = "Quantidade de votos contabilizados como NÃO", example = "16")
	private int votosNao;
	private String situacao;

	public SessaoResultadoResponseDTO(Sessao sessao) {
		this.id = sessao.getId();
		this.idPauta = sessao.getPauta().getId();
		this.inicioVotacao = sessao.getInicioVotacao();
		this.fimVotacao = sessao.getFimVotacao();
	}

}
