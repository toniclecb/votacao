package com.toniclecb.pauta.service;

import java.time.LocalDateTime;

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
import com.toniclecb.pauta.model.dto.VotoRequestDTO;
import com.toniclecb.pauta.model.dto.VotoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;
import com.toniclecb.pauta.repository.AssociadoRepository;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;
import com.toniclecb.pauta.repository.VotoRepository;
import com.toniclecb.pauta.util.DateUtil;

@Service
public class VotacaoService {
	private static final Logger log = LoggerFactory.getLogger(VotacaoService.class);

	@Autowired
	private Mapper<Voto, VotoResponseDTO> votoToVotoResponseMapper;

	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private VotoRepository votoRepository;

	/**
	 * Executa todos os metodos necessarios para validações e gravação da entidade Voto.
	 * 
	 * @param requestDto
	 * @return O voto computado
	 */
	public VotoResponseDTO insertVoto(VotoRequestDTO requestDto) {
		Associado associado = getAssociado(requestDto);
		Pauta pauta = getPauta(requestDto);
		Sessao sessao = getSessaoEntity(pauta);
		
		Voto votoEntity = getVotoEntity(requestDto, associado, sessao);

		try {
			Voto saved = votoRepository.save(votoEntity);

			return mapVotoToResponse(saved);
		} catch (DataIntegrityViolationException e) {
			log.info("O voto do associado já foi contabilizado!");
			throw new ConflictException("O voto do associado já foi contabilizado!");
		}
	}

	protected VotoResponseDTO mapVotoToResponse(Voto saved) {
		return votoToVotoResponseMapper.toResponse(saved);
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
	private Sessao getSessaoEntity(Pauta pauta) {
		Sessao sessao = sessaoRepository.findByPautaId(pauta.getId());
		if (sessao == null) {
			log.info("A votação ainda não foi iniciada! IdPauta: " + pauta.getId());
			throw new ConflictException("A votação ainda não foi iniciada!");
		}
		return sessao;
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
		LocalDateTime dataVoto = validaDataVoto(sessao);
		
		boolean voto = false;
		if (requestDto.getVoto() != null && "SIM".equals(requestDto.getVoto().toUpperCase())) {
			voto = true;
		}
		Voto votoEntity = new Voto(null, sessao, associado, voto, dataVoto);
		return votoEntity;
	}
	
	private LocalDateTime validaDataVoto(Sessao sessao) {
		LocalDateTime dataVoto = LocalDateTime.now();
		if (!DateUtil.dentroDoPeriodo(dataVoto, sessao.getInicioVotacao(), sessao.getFimVotacao())) {
			log.info("O período de votação para a pauta já foi finalizado!" + sessao.getId());
			throw new ForbiddenException("O período de votação para a pauta já foi finalizado!");
		}
		return dataVoto;
	}
}
