package com.toniclecb.pauta.model.mapper.impl;

import org.springframework.stereotype.Component;

import com.toniclecb.pauta.model.Voto;
import com.toniclecb.pauta.model.dto.VotoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;

@Component
public class VotoToVotoResponseDtoMapper implements Mapper<Voto, VotoResponseDTO> {

	@Override
	public VotoResponseDTO toResponse(Voto entity) {
		return new VotoResponseDTO(
				entity.getId(),
				entity.getSessao().getPauta().getId(),
				entity.getSessao().getId(),
				entity.getAssociado().getId(),
				entity.isVoto() ? "SIM" : "NÃO",
				entity.getDataVoto());
	}

}
