package com.toniclecb.pauta.model.dto;

import java.util.Date;

public class VotoResponseDTO {

	private Long idVoto;
	private Long idPauta;
	private Long idSessao;
	private Long idAssociado;
	private String voto;
	private Date dataVoto;
	
	public VotoResponseDTO() {
	}

	public VotoResponseDTO(Long idVoto, Long idPauta, Long idSessao, Long idAssociado, String voto, Date dataVoto) {
		super();
		this.idVoto = idVoto;
		this.idPauta = idPauta;
		this.idSessao = idSessao;
		this.idAssociado = idAssociado;
		this.voto = voto;
		this.dataVoto = dataVoto;
	}


	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Long getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(Long idSessao) {
		this.idSessao = idSessao;
	}

	public Long getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		this.idAssociado = idAssociado;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public Date getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(Date dataVoto) {
		this.dataVoto = dataVoto;
	}
}
