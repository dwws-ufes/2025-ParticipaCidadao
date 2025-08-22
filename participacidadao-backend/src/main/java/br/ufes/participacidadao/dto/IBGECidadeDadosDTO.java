package br.ufes.participacidadao.dto;

public class IBGECidadeDadosDTO {
    private String nome;
    private String uf;
    private Long populacao;
    private Double pibPerCapita;
    private Double areaTerritorial;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getPopulacao() {
        return populacao;
    }

    public void setPopulacao(Long populacao) {
        this.populacao = populacao;
    }

    public Double getPibPerCapita() {
        return pibPerCapita;
    }

    public void setPibPerCapita(Double pibPerCapita) {
        this.pibPerCapita = pibPerCapita;
    }

    public Double getAreaTerritorial() {
        return areaTerritorial;
    }

    public void setAreaTerritorial(Double areaTerritorial) {
        this.areaTerritorial = areaTerritorial;
    }
}
