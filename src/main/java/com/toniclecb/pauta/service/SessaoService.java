package com.toniclecb.pauta.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toniclecb.pauta.exception.ConflictException;
import com.toniclecb.pauta.exception.ResourceNotFoundException;
import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.dto.SessaoRequestDTO;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.model.dto.SessaoResultadoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;
import com.toniclecb.pauta.model.projection.VotoProjection;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;
import com.toniclecb.pauta.repository.VotoRepository;
import com.toniclecb.pauta.util.DateUtil;

@Service
public class SessaoService {
	private static final Logger log = LoggerFactory.getLogger(SessaoService.class);

	@Autowired
	private Mapper<Sessao, SessaoResponseDTO> sessaoToSessaoResponseMapper;
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private VotoRepository votoRepository;
	
	public SessaoResponseDTO insertSessao(SessaoRequestDTO requestDto) {
		Sessao sessaoJaExiste = sessaoRepository.findByPautaId(requestDto.getIdPauta());
		if (sessaoJaExiste != null) {
			log.info("A votação já foi iniciada para a pauta fornecida com o id: " + requestDto.getIdPauta());
			throw new ConflictException("A votação já foi iniciada para a pauta fornecida com o id: " + requestDto.getIdPauta());
		}
		
		Pauta pauta = pautaRepository.findById(requestDto.getIdPauta()).orElseThrow(
				() -> new ResourceNotFoundException("Pauta não encontrada para o id: " + requestDto.getIdPauta()));

		Sessao saved = sessaoRepository.save(requestDto.toEntity(pauta));
		return mapSessaoToResponse(saved);
	}

	protected SessaoResponseDTO mapSessaoToResponse(Sessao saved) {
		return sessaoToSessaoResponseMapper.toResponse(saved);
	}

	public SessaoResultadoResponseDTO getSessao(Long idPauta) {
		Sessao sessao = sessaoRepository.findByPautaId(idPauta);
		if (sessao == null)
			throw new ResourceNotFoundException("Sessão não encontrada para o id: " + idPauta);
		SessaoResultadoResponseDTO sessaoResultado = new SessaoResultadoResponseDTO(sessao);
		
		VotoProjection votacao = votoRepository.findVotacaoByIdSessao(sessao.getId());
		if (votacao != null) {
			sessaoResultado.setVotosSim(votacao.getSim());
			sessaoResultado.setVotosNao(votacao.getNao());
		}
		LocalDateTime data = LocalDateTime.now();
		if (DateUtil.dentroDoPeriodo(data, sessao.getInicioVotacao(), sessao.getFimVotacao())) {
			sessaoResultado.setSituacao("Votação em andamento!");
		} else {
			sessaoResultado.setSituacao("Votação Finalizada!");
		}
		
		return sessaoResultado;
	}
}
