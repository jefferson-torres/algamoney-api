package com.algaworks.algamoneyapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.algamoneyapi.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaService pessoaService;

	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		try {
			Pessoa pessoa = pessoaService.buscarPeloCodigo(lancamento.getPessoa().getCodigo());

			if (pessoa.isInativo()) {
				throw new PessoaInexistenteOuInativaException();
			}

			lancamento.setPessoa(pessoa);

			return lancamentoRepository.save(lancamento);
		} catch (NoSuchElementException e) {
			throw new PessoaInexistenteOuInativaException();
		}

	}

	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}

	public Lancamento buscarPorCodigo(Long codigo) {
		return lancamentoRepository.findById(codigo).orElseThrow();
	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return ResponseEntity.badRequest().body(erros);
	}

}
