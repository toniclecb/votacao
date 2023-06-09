package com.toniclecb.pauta.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"sessao_id", "associado_id"})
})
public class Voto implements Serializable {
	private static final long serialVersionUID = 3641101252355921103L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(referencedColumnName= "id")
	private Sessao sessao;

	@ManyToOne
	@JoinColumn(referencedColumnName= "id")
	private Associado associado;
	
	@Column
	private boolean voto;

	@Column
	private LocalDateTime dataVoto;
}
