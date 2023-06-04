package com.toniclecb.pauta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toniclecb.pauta.exception.ResourceNotFoundException;
import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.dto.PautaRequestDTO;
import com.toniclecb.pauta.model.dto.PautaResponseDTO;
import com.toniclecb.pauta.repository.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository repository;

	public PautaResponseDTO insertPauta(PautaRequestDTO requestDto) {
		Pauta saved = repository.save(requestDto.toEntity());
		return new PautaResponseDTO(saved);
	}

	public PautaResponseDTO getPautaById(Long id) {
		return repository.findById(id).map(p -> new PautaResponseDTO(p))
				.orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada para o id: " + id));
	}
}
