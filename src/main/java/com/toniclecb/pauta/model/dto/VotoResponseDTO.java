package com.toniclecb.pauta.model.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

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
	@Schema(description = "Data do voto contabilizado", example = "2023-06-06T01:05:50.925+00:00")
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
