package com.toniclecb.pauta.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
public class Sessao implements Serializable {
	private static final long serialVersionUID = -6464712698086179660L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@OneToOne
	@JoinColumn(name ="id_pauta", referencedColumnName= "id")
	private Pauta pauta;

	@Column
	private LocalDateTime inicioVotacao;
	
	@Column
	private LocalDateTime fimVotacao;

}
