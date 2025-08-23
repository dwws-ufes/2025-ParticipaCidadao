package br.ufes.participacidadao.services;

import br.ufes.participacidadao.models.DadosEnriquecidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.ufes.participacidadao.dto.IBGECidadeDadosDTO;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class LinkedDataService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IBGEService ibgeService;

    public DadosEnriquecidos enriquecerDadosCidade(String nomeCidade) {

        // Implementação da busca em APIs externas e DBpedia
        DadosEnriquecidos dados = new DadosEnriquecidos();

        // Buscar dados do IBGE
        Map<String, Object> dadosIbge = buscarDadosIBGE(nomeCidade);

        // Buscar dados do DBpedia
        Map<String, Object> dadosDbpedia = buscarDadosDBpedia(nomeCidade);

        // Combinar os dados obtidos
        return combinarDados(dados, dadosIbge, dadosDbpedia);
    }

    public String executarConsultaSparql(String sparqlQuery) {
        try {
            // DBpedia public endpoint
            String endpoint = "https://dbpedia.org/sparql";
            String url = endpoint
                    + "?query=" + URLEncoder.encode(sparqlQuery, StandardCharsets.UTF_8)
                    + "&format=" + URLEncoder.encode("application/sparql-results+json", StandardCharsets.UTF_8);

            ResponseEntity<String> resp = restTemplate.getForEntity(URI.create(url), String.class);
            return resp.getBody() != null ? resp.getBody() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // private Map<String, Object> buscarDadosIBGE(String nomeCidade) {
    // // Implementar a lógica para buscar dados do IBGE
    // String url = "https://servicodados.ibge.gov.br/api/v2/malhas/" + nomeCidade;
    // try {
    // Map<String, Object> response = restTemplate.getForObject(url, Map.class);
    // return response != null ? response : new HashMap<>();
    // } catch (Exception e) {
    // e.printStackTrace();
    // return new HashMap<>();
    // }
    // }

    private Map<String, Object> buscarDadosIBGE(String nomeCidade) {
        Map<String, Object> r = new HashMap<>();
        try {
            var dadosOpt = ibgeService.buscarDadosCompletosCidade(nomeCidade);
            if (dadosOpt.isPresent()) {
                IBGECidadeDadosDTO dados = dadosOpt.get();
                r.put("nome", dados.getNome());
                r.put("uf", dados.getUf());
                r.put("regiao", dados.getRegiao());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    // private Map<String, Object> buscarDadosDBpedia(String nomeCidade) {
    // // Implementar a lógica para buscar dados do DBpedia
    // String url = "http://dbpedia.org/data/" + nomeCidade + ".json";
    // try {
    // Map<String, Object> response = restTemplate.getForObject(url, Map.class);
    // return response != null ? response : new HashMap<>();
    // } catch (Exception e) {
    // e.printStackTrace();
    // return new HashMap<>();
    // }
    // }

    private Map<String, Object> buscarDadosDBpedia(String nomeCidade) {
        Map<String, Object> r = new HashMap<>();
        try {
            // Heurística simples para montar o recurso
            String resource = nomeCidade.trim().replace(' ', '_');
            String uri = "http://dbpedia.org/resource/" + resource;
            r.put("dbpediaUri", uri);

            // Em muitos casos a ligação para Wikidata vem via owl:sameAs no grafo;
            // para manter simples, deixo um placeholder:
            // Ex.: r.put("wikidataUri", "http://www.wikidata.org/entity/QXXXX");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    // private DadosEnriquecidos combinarDados(DadosEnriquecidos dados, Map<String,
    // Object> dadosIbge,
    // Map<String, Object> dadosDbpedia) {

    // if (dadosIbge.containsKey("nome")) {
    // dados.setnomeCidade((String) dadosIbge.get("nome"));
    // }
    // if (dadosIbge.containsKey("populacao")) {
    // dados.setPopulation((Long) dadosIbge.get("populacao"));
    // }
    // if (dadosIbge.containsKey("area")) {
    // dados.setArea((Double) dadosIbge.get("area"));
    // }
    // if (dadosIbge.containsKey("pibPerCapita")) {
    // dados.setPibPerCapita((Double) dadosIbge.get("pibPerCapita"));
    // }
    // if (dadosDbpedia.containsKey("dbpediaUri")) {
    // dados.setDbpediaUri((String) dadosDbpedia.get("dbpediaUri"));
    // }
    // if (dadosDbpedia.containsKey("wikidataUri")) {
    // dados.setWikidataUri((String) dadosDbpedia.get("wikidataUri"));
    // }
    // return dados;
    // }

    private DadosEnriquecidos combinarDados(
            DadosEnriquecidos dados,
            Map<String, Object> dadosIbge,
            Map<String, Object> dadosDbpedia) {

        if (dadosIbge.containsKey("nome")) {
            // Ideal: padronize na entidade para setNomeCidade(...)
            try {
                dados.getClass().getMethod("setNomeCidade", String.class);
                // se existir o setter padronizado, use-o
                dados.setNomeCidade((String) dadosIbge.get("nome"));
            } catch (NoSuchMethodException ex) {
                // fallback para o seu método atual
                dados.setNomeCidade((String) dadosIbge.get("nome"));
            }
        }

        if (dadosIbge.containsKey("populacao")) {
            Number n = asNumber(dadosIbge.get("populacao"));
            if (n != null)
                dados.setPopulation(n.longValue());
        }

        if (dadosIbge.containsKey("area")) {
            Number n = asNumber(dadosIbge.get("area"));
            if (n != null)
                dados.setArea(n.doubleValue());
        }

        if (dadosIbge.containsKey("pibPerCapita")) {
            Number n = asNumber(dadosIbge.get("pibPerCapita"));
            if (n != null)
                dados.setPibPerCapita(n.doubleValue());
        }

        if (dadosDbpedia.containsKey("dbpediaUri")) {
            dados.setDbpediaUri((String) dadosDbpedia.get("dbpediaUri"));
        }
        if (dadosDbpedia.containsKey("wikidataUri")) {
            dados.setWikidataUri((String) dadosDbpedia.get("wikidataUri"));
        }

        return dados;
    }

    /** Converte objeto para Number de forma segura */
    private Number asNumber(Object o) {
        if (o == null)
            return null;
        if (o instanceof Number)
            return (Number) o;
        try {
            return Double.valueOf(o.toString());
        } catch (Exception ignored) {
        }
        return null;
    }
}
