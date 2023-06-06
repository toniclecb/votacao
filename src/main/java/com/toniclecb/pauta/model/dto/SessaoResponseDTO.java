package com.toniclecb.pauta.model.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public class SessaoResponseDTO {

	@Schema(description = "Código identificador da sessão", example = "2")
	private Long id;
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;
	@Schema(description = "Data de inicio da votação", example = "2023-06-06T01:05:50.925+00:00")
	private Date inicioVotacao;
	@Schema(description = "Data do fim da votação", example = "2023-06-06T01:05:50.925+00:00")
	private Date fimVotacao;

	public SessaoResponseDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Date getInicioVotacao() {
		return inicioVotacao;
	}

	public void setInicioVotacao(Date inicioVotacao) {
		this.inicioVotacao = inicioVotacao;
	}

	public Date getFimVotacao() {
		return fimVotacao;
	}

	public void setFimVotacao(Date fimVotacao) {
		this.fimVotacao = fimVotacao;
	}
}
