package com.algaworks.algamoneyapi.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

@Entity
@Table(name = "pessoa")
@Data
@EqualsAndHashCode
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Include
	private Long codigo;
	
	@NotNull
	private String nome;
	
	@NotNull
	private Boolean ativo;
	
	@Embedded
	private Endereco endereco;
}
