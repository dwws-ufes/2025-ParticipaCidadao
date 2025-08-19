package br.ufes.participacidadao.services;

import br.ufes.participacidadao.models.DadosInterligados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class LinkedDataService {
    @Autowired
    private RestTemplate restTemplate;

    public DadosInterligados interligarDadosCidade(String cityName) {

        // Implementação da busca em APIs externas e DBpedia
        DadosInterligados dados = new DadosInterligados();

        // Buscar dados do IBGE
        Map<String, Object> dadosIbge = buscarDadosIBGE(cityName);

        // Buscar dados do DBpedia
        Map<String, Object> dadosDbpedia = buscarDadosDBpedia(cityName);

        // Combinar os dados obtidos
        return combinarDados(dados, dadosIbge, dadosDbpedia);
    }

    private Map<String, Object> buscarDadosIBGE(String cityName) {
        // Implementar a lógica para buscar dados do IBGE
        String url = "https://servicodados.ibge.gov.br/api/v2/malhas/" + cityName;
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return response != null ? response : new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private Map<String, Object> buscarDadosDBpedia(String cityName) {
        // Implementar a lógica para buscar dados do DBpedia
        String url = "http://dbpedia.org/data/" + cityName + ".json";
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return response != null ? response : new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private DadosInterligados combinarDados(DadosInterligados dados, Map<String, Object> dadosIbge,
            Map<String, Object> dadosDbpedia) {

        if (dadosIbge.containsKey("nome")) {
            dados.setCityName((String) dadosIbge.get("nome"));
        }
        if (dadosIbge.containsKey("populacao")) {
            dados.setPopulation((Long) dadosIbge.get("populacao"));
        }
        if (dadosIbge.containsKey("area")) {
            dados.setArea((Double) dadosIbge.get("area"));
        }
        if (dadosIbge.containsKey("pibPerCapita")) {
            dados.setPibPerCapita((Double) dadosIbge.get("pibPerCapita"));
        }
        if (dadosDbpedia.containsKey("dbpediaUri")) {
            dados.setDbpediaUri((String) dadosDbpedia.get("dbpediaUri"));
        }
        if (dadosDbpedia.containsKey("wikidataUri")) {
            dados.setWikidataUri((String) dadosDbpedia.get("wikidataUri"));
        }
        return dados;
    }
}
