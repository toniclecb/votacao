package com.toniclecb.pauta.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.dto.SessaoRequestDTO;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.repository.PautaRepository;
import com.toniclecb.pauta.repository.SessaoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SessaoServiceTest {

	@Mock
    private SessaoRepository sessaoRepository;

	@Mock
    private PautaRepository pautaRepository;
	
    @InjectMocks
    private SessaoService service;
        
    @Test
    public void testInicioSessao(){
        Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Sessao sessao = new Sessao(pautaEntity);
        sessao.setFimVotacao(new Date(1L));
        sessao.setInicioVotacao(new Date(2L));
        
        Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));
        Mockito.when(sessaoRepository.save(any())).thenReturn(sessao);

        SessaoResponseDTO response = service.insertSessao(
        		new SessaoRequestDTO(10L, 3600));

        Assert.assertEquals(sessao.getPauta().getId(), response.getIdPauta());
    }
}
