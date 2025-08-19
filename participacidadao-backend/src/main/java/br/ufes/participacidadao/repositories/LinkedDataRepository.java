package br.ufes.participacidadao.repositores;

import br.ufes.participacidadao.models.DadosInterligados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface LinkedDataRepository extends JpaRepository<dadosInterligados, Long> {
    Optional<dadosInterligados> findByCityName(String cityName);
}
