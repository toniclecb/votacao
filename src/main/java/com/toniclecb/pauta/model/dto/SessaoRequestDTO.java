package com.toniclecb.pauta.model.dto;

import java.util.Calendar;
import java.util.Date;

import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class SessaoRequestDTO {

	@NotNull(message = "Pauta é obrigatória!")
	@Schema(description = "Código identificador da pauta", example = "2")
	private Long idPauta;

	@Schema(description = "Duração, em segundos, da votação", example = "3600")
	private Integer duracao = 60;
	
	public SessaoRequestDTO() {
	}
	
	public SessaoRequestDTO(Long idPauta, Integer duracao) {
		super();
		this.idPauta = idPauta;
		this.duracao = duracao;
	}

	public Sessao toEntity(Pauta pauta) {
		Sessao sessao = new Sessao();
		Date inicio = new Date();
		sessao.setInicioVotacao(inicio);
		
		Calendar data = Calendar.getInstance();
		data.setTime(inicio);
		data.add(Calendar.SECOND, duracao);
		sessao.setFimVotacao(data.getTime());
		
		sessao.setPauta(pauta);
		return sessao;
	}

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}
}
