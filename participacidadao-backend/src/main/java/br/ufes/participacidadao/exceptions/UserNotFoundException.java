package br.ufes.participacidadao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("Usuário com ID" + id + "não encontrado.");
    }
}
