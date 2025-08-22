package br.ufes.participacidadao.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import br.ufes.participacidadao.dto.IBGEMunicipioDTO;
import br.ufes.participacidadao.dto.IBGECidadeDadosDTO;
import br.ufes.participacidadao.services.IBGEService;

@RestController
@RequestMapping("/api/ibge")
@CrossOrigin(origins = "http://localhost:4200")

public class IBGEController {

    @Autowired
    private IBGEService ibgeService;

    public IBGEController(IBGEService ibgeService) {
        this.ibgeService = ibgeService;
    }

    @GetMapping("/municipios")
    public ResponseEntity<List<IBGEMunicipioDTO>> buscarMunicipios(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf) {

        List<IBGEMunicipioDTO> municipios;

        if (uf != null) {
            municipios = ibgeService.buscarMunicipiosPorUF(uf);
        } else if (nome != null) {
            municipios = ibgeService.buscarMunicipioPorNome(nome)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } else {
            municipios = ibgeService.buscarTodosMunicipios();
        }

        return ResponseEntity.ok(municipios);
    }

    @GetMapping("/cidade/{nome}/dados-completos")
    public ResponseEntity<IBGECidadeDadosDTO> buscarDadosCompletos(@PathVariable String nome) {
        return ibgeService.buscarDadosCompletosCidade(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/municipio/{codigoIBGE}/populacao")
    public ResponseEntity<Long> buscarPopulacao(@PathVariable Long codigoIBGE) {
        Optional<Long> populacao = ibgeService.buscarPopulacao(codigoIBGE);
        return populacao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}