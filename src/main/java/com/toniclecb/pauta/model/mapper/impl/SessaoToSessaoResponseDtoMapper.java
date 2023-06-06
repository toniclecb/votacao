package com.toniclecb.pauta.model.mapper.impl;

import org.springframework.stereotype.Component;

import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;

@Component
public class SessaoToSessaoResponseDtoMapper implements Mapper<Sessao, SessaoResponseDTO> {

	@Override
	public SessaoResponseDTO toResponse(Sessao entity) {
		SessaoResponseDTO dto = new SessaoResponseDTO();

		dto.setId(entity.getId());
		dto.setIdPauta(entity.getPauta().getId());
		dto.setInicioVotacao(entity.getInicioVotacao());
		dto.setFimVotacao(entity.getFimVotacao());

		return dto;
	}

}
