package br.ufes.participacidadao.dto;

import java.util.List;
import java.util.Map;

public class IBGEPopulacaoDTO {
    private String id;
    private String variavel;
    private String unidade;
    private List<Resultado> resultados;

    // getters/setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariavel() {
        return variavel;
    }

    public void setVariavel(String variavel) {
        this.variavel = variavel;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    public static class Resultado {
        private List<Serie> series;

        public List<Serie> getSeries() {
            return series;
        }

        public void setSeries(List<Serie> series) {
            this.series = series;
        }

        public static class Serie {
            private Map<String, String> localidade;
            private Map<String, String> serie;

            public Map<String, String> getLocalidade() {
                return localidade;
            }

            public void setLocalidade(Map<String, String> localidade) {
                this.localidade = localidade;
            }

            public Map<String, String> getSerie() {
                return serie;
            }

            public void setSerie(Map<String, String> serie) {
                this.serie = serie;
            }
        }
    }
}
