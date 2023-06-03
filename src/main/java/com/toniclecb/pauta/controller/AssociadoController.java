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

import jakarta.validation.Valid;

/**
 * Controle utilizado para cadastro dos associados.
 * 
 * @author cleiton.balansin
 *
 */
@RestController
@RequestMapping("/api/v1/associado")
public class AssociadoController {
	@Autowired
	private AssociadoService service;

	@PostMapping
	public ResponseEntity<AssociadoResponseDTO> post(@Valid @RequestBody AssociadoRequestDTO associadoDto){
		AssociadoResponseDTO saved = service.insertAssociado(associadoDto);
		
		return ResponseEntity.created(null).body(saved);
	}
}
//TODO fazer os testes
