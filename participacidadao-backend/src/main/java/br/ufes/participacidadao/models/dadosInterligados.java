package br.ufes.participacidadao.models;

@Entity
@Table(name = "dados_interligados")

public class DadosInterligados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;
    private Long population;
    private Double area;
    private String dbpediaUri;
    private String wikidataUri;
    private Double pibPerCapita;

    // ===================================================================================================
    // Getters and Setters
    // ===================================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

}