package com.toniclecb.pauta.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.toniclecb.pauta.model.dto.SessaoResultadoResponseDTO;
import com.toniclecb.pauta.model.dto.VotoRequestDTO;
import com.toniclecb.pauta.model.dto.VotoResponseDTO;
import com.toniclecb.pauta.model.projection.VotoProjection;
import com.toniclecb.pauta.repository.AssociadoRepository;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;
import com.toniclecb.pauta.repository.VotoRepository;
import com.toniclecb.pauta.util.DateUtil;

@Service
public class SessaoService {
	private static final Logger log = LoggerFactory.getLogger(SessaoService.class);
	
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
			log.info("A votação já foi iniciada para a pauta fornecida com o id: " + requestDto.getIdPauta());
			throw new ConflictException("A votação já foi iniciada para a pauta fornecida com o id: " + requestDto.getIdPauta());
		}
		
		Pauta pauta = pautaRepository.findById(requestDto.getIdPauta()).orElseThrow(
				() -> new ResourceNotFoundException("Pauta não encontrada para o id: " + requestDto.getIdPauta()));

		Sessao saved = sessaoRepository.save(requestDto.toEntity(pauta));
		return new SessaoResponseDTO(saved);
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
		Date data = new Date();
		if (DateUtil.dentroDoPeriodo(data, sessao.getInicioVotacao(), sessao.getFimVotacao())) {
			sessaoResultado.setSituacao("Votação em andamento!");
		} else {
			sessaoResultado.setSituacao("Votação Finalizada!");
		}
		
		return sessaoResultado;
	}
	
	/**
	 * Executa todos os metodos necessarios para validações e gravação da entidade Voto.
	 * 
	 * @param requestDto
	 * @return O voto computado
	 */
	public VotoResponseDTO insertVoto(VotoRequestDTO requestDto) {
		Associado associado = getAssociado(requestDto);
		Pauta pauta = getPauta(requestDto);
		Sessao sessao = getSessao(pauta);
		
		Voto votoEntity = getVotoEntity(requestDto, associado, sessao);

		try {
			Voto saved = votoRepository.save(votoEntity);

			return new VotoResponseDTO(saved.getId(), saved.getSessao().getPauta().getId(), saved.getSessao().getId(),
					saved.getAssociado().getId(), saved.isVoto() ? "SIM" : "NÃO", saved.getDataVoto());
		} catch (DataIntegrityViolationException e) {
			log.info("O voto do associado já foi contabilizado!");
			throw new ConflictException("O voto do associado já foi contabilizado!");
		}
	}

	/**
	 * Retorna a entidade do banco ou <code>ResourceNotFoundException</code> se não existir no banco.
	 * @param requestDto
	 * @return A entidade do banco
	 */
	private Associado getAssociado(VotoRequestDTO requestDto) {
		Associado associado = associadoRepository.findById(requestDto.getIdAssociado()).orElseThrow(
				() -> new ResourceNotFoundException("Associado não encontrado para o id: " + requestDto.getIdAssociado()));
		return associado;
	}

	/**
	 * Retorna a entidade do banco ou <code>ResourceNotFoundException</code> se não existir no banco.
	 * @param requestDto
	 * @return A entidade do banco
	 */
	private Pauta getPauta(VotoRequestDTO requestDto) {
		Pauta pauta = pautaRepository.findById(requestDto.getIdPauta()).orElseThrow(
				() -> new ResourceNotFoundException("Pauta não encontrada para o id: " + requestDto.getIdPauta()));
		return pauta;
	}

	/**
	 * Retorna a entidade do banco ou <code>ResourceNotFoundException</code> se não existir no banco.
	 * @param requestDto
	 * @return A entidade do banco
	 */
	private Sessao getSessao(Pauta pauta) {
		Sessao sessao = sessaoRepository.findByPautaId(pauta.getId());
		if (sessao == null) {
			log.info("A votação ainda não foi iniciada! IdPauta: " + pauta.getId());
			throw new ConflictException("A votação ainda não foi iniciada!");
		}
		return sessao;
	}

	private Date validaDataVoto(Sessao sessao) {
		Date dataVoto = new Date();
		if (!DateUtil.dentroDoPeriodo(dataVoto, sessao.getInicioVotacao(), sessao.getFimVotacao())) {
			log.info("O período de votação para a pauta já foi finalizado!" + sessao.getId());
			throw new ForbiddenException("O período de votação para a pauta já foi finalizado!");
		}
		return dataVoto;
	}


	/**
	 * Valida o periodo de votação;
	 * Determina o valor da variável voto como true ou false;
	 * Constroi a entidade Voto com base em todas as informações dos parâmetros.
	 * 
	 * @param requestDto Os dados da requisição
	 * @param associado O associado encontrado
	 * @param sessao A sessao encontrada
	 * @return A entidade Voto com todas as informações necessarias para ser persistida
	 */
	private Voto getVotoEntity(VotoRequestDTO requestDto, Associado associado, Sessao sessao) {
		Date dataVoto = validaDataVoto(sessao);
		
		boolean voto = false;
		if (requestDto.getVoto() != null && "SIM".equals(requestDto.getVoto().toUpperCase())) {
			voto = true;
		}
		Voto votoEntity = new Voto(sessao, associado, voto, dataVoto);
		return votoEntity;
	}
}