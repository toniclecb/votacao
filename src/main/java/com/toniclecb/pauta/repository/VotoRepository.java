package com.toniclecb.pauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toniclecb.pauta.model.Voto;
import com.toniclecb.pauta.model.projection.VotoProjection;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
	
	@Query("SELECT "
	+ "    COUNT(*) AS total, "
	+ "    SUM(case when voto = true then 1 else 0 end) AS sim, "
	+ "    SUM(case when voto = false then 1 else 0 end) AS nao "
	+ "FROM Voto "
	+ "WHERE sessao.id = :idSessao "
	+ "GROUP BY sessao.id")
	public VotoProjection findVotacaoByIdSessao(@Param("idSessao") Long idSessao);
	
}
