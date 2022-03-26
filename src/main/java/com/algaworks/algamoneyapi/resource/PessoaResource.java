package com.algaworks.algamoneyapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse httpServletResponse) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pessoaSalva.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(pessoaSalva);
	}
	
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(codigo);
		
		return pessoaOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}
