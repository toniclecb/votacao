package com.toniclecb.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toniclecb.pauta.model.dto.VotoRequestDTO;
import com.toniclecb.pauta.model.dto.VotoResponseDTO;
import com.toniclecb.pauta.service.VotacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/votacao")
@Tag(name = "Votação", description = "Possui todas as chamadas para executar o processo de votação.")
public class VotacaoController {

	@Autowired
	private VotacaoService service;

	@PostMapping
	@Operation(summary = "Enviar um voto de um associado")
	@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Voto realizado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Informações fornecidas incorretas!"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado!"),
        @ApiResponse(responseCode = "403", description = "O período de votação para a pauta já foi finalizado!"),
        @ApiResponse(responseCode = "409", description = "A votação ainda não foi iniciada ou o voto já foi realizado pelo associado!"),
	})
	public ResponseEntity<VotoResponseDTO> post(@Parameter(description = "Voto do associado para a determinada pauta") @Valid @RequestBody VotoRequestDTO votoDto){
		VotoResponseDTO saved = service.insertVoto(votoDto);

		return ResponseEntity.created(null).body(saved);
	}
}
