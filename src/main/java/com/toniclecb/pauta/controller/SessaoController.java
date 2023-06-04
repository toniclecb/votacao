package com.toniclecb.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toniclecb.pauta.model.dto.SessaoRequestDTO;
import com.toniclecb.pauta.model.dto.SessaoResponseDTO;
import com.toniclecb.pauta.service.SessaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controle utilizado para gerenciamento do processo de votacao
 * 
 * @author cleiton.balansin
 *
 */
@RestController
@RequestMapping("/api/v1/votacao")
@Tag(name = "Votação", description = "Possui todas as chamadas para executar o processo de votação.")
public class SessaoController {
	@Autowired
	private SessaoService service;

	@PostMapping
	@Operation(summary = "Abrir uma sessão de votação")
	@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sessão iniciada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Informações fornecidas incorretas!"),
        @ApiResponse(responseCode = "404", description = "Pauta fornecida não encontrada!"),
        @ApiResponse(responseCode = "409", description = "Sessão já iniciada para pauta fornecida!"),
	})
	public ResponseEntity<SessaoResponseDTO> post(@Parameter(description = "Pauta selecionada para início") @Valid @RequestBody SessaoRequestDTO sessaoDto){
		SessaoResponseDTO saved = service.insertSessao(sessaoDto);
		
		return ResponseEntity.created(null).body(saved);
	}
}
