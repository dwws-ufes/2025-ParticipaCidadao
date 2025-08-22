package br.ufes.participacidadao.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dados_Enriquecidos")

public class DadosEnriquecidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCidade;
    private Long population;
    private Double area;
    private String dbpediaUri;
    private String uf;
    private String regiao;
    private Long codigoIBGE;

    private String wikidataUri;
    private Double pibPerCapita;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // ===================================================================================================
    // Getters and Setters
    // ===================================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getDbpediaUri() {
        return dbpediaUri;
    }

    public void setDbpediaUri(String dbpediaUri) {
        this.dbpediaUri = dbpediaUri;
    }

    public String getWikidataUri() {
        return wikidataUri;
    }

    public void setWikidataUri(String wikidataUri) {
        this.wikidataUri = wikidataUri;
    }

    public Double getPibPerCapita() {
        return pibPerCapita;
    }

    public void setPibPerCapita(Double pibPerCapita) {
        this.pibPerCapita = pibPerCapita;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Long getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(Long codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}