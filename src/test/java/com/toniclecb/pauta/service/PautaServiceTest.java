package com.toniclecb.pauta.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.toniclecb.pauta.model.Pauta;
import com.toniclecb.pauta.model.dto.PautaRequestDTO;
import com.toniclecb.pauta.model.dto.PautaResponseDTO;
import com.toniclecb.pauta.repository.PautaRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PautaServiceTest {

	@Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService service;
    
    @Test
    public void testGetPautaById(){
        Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Mockito.when(pautaRepository.findById(10L)).thenReturn(Optional.of(pautaEntity));

        PautaResponseDTO response = service.getPautaById(10L);

        Assertions.assertEquals(pautaEntity.getId(), response.getId());
        Assertions.assertEquals(pautaEntity.getNome(), response.getNome());
        Assertions.assertEquals(pautaEntity.getDescricao(), response.getDescricao());
    }
    
    @Test
    public void testInsertPauta(){
        Pauta pautaEntity = new Pauta(10L, "pauta", "descricao da pauta");
        Mockito.when(pautaRepository.save(any())).thenReturn(pautaEntity);

        PautaResponseDTO response = service.insertPauta(
        		new PautaRequestDTO(pautaEntity.getNome(), pautaEntity.getDescricao()));

        Assertions.assertEquals(pautaEntity.getId(), response.getId());
        Assertions.assertEquals(pautaEntity.getNome(), response.getNome());
        Assertions.assertEquals(pautaEntity.getDescricao(), response.getDescricao());
    }
    
}
