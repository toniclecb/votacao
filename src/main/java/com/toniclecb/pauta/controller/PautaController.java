package com.toniclecb.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toniclecb.pauta.model.dto.PautaRequestDTO;
import com.toniclecb.pauta.model.dto.PautaResponseDTO;
import com.toniclecb.pauta.service.PautaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controle utilizado para cadastro das pautas.
 * 
 * @author cleiton.balansin
 *
 */
@RestController
@RequestMapping("/api/v1/pauta")
@Tag(name = "Pautas", description = "Gestão de pauta")
public class PautaController {
	@Autowired
	private PautaService service;

	@PostMapping
	@Operation(summary = "Criar uma nova pauta")
	@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Informações fornecidas incorretas!"),
	})
	public ResponseEntity<PautaResponseDTO> post(@Parameter(description = "Dados da pauta") @Valid @RequestBody PautaRequestDTO pautaDto){
		PautaResponseDTO saved = service.insertPauta(pautaDto);
		
		return ResponseEntity.created(null).body(saved);
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar uma pauta")
	@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pauta encontrada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Informação fornecida incorreta!"),
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada!"),
	})
    public ResponseEntity<PautaResponseDTO> get(@Parameter(description = "Identificador único da pauta", example = "2") @PathVariable("id") Long id){
		PautaResponseDTO pauta = service.getPautaById(id);
        return ResponseEntity.ok().body(pauta);
    }
}
