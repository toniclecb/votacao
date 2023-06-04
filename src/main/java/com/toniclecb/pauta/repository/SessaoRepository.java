package com.toniclecb.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toniclecb.pauta.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
	
	public Sessao findByPautaId(Long pautaId);
}
