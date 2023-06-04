package com.toniclecb.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toniclecb.pauta.model.Sessao;
import com.toniclecb.pauta.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
	
}
