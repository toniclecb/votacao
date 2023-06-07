package com.toniclecb.pauta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.toniclecb.pauta.exception.ConflictException;
import com.toniclecb.pauta.model.Associado;
import com.toniclecb.pauta.model.dto.AssociadoRequestDTO;
import com.toniclecb.pauta.model.dto.AssociadoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;
import com.toniclecb.pauta.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private Mapper<Associado, AssociadoResponseDTO> associadoToAssociadoResponseMapper;
	
	@Autowired
	private AssociadoRepository repository;
	
	public AssociadoResponseDTO insertAssociado(AssociadoRequestDTO requestDto) {
		try {
			Associado saved = repository.save(requestDto.toEntity());
			return associadoToAssociadoResponseMapper.toResponse(saved);
		} catch (DataIntegrityViolationException e) {
			throw new ConflictException("Associado com este documento já foi cadastrado!");
		}
	}
}
