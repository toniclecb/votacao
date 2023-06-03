package com.toniclecb.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toniclecb.pauta.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
	
}
