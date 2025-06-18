package br.ufes.participacidadao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.participacidadao.models.VoteModel;

public interface VoteRepository extends JpaRepository<VoteModel, Long>{

}
