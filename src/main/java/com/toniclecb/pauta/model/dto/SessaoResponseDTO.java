package com.toniclecb.pauta.model.dto;

import java.util.Date;

import com.toniclecb.pauta.model.Sessao;

public class SessaoResponseDTO {

	private Long id;
	private Long idPauta;
	private Date inicioVotacao;
	private Date fimVotacao;

	public SessaoResponseDTO() {
	}

	public SessaoResponseDTO(Sessao sessao) {
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
}
