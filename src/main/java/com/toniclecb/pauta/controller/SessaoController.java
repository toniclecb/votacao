package com.toniclecb.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toniclecb.pauta.model.dto.SessaoRequestDTO;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.model.dto.SessaoResultadoResponseDTO;
import com.toniclecb.pauta.service.SessaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controle utilizado para gerenciamento das sessoes de votacao
 * 
 * @author cleiton.balansin
 *
 */
@RestController
@RequestMapping("/api/v1/sessao")
@Tag(name = "Votação", description = "Possui as chamadas para processar as sessões.")
public class SessaoController {
	@Autowired
	private SessaoService service;

	@PostMapping
	@Operation(summary = "Abrir uma sessão de votação (inicia o período de tempo de votação)")
	@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sessão iniciada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Informações fornecidas incorretas!"),
        @ApiResponse(responseCode = "404", description = "Pauta fornecida não encontrada!"),
        @ApiResponse(responseCode = "409", description = "Sessão já iniciada para pauta fornecida!"),
	})
	public ResponseEntity<SessaoResponseDTO> post(@Parameter(description = "Pauta selecionada para início da votação") @Valid @RequestBody SessaoRequestDTO sessaoDto){
		SessaoResponseDTO saved = service.insertSessao(sessaoDto);
		
		return ResponseEntity.created(null).body(saved);
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar uma sessão de votação")
	@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sessão encontrada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Informação fornecida incorreta!"),
        @ApiResponse(responseCode = "404", description = "Sessão não encontrada!"),
	})
	public ResponseEntity<SessaoResultadoResponseDTO> get(@Parameter(description = "Identificador único da sessão", example = "2") @PathVariable("id") Long id){
		return ResponseEntity.ok(service.getSessao(id));
    }
}
