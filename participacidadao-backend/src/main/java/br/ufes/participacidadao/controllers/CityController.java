package br.ufes.participacidadao.controllers;

import br.ufes.participacidadao.dto.CityDTO;
import br.ufes.participacidadao.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<?> getCity(@RequestParam("name") String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body("Parâmetro 'name' é obrigatório.");
        }
        CityDTO dto = cityService.fetchByName(name.trim());
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}