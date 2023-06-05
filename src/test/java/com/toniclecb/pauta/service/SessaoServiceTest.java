package com.toniclecb.pauta.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	
    @InjectMocks
    private SessaoService service;
    
    @Test
    public void testGetSessao() {
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Sessao sessaoEntity = new Sessao(pautaEntity);
        Date data = new Date();
        sessaoEntity.setInicioVotacao(new Date(data.getTime() - 3600));
        sessaoEntity.setFimVotacao(new Date(data.getTime() - 3000));
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
        
        Assert.assertEquals(5, response.getVotosSim());
        Assert.assertEquals(5, response.getVotosNao());
        Assert.assertEquals("Votação Finalizada!", response.getSituacao());
    }

    @Test
    public void testGetSessaoEmAndamento() {
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Sessao sessaoEntity = new Sessao(pautaEntity);
        Date data = new Date();
        sessaoEntity.setInicioVotacao(new Date(data.getTime()));
        sessaoEntity.setFimVotacao(new Date(data.getTime() + 360));
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
        
        Assert.assertEquals(9, response.getVotosSim());
        Assert.assertEquals(1, response.getVotosNao());
        Assert.assertEquals("Votação em andamento!", response.getSituacao());
    }
    
    @Test
    public void testInicioSessao(){
        Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Sessao sessao = new Sessao(pautaEntity);
        
        Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
        Mockito.when(sessaoRepository.save(any())).thenReturn(sessao);

        SessaoResponseDTO response = service.insertSessao(
        		new SessaoRequestDTO(10L, 3600));

        Assert.assertEquals(sessao.getPauta().getId(), response.getIdPauta());
    }

    @Test
    public void testVoto(){
    	Associado associado = new Associado(2L, "Fulano", "36599334067");
    	Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
    	Sessao sessao = new Sessao(pautaEntity);
    	sessao.setInicioVotacao(new Date());
    	sessao.setFimVotacao(new Date(sessao.getInicioVotacao().getTime() + 3600));
    	Date data = new Date();
    	Voto voto = new Voto(sessao, associado, false, data);
    	
		Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.of(associado));
    	Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
    	Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(sessao);
    	Mockito.when(votoRepository.save(any())).thenReturn(voto);
    	
    	VotoResponseDTO response = service.insertVoto(new VotoRequestDTO(10L, 2L, "não"));
    	
    	Assert.assertEquals(pautaEntity.getId(), response.getIdPauta());
    	Assert.assertEquals(associado.getId(), response.getIdAssociado());
    	Assert.assertEquals(sessao.getId(), response.getIdSessao());
    	Assert.assertEquals("NÃO", response.getVoto());
    	Assert.assertEquals(data.getTime(), response.getDataVoto().getTime());
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
    	Date data = new Date();
    	sessao.setInicioVotacao(new Date(data.getTime() - 3600));
    	sessao.setFimVotacao(new Date(data.getTime() - 3000));
    	Voto voto = new Voto(sessao, associado, false, data);
    	
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
    	sessao.setInicioVotacao(new Date());
    	sessao.setFimVotacao(new Date(sessao.getInicioVotacao().getTime() + 3600));
    	Date data = new Date();
    	Voto voto = new Voto(sessao, associado, false, data);
    	
		Mockito.when(associadoRepository.findById(2L)).thenReturn(Optional.of(associado));
    	Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
    	Mockito.when(sessaoRepository.findByPautaId(10L)).thenReturn(sessao);
    	Mockito.when(votoRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
    	
    	Assertions.assertThrows(ConflictException.class, () -> service.insertVoto(new VotoRequestDTO(10L, 2L, "não")));
    }
}
