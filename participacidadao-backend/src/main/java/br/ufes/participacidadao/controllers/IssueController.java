package br.ufes.participacidadao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.participacidadao.models.IssueModel;

import br.ufes.participacidadao.repositories.IssueRepository;
import br.ufes.participacidadao.services.LinkedDataService;
import br.ufes.participacidadao.services.IssueRdfService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {


    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private LinkedDataService linkedDataService;

    @Autowired
    private IssueRdfService issueRdfService;


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/new")
    public IssueModel createIssue(@RequestBody IssueModel issueModel) {
        // Enriquecer dados da cidade ao criar issue
        if (issueModel.getCity() != null && !issueModel.getCity().isEmpty()) {
            var dados = linkedDataService.enriquecerDadosCidade(issueModel.getCity());
            issueModel.setDadosEnriquecidos(dados);
        }
        return this.issueRepository.save(issueModel);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<IssueModel> getAllIssues() {
        return issueRepository.findAll();
    }

    @GetMapping(value = "/rdf", produces = "text/turtle")
    public ResponseEntity<String> getAllIssuesRdf() {
        List<IssueModel> issues = issueRepository.findAll();
        String rdf = issueRdfService.issuesToRdf(issues);
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/turtle")).body(rdf);
    }
}
