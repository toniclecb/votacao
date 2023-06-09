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
import org.springframework.test.context.junit4.SpringRunner;

import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.Voto;
import com.toniclecb.pauta.model.dto.SessaoRequestDTO;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.model.dto.SessaoResultadoResponseDTO;
import com.toniclecb.pauta.model.dto.VotoResponseDTO;
import com.toniclecb.pauta.model.mapper.Mapper;
import com.toniclecb.pauta.model.mapper.impl.SessaoToSessaoResponseDtoMapper;
import com.toniclecb.pauta.model.mapper.impl.VotoToVotoResponseDtoMapper;
import com.toniclecb.pauta.model.projection.VotoProjection;
import com.toniclecb.pauta.repository.AssociadoRepository;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;
import com.toniclecb.pauta.repository.VotoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SessaoServiceTest {

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
    private SessaoService service;
    
    
    @Test
    public void testGetSessao() {
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Sessao sessaoEntity = new Sessao(pautaEntity);
        LocalDateTime data = LocalDateTime.now();
        sessaoEntity.setInicioVotacao(data.minusSeconds(3600));
        sessaoEntity.setFimVotacao(data.minusSeconds(3000));
        VotoProjection votacao = new VotoProjection() {
			
			@Override
			public int getTotal() {
				return 10;
			}
			
			@Override
			public int getSim() {
				return 5;
			}
			
			@Override
			public int getNao() {
				return 5;
			}
		};
        
        Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
        Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(sessaoEntity);
        Mockito.when(sessaoRepository.save(any())).thenReturn(sessaoEntity);
        Mockito.when(votoRepository.findVotacaoByIdSessao(sessaoEntity.getId())).thenReturn(votacao);
        
        SessaoResultadoResponseDTO response = service.getSessao(10L);
        
        Assertions.assertEquals(5, response.getVotosSim());
        Assertions.assertEquals(5, response.getVotosNao());
        Assertions.assertEquals("Votação Finalizada!", response.getSituacao());
    }

    @Test
    public void testGetSessaoEmAndamento() {
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Sessao sessaoEntity = new Sessao(pautaEntity);
        
        LocalDateTime data = LocalDateTime.now();
        sessaoEntity.setInicioVotacao(data);
        sessaoEntity.setFimVotacao(data.plusSeconds(360));
        VotoProjection votacao = new VotoProjection() {
			
			@Override
			public int getTotal() {
				return 10;
			}
			
			@Override
			public int getSim() {
				return 9;
			}
			
			@Override
			public int getNao() {
				return 1;
			}
		};
        
        Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
        Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(sessaoEntity);
        Mockito.when(sessaoRepository.save(any())).thenReturn(sessaoEntity);
        Mockito.when(votoRepository.findVotacaoByIdSessao(sessaoEntity.getId())).thenReturn(votacao);
        
        SessaoResultadoResponseDTO response = service.getSessao(10L);
        
        Assertions.assertEquals(9, response.getVotosSim());
        Assertions.assertEquals(1, response.getVotosNao());
        Assertions.assertEquals("Votação em andamento!", response.getSituacao());
    }
    
    @Test
    public void testInicioSessao(){
        Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Sessao sessao = new Sessao(pautaEntity);
        
        Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
        Mockito.when(sessaoRepository.save(any())).thenReturn(sessao);

        SessaoResponseDTO response = service.insertSessao(
        		new SessaoRequestDTO(10L, 3600));

        Assertions.assertEquals(sessao.getPauta().getId(), response.getIdPauta());
    }
}
