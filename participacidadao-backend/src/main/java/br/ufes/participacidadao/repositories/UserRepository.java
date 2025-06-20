package br.ufes.participacidadao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.participacidadao.models.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Long>{

}
