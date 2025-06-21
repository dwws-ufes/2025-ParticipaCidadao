package br.ufes.participacidadao.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    public String checkLogin(Authentication authentication) {
        return "Autenticado como " + authentication.getName();
    }
}
