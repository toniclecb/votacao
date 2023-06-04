package com.toniclecb.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toniclecb.pauta.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
}
