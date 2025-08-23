package br.ufes.participacidadao.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

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

    // Cache simples para cidades já consultadas
    private final Map<String, IBGECidadeDadosDTO> cacheCidade = new ConcurrentHashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    // Buscar município por nome e UF (se informado)
    public Optional<IBGEMunicipioDTO> buscarMunicipioPorNome(String nome, String uf) {
        try {
            String url = IBGE_LOCALIDADES_URL + "?nome=" + URLEncoder.encode(nome, StandardCharsets.UTF_8);
            ResponseEntity<IBGEMunicipioDTO[]> response = restTemplate.getForEntity(url, IBGEMunicipioDTO[].class);
            if (response.getBody() != null && response.getBody().length > 0) {
                // Busca por nome exato (ignorando acentos e caixa)
                String nomeBusca = removerAcentos(nome).trim().toLowerCase();
                IBGEMunicipioDTO municipioExato = null;
                for (IBGEMunicipioDTO m : response.getBody()) {
                    String nomeMunicipio = removerAcentos(m.getNome()).trim().toLowerCase();
                    boolean nomeIgual = nomeMunicipio.equals(nomeBusca);
                    boolean ufIgual = true;
                    if (uf != null && !uf.isBlank()) {
                        String sigla = null;
                        try {
                            sigla = m.getMicrorregiao().getMesorregiao().getUF().getSigla();
                        } catch (Exception ignore) {}
                        ufIgual = uf.equalsIgnoreCase(sigla);
                    }
                    if (nomeIgual && ufIgual) {
                        municipioExato = m;
                        break;
                    }
                }
                if (municipioExato != null) {
                    return Optional.of(municipioExato);
                } else {
                    // Se não achou nome exato, retorna o primeiro
                    return Optional.of(response.getBody()[0]);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar município: " + e.getMessage());
        }
        return Optional.empty();
    }

    // Função utilitária para remover acentos
    private String removerAcentos(String str) {
        if (str == null) return null;
        return java.text.Normalizer.normalize(str, java.text.Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "");
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
                        String populacaoStr = serie.getSerie().get("2021");
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

    // Metodo para buscar dados completos de uma cidade (com cache, logs e UF opcional)
    public Optional<IBGECidadeDadosDTO> buscarDadosCompletosCidade(String nomeCidade) {
        return buscarDadosCompletosCidade(nomeCidade, null);
    }

    public Optional<IBGECidadeDadosDTO> buscarDadosCompletosCidade(String nomeCidade, String uf) {
        String cacheKey = nomeCidade.trim().toLowerCase() + (uf != null ? ":" + uf.trim().toLowerCase() : "");
        if (cacheCidade.containsKey(cacheKey)) {
            System.out.println("[IBGEService] Cache hit para cidade: " + cacheKey);
            return Optional.of(cacheCidade.get(cacheKey));
        }
        System.out.println("[IBGEService] Buscando dados do IBGE para cidade: " + nomeCidade + (uf != null ? ", UF: " + uf : ""));
        IBGECidadeDadosDTO dto = new IBGECidadeDadosDTO();
        try {
            Optional<IBGEMunicipioDTO> municipioOpt = buscarMunicipioPorNome(nomeCidade, uf);
            if (municipioOpt.isEmpty()) {
                System.err.println("[IBGEService] Município não encontrado: " + nomeCidade + (uf != null ? ", UF: " + uf : ""));
                return Optional.empty();
            }
            IBGEMunicipioDTO municipio = municipioOpt.get();
            dto.setNome(municipio.getNome());
            // Checagem segura para UF e Região em múltiplos caminhos
            String siglaUf = null;
            String regiao = null;
            try {
                // Tenta pelo caminho microrregiao.mesorregiao.UF
                if (municipio.getMicrorregiao() != null && municipio.getMicrorregiao().getMesorregiao() != null && municipio.getMicrorregiao().getMesorregiao().getUF() != null) {
                    siglaUf = municipio.getMicrorregiao().getMesorregiao().getUF().getSigla();
                    if (municipio.getMicrorregiao().getMesorregiao().getUF().getRegiao() != null) {
                        regiao = municipio.getMicrorregiao().getMesorregiao().getUF().getRegiao().getNome();
                    }
                }
                // Se não encontrou, tenta pelo caminho regiao-imediata.regiao-intermediaria.UF
                if ((siglaUf == null || regiao == null) && municipio.getRegiaoImediata() != null && municipio.getRegiaoImediata().getRegiaoIntermediaria() != null && municipio.getRegiaoImediata().getRegiaoIntermediaria().getUF() != null) {
                    if (siglaUf == null) {
                        siglaUf = municipio.getRegiaoImediata().getRegiaoIntermediaria().getUF().getSigla();
                    }
                    if (regiao == null && municipio.getRegiaoImediata().getRegiaoIntermediaria().getUF().getRegiao() != null) {
                        regiao = municipio.getRegiaoImediata().getRegiaoIntermediaria().getUF().getRegiao().getNome();
                    }
                }
            } catch (Exception e) {
                System.err.println("[IBGEService] Erro ao extrair UF/Região: " + e.getMessage());
            }
            dto.setUf(siglaUf);
            dto.setRegiao(regiao);

            // Não preenche mais populacao, pibPerCapita ou areaTerritorial
        } catch (Exception e) {
            System.err.println("[IBGEService] Erro inesperado ao buscar dados completos: " + e.getMessage());
        }
        cacheCidade.put(cacheKey, dto);
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