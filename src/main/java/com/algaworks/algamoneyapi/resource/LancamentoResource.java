package com.algaworks.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@PostMapping
	public Lancamento criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo =  lancamentoService.salvar(lancamento);
		applicationEventPublisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return lancamentoSalvo;
	}
	
	@GetMapping
	public List<Lancamento> listar(){
		return lancamentoService.listar();
	}
	
	@GetMapping("/{codigo}")
	public Lancamento buscarPorCodigo(@PathVariable Long codigo){
		return lancamentoService.buscarPorCodigo(codigo);
	}
	
}
