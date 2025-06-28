package br.ufes.participacidadao.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/users/auth/check")
    public Map<String, String> checkLogin(Authentication authentication) {
        return Map.of("message", "Autenticado como " + authentication.getName());
    }
}
