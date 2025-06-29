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

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/new")
    public IssueModel createIssue(@RequestBody IssueModel issueModel) {
        return this.issueRepository.save(issueModel);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<IssueModel> getAllIssues() {
        return issueRepository.findAll();
    }
}
