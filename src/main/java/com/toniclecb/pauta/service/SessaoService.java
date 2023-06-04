package com.toniclecb.pauta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toniclecb.pauta.exception.ConflictException;
import com.toniclecb.pauta.exception.ResourceNotFoundException;
import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.dto.SessaoRequestDTO;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;

@Service
public class SessaoService {
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;

	public SessaoResponseDTO insertSessao(SessaoRequestDTO requestDto) {
		Sessao sessaoJaExiste = sessaoRepository.findByPautaId(requestDto.getIdPauta());
		if (sessaoJaExiste != null) {
			throw new ConflictException("A votação já foi iniciada para a pauta fornecida com o id: " + requestDto.getIdPauta());
		}
		
		Pauta pauta = pautaRepository.findById(requestDto.getIdPauta()).orElseThrow(
				() -> new ResourceNotFoundException("Pauta não encontrada para o id: " + requestDto.getIdPauta()));

		Sessao saved = sessaoRepository.save(requestDto.toEntity(pauta));
		return new SessaoResponseDTO(saved);
	}
}