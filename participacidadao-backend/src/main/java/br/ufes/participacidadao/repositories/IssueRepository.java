package br.ufes.participacidadao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.participacidadao.models.IssueModel;

public interface IssueRepository extends JpaRepository<IssueModel, Long>{

}
