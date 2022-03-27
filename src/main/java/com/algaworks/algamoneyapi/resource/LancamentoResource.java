package com.algaworks.algamoneyapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.service.LancamentoService;
import com.algaworks.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	private MessageSource messageSource;
	
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
	
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
}
