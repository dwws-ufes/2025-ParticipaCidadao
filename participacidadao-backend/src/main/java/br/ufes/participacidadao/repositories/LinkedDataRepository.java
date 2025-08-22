package br.ufes.participacidadao.repositories;

import br.ufes.participacidadao.models.DadosEnriquecidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkedDataRepository extends JpaRepository<DadosEnriquecidos, Long> {
    Optional<DadosEnriquecidos> findByNomeCidade(String nomeCidade);

    Optional<DadosEnriquecidos> findByCodigoIBGE(Long codigoIBGE);

    boolean existsByNomeCidade(String nomeCidade);
}
