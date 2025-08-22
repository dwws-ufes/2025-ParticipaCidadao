package br.ufes.participacidadao.controllers;

import br.ufes.participacidadao.models.DadosEnriquecidos;
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

    @GetMapping("/cidades/{nomeCidade}")
    public ResponseEntity<DadosEnriquecidos> getDadosEnriquecidos(@PathVariable String nomeCidade) {
        DadosEnriquecidos dados = linkedDataService.enriquecerDadosCidade(nomeCidade);
        return ResponseEntity.ok(dados);
    }

    // @PostMapping("/sparql")
    // public ResponseEntity<String> executeSparqlQuery(@RequestBody String
    // sparqlQuery) {
    // String resultado = linkedDataService.executarConsultaSparql(sparqlQuery);
    // return ResponseEntity.ok(resultado);
    // }

    @PostMapping("/sparql")
    public ResponseEntity<String> executeSparqlQuery(@RequestBody String sparqlQuery) {
        String resultado = linkedDataService.executarConsultaSparql(sparqlQuery);
        return ResponseEntity.ok(resultado);
    }
}
