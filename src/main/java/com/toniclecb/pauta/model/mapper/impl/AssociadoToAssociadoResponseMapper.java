package com.toniclecb.pauta.model.mapper.impl;

import org.springframework.stereotype.Component;

import com.toniclecb.pauta.model.Associado;
import com.toniclecb.pauta.model.dto.AssociadoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;

@Component
public class AssociadoToAssociadoResponseMapper implements Mapper<Associado, AssociadoResponseDTO> {

	@Override
	public AssociadoResponseDTO toResponse(Associado entity) {
		AssociadoResponseDTO dto = new AssociadoResponseDTO();
		dto.setId(entity.getId());
		dto.setCpf(entity.getCpf());
		dto.setNome(entity.getNome());
		
		return dto;
	}

}
