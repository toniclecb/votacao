package com.toniclecb.pauta.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class VotoRequestDTO {

	@NotNull(message = "Pauta é obrigatória!")
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;

	@NotNull(message = "Associado é obrigatório!")
	@Schema(description = "Código identificador do associado", example = "3")
	private Long idAssociado;
	
	@NotNull(message = "Voto do associado é obrigatório!")
	@Schema(description = "Voto do associado para a pauta", example = "SIM")
	private String voto;
	
	public VotoRequestDTO() {
	}
	
	public VotoRequestDTO(Long idPauta, Long idAssociado, String voto) {
		this.idPauta = idPauta;
		this.idAssociado = idAssociado;
		this.voto = voto;
	}



	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
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
}
