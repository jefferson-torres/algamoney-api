package com.algaworks.algamoneyapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.repository.LacamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LacamentoRepository lacamentoRepository;
	
	@GetMapping
	public List<Lancamento> listar(){
		return lacamentoRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public Lancamento buscarPorCodigo(@PathVariable Long codigo){
		return lacamentoRepository.findById(codigo).orElseThrow();
	}
	
}
