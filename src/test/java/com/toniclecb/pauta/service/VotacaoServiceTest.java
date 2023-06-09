package com.toniclecb.pauta.service;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.toniclecb.pauta.exception.ConflictException;
import com.toniclecb.pauta.exception.ForbiddenException;
import com.toniclecb.pauta.exception.ResourceNotFoundException;
import com.toniclecb.pauta.model.Associado;
import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.Voto;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.model.dto.VotoRequestDTO;
import com.toniclecb.pauta.model.dto.VotoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;
import com.toniclecb.pauta.model.mapper.impl.SessaoToSessaoResponseDtoMapper;
import com.toniclecb.pauta.model.mapper.impl.VotoToVotoResponseDtoMapper;
import com.toniclecb.pauta.repository.AssociadoRepository;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;
import com.toniclecb.pauta.repository.VotoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VotacaoServiceTest {

	@Mock
    private AssociadoRepository associadoRepository;
	
	@Mock
    private SessaoRepository sessaoRepository;

	@Mock
    private PautaRepository pautaRepository;
	
	@Mock
	private VotoRepository votoRepository;

	@Spy
	private Mapper<Voto, VotoResponseDTO> votoToVotoResponseMapper = new VotoToVotoResponseDtoMapper();

	@Spy
	private Mapper<Sessao, SessaoResponseDTO> sessaoToSessaoResponseMapper = new SessaoToSessaoResponseDtoMapper();
    
    @InjectMocks
    private VotacaoService service;

    @Test
    public void testVoto(){
    	Associado associado = new Associado(2L, "Fulano", "36599334067");
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
    	Sessao sessao = new Sessao(pautaEntity);
    	
    	sessao.setId(10L);
    	LocalDateTime data = LocalDateTime.now();
        sessao.setInicioVotacao(data.minusSeconds(360000));
        sessao.setFimVotacao(data.plusSeconds(360000));
    	Voto voto = new Voto(1L, sessao, associado, false, data);
    	
    	Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.of(associado));
    	Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
    	Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(sessao);
    	Mockito.when(votoRepository.save(any())).thenReturn(voto);
    	
    	
    	VotoResponseDTO response = service.insertVoto(new VotoRequestDTO(10L, 2L, "não"));
    	
    	Assertions.assertEquals(pautaEntity.getId(), response.getIdPauta());
    	Assertions.assertEquals(associado.getId(), response.getIdAssociado());
    	Assertions.assertEquals(sessao.getId(), response.getIdSessao());
    	Assertions.assertEquals("NÃO", response.getVoto());
    	Assertions.assertEquals(0, data.compareTo(response.getDataVoto()));
    }
    
    @Test
    public void testVotoSemAssociado(){    	
		Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.ofNullable(null));
    	
    	Assertions.assertThrows(ResourceNotFoundException.class, () -> service.insertVoto(new VotoRequestDTO(10L, 2L, "não")));
    }
    
    @Test
    public void testVotoSemPauta(){
    	Associado associado = new Associado(2L, "Fulano", "36599334067");
    	
		Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.of(associado));
    	Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.ofNullable(null));
    	
    	Assertions.assertThrows(ResourceNotFoundException.class, () -> service.insertVoto(new VotoRequestDTO(10L, 2L, "não")));
    }
    
    @Test
    public void testVotoSemSessao(){
    	Associado associado = new Associado(2L, "Fulano", "36599334067");
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
    	
		Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.of(associado));
    	Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
    	Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(null);
    	
    	Assertions.assertThrows(ConflictException.class, () -> service.insertVoto(new VotoRequestDTO(10L, 2L, "não")));
    }

    @Test
    public void testVotoForaDoPeriodo(){
    	Associado associado = new Associado(2L, "Fulano", "36599334067");
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
    	Sessao sessao = new Sessao(pautaEntity);
    	
    	LocalDateTime data = LocalDateTime.now();
        sessao.setInicioVotacao(data.minusSeconds(3600));
        sessao.setFimVotacao(data.minusSeconds(3000));
    	Voto voto = new Voto(null, sessao, associado, false, data);
    	
		Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.of(associado));
    	Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
    	Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(sessao);
    	Mockito.when(votoRepository.save(any())).thenReturn(voto);
    	
    	Assertions.assertThrows(ForbiddenException.class, () -> service.insertVoto(new VotoRequestDTO(10L, 2L, "não")));
    }

    @Test
    public void testVotoJaVotado(){
    	Associado associado = new Associado(2L, "Fulano", "36599334067");
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
    	Sessao sessao = new Sessao(pautaEntity);
    	sessao.setInicioVotacao(LocalDateTime.now());
    	sessao.setFimVotacao(LocalDateTime.now().plusSeconds(3600));
    	LocalDateTime data = LocalDateTime.now();
    	Voto voto = new Voto(null, sessao, associado, false, data);
    	
		Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.of(associado));
    	Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
    	Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(sessao);
    	Mockito.when(votoRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
    	
    	Assertions.assertThrows(ConflictException.class, () -> service.insertVoto(new VotoRequestDTO(10L, 2L, "não")));
    }
}
