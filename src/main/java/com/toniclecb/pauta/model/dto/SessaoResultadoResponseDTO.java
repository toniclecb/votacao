package com.toniclecb.pauta.model.dto;

import java.util.Date;

import com.toniclecb.pauta.model.Sessao;

import io.swagger.v3.oas.annotations.media.Schema;

public class SessaoResultadoResponseDTO {

	@Schema(description = "Código identificador da sessão", example = "2")
	private Long id;
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;
	@Schema(description = "Data de inicio da votação", example = "2023-06-06T01:05:50.925+00:00")
	private Date inicioVotacao;
	@Schema(description = "Data do fim da votação", example = "2023-06-06T01:05:50.925+00:00")
	private Date fimVotacao;
	@Schema(description = "Quantidade de votos contabilizados como SIM", example = "32")
	private int votosSim;
	@Schema(description = "Quantidade de votos contabilizados como NÃO", example = "16")
	private int votosNao;
	private String situacao;

	public SessaoResultadoResponseDTO() {
	}

	public SessaoResultadoResponseDTO(Sessao sessao) {
		this.id = sessao.getId();
		this.idPauta = sessao.getPauta().getId();
		this.inicioVotacao = sessao.getInicioVotacao();
		this.fimVotacao = sessao.getFimVotacao();
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

	public int getVotosSim() {
		return votosSim;
	}

	public void setVotosSim(int votosSim) {
		this.votosSim = votosSim;
	}

	public int getVotosNao() {
		return votosNao;
	}

	public void setVotosNao(int votosNao) {
		this.votosNao = votosNao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
