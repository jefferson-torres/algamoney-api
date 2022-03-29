package com.algaworks.algamoneyapi.service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import com.algaworks.algamoneyapi.repository.projection.ResumoLancamento;
import com.algaworks.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

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

	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}

	public Lancamento buscarPorCodigo(Long codigo) {
		return lancamentoRepository.findById(codigo).orElseThrow();
	}
	
	public void deletar(Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}

	public Page<ResumoLancamento> resumo(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}

}
