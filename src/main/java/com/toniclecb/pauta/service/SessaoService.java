package com.toniclecb.pauta.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.toniclecb.pauta.exception.ConflictException;
import com.toniclecb.pauta.exception.ForbiddenException;
import com.toniclecb.pauta.exception.ResourceNotFoundException;
import com.toniclecb.pauta.model.Associado;
import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.Voto;
import com.toniclecb.pauta.model.dto.SessaoRequestDTO;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.model.dto.VotoRequestDTO;
import com.toniclecb.pauta.model.dto.VotoResponseDTO;
import com.toniclecb.pauta.repository.AssociadoRepository;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;
import com.toniclecb.pauta.repository.VotoRepository;
import com.toniclecb.pauta.util.DateUtil;

@Service
public class SessaoService {
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private AssociadoRepository associadoRepository;

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

	public VotoResponseDTO insertVoto(VotoRequestDTO requestDto) {
		Associado associado = associadoRepository.findById(requestDto.getIdAssociado()).orElseThrow(
				() -> new ResourceNotFoundException("Associado não encontrado para o id: " + requestDto.getIdAssociado()));

		Pauta pauta = pautaRepository.findById(requestDto.getIdPauta()).orElseThrow(
				() -> new ResourceNotFoundException("Pauta não encontrada para o id: " + requestDto.getIdPauta()));

		Sessao sessao = sessaoRepository.findByPautaId(pauta.getId());
		if (sessao == null) {
			throw new ConflictException("A votação ainda não foi iniciada!");
		}
		Date dataVoto = new Date();
		if (!DateUtil.dentroDoPeriodo(dataVoto, sessao.getInicioVotacao(), sessao.getFimVotacao())) {
			throw new ForbiddenException("O período de votação para a pauta já foi finalizado!");
		}
		boolean voto = false;
		if (requestDto.getVoto() != null && "SIM".equals(requestDto.getVoto().toUpperCase())) {
			voto = true;
		}

		try {
			Voto saved = votoRepository.save(new Voto(sessao, associado, voto, dataVoto));

			return new VotoResponseDTO(saved.getId(), saved.getSessao().getPauta().getId(), saved.getSessao().getId(),
					saved.getAssociado().getId(), saved.isVoto() ? "SIM" : "NÃO", saved.getDataVoto());
		} catch (DataIntegrityViolationException e) {
			throw new ConflictException("O voto do associado já foi contabilizado!");
		}
	}
}