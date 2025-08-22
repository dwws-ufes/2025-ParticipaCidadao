package br.ufes.participacidadao.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import br.ufes.participacidadao.dto.IBGEMunicipioDTO;
import br.ufes.participacidadao.dto.IBGEPopulacaoDTO;
import br.ufes.participacidadao.dto.IBGECidadeDadosDTO;

@Service
public class IBGEService {

    private static final String IBGE_LOCALIDADES_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";
    private static final String IBGE_AGREGADOS_URL = "https://servicodados.ibge.gov.br/api/v3/agregados";

    @Autowired
    private RestTemplate restTemplate;

    // Buscar município por nome
    public Optional<IBGEMunicipioDTO> buscarMunicipioPorNome(String nome) {
        try {
            String url = IBGE_LOCALIDADES_URL + "?nome" + URLEncoder.encode(nome, StandardCharsets.UTF_8);
            ResponseEntity<IBGEMunicipioDTO[]> response = restTemplate.getForEntity(url, IBGEMunicipioDTO[].class);

            if (response.getBody() != null && response.getBody().length > 0) {
                return Optional.of(response.getBody()[0]);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar município: " + e.getMessage());
        }

        return Optional.empty();
    }

    // Buscar todos os municípios (para autocomplete)
    public List<IBGEMunicipioDTO> buscarTodosMunicipios() {
        try {
            ResponseEntity<IBGEMunicipioDTO[]> response = restTemplate.getForEntity(IBGE_LOCALIDADES_URL,
                    IBGEMunicipioDTO[].class);

            return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar municípios: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Buscar municípios por UF
    public List<IBGEMunicipioDTO> buscarMunicipiosPorUF(String uf) {
        try {
            String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/"
                    + URLEncoder.encode(uf, StandardCharsets.UTF_8) + "/municipios";
            ResponseEntity<IBGEMunicipioDTO[]> response = restTemplate.getForEntity(url, IBGEMunicipioDTO[].class);

            return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar municípios por UF: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Buscar população atual
    public Optional<Long> buscarPopulacao(Long codigoIBGE) {
        try {
            String url = IBGE_AGREGADOS_URL + "/6579/periodos/2021/variaveis/9324?localidades=N6[" + codigoIBGE + "]";
            ResponseEntity<IBGEPopulacaoDTO[]> response = restTemplate.getForEntity(url, IBGEPopulacaoDTO[].class);

            if (response.getBody() != null && response.getBody().length > 0) {
                IBGEPopulacaoDTO dto = response.getBody()[0];
                if (dto.getResultados() != null && !dto.getResultados().isEmpty()) {
                    IBGEPopulacaoDTO.Resultado resultado = dto.getResultados().get(0);
                    if (resultado.getSeries() != null && !resultado.getSeries().isEmpty()) {
                        IBGEPopulacaoDTO.Resultado.Serie serie = resultado.getSeries().get(0);
                        String populacaoStr = serie.getSerie().get("2024");
                        if (populacaoStr != null) {
                            return Optional.of(Long.parseLong(populacaoStr));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar população: " + e.getMessage());
        }

        return Optional.empty();
    }

    // Buscar PIB per capita
    public Optional<Double> buscarPIBPerCapita(Long codigoIBGE) {
        try {
            String url = IBGE_AGREGADOS_URL + "/593/periodos/2019/variaveis/37?localidades=N6[" + codigoIBGE + "]";
            ResponseEntity<IBGEPopulacaoDTO[]> response = restTemplate.getForEntity(url, IBGEPopulacaoDTO[].class);

            if (response.getBody() != null && response.getBody().length > 0) {
                IBGEPopulacaoDTO dto = response.getBody()[0];
                if (dto.getResultados() != null && !dto.getResultados().isEmpty()) {
                    IBGEPopulacaoDTO.Resultado resultado = dto.getResultados().get(0);
                    if (resultado.getSeries() != null && !resultado.getSeries().isEmpty()) {
                        IBGEPopulacaoDTO.Resultado.Serie serie = resultado.getSeries().get(0);
                        String pibStr = serie.getSerie().get("2019");
                        if (pibStr != null) {
                            return Optional.of(Double.parseDouble(pibStr));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar PIB per capita: " + e.getMessage());
        }

        return Optional.empty();
    }

    // Buscar área territorial
    public Optional<Double> buscarAreaTerritorial(Long codigoIBGE) {
        try {
            String url = IBGE_AGREGADOS_URL + "/593/periodos/2019/variaveis/41?localidades=N6[" + codigoIBGE + "]";
            ResponseEntity<IBGEPopulacaoDTO[]> response = restTemplate.getForEntity(url, IBGEPopulacaoDTO[].class);

            if (response.getBody() != null && response.getBody().length > 0) {
                IBGEPopulacaoDTO dto = response.getBody()[0];
                if (dto.getResultados() != null && !dto.getResultados().isEmpty()) {
                    IBGEPopulacaoDTO.Resultado resultado = dto.getResultados().get(0);
                    if (resultado.getSeries() != null && !resultado.getSeries().isEmpty()) {
                        IBGEPopulacaoDTO.Resultado.Serie serie = resultado.getSeries().get(0);
                        String areaStr = serie.getSerie().get("2019");
                        if (areaStr != null) {
                            return Optional.of(Double.parseDouble(areaStr));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar área territorial: " + e.getMessage());
        }

        return Optional.empty();
    }

    // Metodo para buscar dados completos de uma cidade
    public Optional<IBGECidadeDadosDTO> buscarDadosCompletosCidade(String nomeCidade) {
        Optional<IBGEMunicipioDTO> municipioOpt = buscarMunicipioPorNome(nomeCidade);
        if (municipioOpt.isEmpty()) {
            return Optional.empty();
        }

        IBGEMunicipioDTO municipio = municipioOpt.get();
        Long codigoIBGE = municipio.getId();

        Optional<Long> populacaoOpt = buscarPopulacao(codigoIBGE);
        Optional<Double> pibPerCapitaOpt = buscarPIBPerCapita(codigoIBGE);
        Optional<Double> areaTerritorialOpt = buscarAreaTerritorial(codigoIBGE);

        IBGECidadeDadosDTO dto = new IBGECidadeDadosDTO();
        dto.setNome(municipio.getName());
        dto.setUf(municipio.getMicrorregiao().getMesorregiao().getUF().getSigla());
        dto.setPopulacao(populacaoOpt.orElse(null));
        dto.setPibPerCapita(pibPerCapitaOpt.orElse(null));
        dto.setAreaTerritorial(areaTerritorialOpt.orElse(null));

        return Optional.of(dto);
    }

    // Metodos auxiliares para extrair dados das respostas
    private Optional<Long> extrairPopulacao(IBGEPopulacaoDTO dados) {
        try {
            if (dados.getResultados() != null && !dados.getResultados().isEmpty()) {
                var resultado = dados.getResultados().get(0);
                if (resultado.getSeries() != null && !resultado.getSeries().isEmpty()) {
                    var serie = resultado.getSeries().get(0);
                    var valorStr = serie.getSerie().get("2022");
                    if (valorStr != null && !valorStr.equals("-")) {
                        return Optional.of(Long.parseLong(valorStr));
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter população: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<Double> extrairValorNumerico(IBGEPopulacaoDTO dados) {
        try {
            if (dados.getResultados() != null && !dados.getResultados().isEmpty()) {
                var resultado = dados.getResultados().get(0);
                if (resultado.getSeries() != null && !resultado.getSeries().isEmpty()) {
                    var serie = resultado.getSeries().get(0);
                    // Pega o primeiro valor disponível na série
                    for (String valor : serie.getSerie().values()) {
                        if (valor != null && !valor.equals("-")) {
                            return Optional.of(Double.parseDouble(valor));
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor numérico: " + e.getMessage());
        }
        return Optional.empty();
    }
}