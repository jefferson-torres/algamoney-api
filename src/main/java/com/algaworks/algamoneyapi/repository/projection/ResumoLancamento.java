package com.algaworks.algamoneyapi.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.algamoneyapi.model.TipoLacamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResumoLancamento {

	private Long codigo;

	private String descricao;

	private TipoLacamento tipoLancamento;

	private LocalDate dataVencimento;

	private LocalDate dataPagamento;

	private BigDecimal valor;
	
	private String pessoa;

	private String categoria;
	
}
