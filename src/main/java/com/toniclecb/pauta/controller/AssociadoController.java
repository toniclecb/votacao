package com.toniclecb.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toniclecb.pauta.model.dto.AssociadoRequestDTO;
import com.toniclecb.pauta.model.dto.AssociadoResponseDTO;
import com.toniclecb.pauta.service.AssociadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controle utilizado para cadastro dos associados.
 * 
 * @author cleiton.balansin
 *
 */
@RestController
@RequestMapping("/api/v1/associado")
@Tag(name = "Associados", description = "Gestão de associados")
public class AssociadoController {
	@Autowired
	private AssociadoService service;

	@PostMapping
	@Operation(summary = "Criar um novo associado")
	@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Associado criado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Informações fornecidas incorretas!"),
	})
	public ResponseEntity<AssociadoResponseDTO> post(@Parameter(description = "Dados do associado") @Valid @RequestBody AssociadoRequestDTO associadoDto){
		AssociadoResponseDTO saved = service.insertAssociado(associadoDto);
		
		return ResponseEntity.created(null).body(saved);
	}
}
