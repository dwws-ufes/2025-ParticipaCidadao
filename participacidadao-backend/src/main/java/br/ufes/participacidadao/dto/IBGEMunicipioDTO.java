package br.ufes.participacidadao.dto;

public class IBGEMunicipioDTO {
    // Classe principal
    private Long id;
    private String name;
    private Microrregiao microrregiao;

    // Getters e Setters da classe principal
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Microrregiao getMicrorregiao() {
        return microrregiao;
    }

    public void setMicrorregiao(Microrregiao microrregiao) {
        this.microrregiao = microrregiao;
    }

    public static class Microrregiao {
        private Mesorregiao mesorregiao;

        // Getters e Setters da Microrregiao
        public Mesorregiao getMesorregiao() {
            return mesorregiao;
        }

        public void setMesorregiao(Mesorregiao mesorregiao) {
            this.mesorregiao = mesorregiao;
        }

        public static class Mesorregiao {
            private UF UF;

            // Getters e Setters da Mesorregiao
            public UF getUF() {
                return UF;
            }

            public void setUF(UF UF) {
                this.UF = UF;
            }

            public static class UF {
                private String sigla;
                private String nome;
                private Regiao regiao;

                // Getters e Setters da UF
                public String getSigla() {
                    return sigla;
                }

                public void setSigla(String sigla) {
                    this.sigla = sigla;
                }

                public String getNome() {
                    return nome;
                }

                public void setNome(String nome) {
                    this.nome = nome;
                }

                public Regiao getRegiao() {
                    return regiao;
                }

                public void setRegiao(Regiao regiao) {
                    this.regiao = regiao;
                }

                public static class Regiao {
                    private String nome;
                    private String sigla;

                    // Getters e Setters da Regiao
                    public String getNome() {
                        return nome;
                    }

                    public void setNome(String nome) {
                        this.nome = nome;
                    }

                    public String getSigla() {
                        return sigla;
                    }

                    public void setSigla(String sigla) {
                        this.sigla = sigla;
                    }
                }
            }
        }
    }
}