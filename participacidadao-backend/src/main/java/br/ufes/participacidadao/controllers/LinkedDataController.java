package br.ufes.participacidadao.controllers;

import br.ufes.participacidadao.models.DadosInterligados;
import br.ufes.participacidadao.services.LinkedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/linked-data")
public class LinkedDataController {

    @Autowired
    private LinkedDataService linkedDataService;

    @GetMapping("/cidades/{cityName}")
    public ResponseEntity<DadosInterligados> getDadosInterligados(@PathVariable String cityName) {
        DadosInterligados dados = linkedDataService.interligarDadosCidade(cityName);
        return ResponseEntity.ok(dados);
    }

    @PostMapping("/sparql")
    public ResponseEntity<String> executeSparqlQuery(@RequestBody String sparqlQuery) {
        String resultado = linkedDataService.executarConsultaSparql(sparqlQuery);
        return ResponseEntity.ok(resultado);
    }
}
