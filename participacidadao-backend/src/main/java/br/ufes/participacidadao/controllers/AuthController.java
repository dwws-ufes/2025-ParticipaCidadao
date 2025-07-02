package br.ufes.participacidadao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        Map<String, Object> result = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            result.put("success", true);
            result.put("user", authentication.getName());
            response.setStatus(HttpServletResponse.SC_OK);
            return result;
        } catch (AuthenticationException e) {
            result.put("success", false);
            result.put("error", "Invalid credentials");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return result;
        }
    }

    // Removed duplicate /users/auth/check endpoint to avoid mapping conflict
}
