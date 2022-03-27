package com.algaworks.algamoneyapi.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@AllArgsConstructor
public class PessoaInexistenteOuInativaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
