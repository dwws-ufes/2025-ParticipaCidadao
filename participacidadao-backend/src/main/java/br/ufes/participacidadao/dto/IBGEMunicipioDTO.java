package br.ufes.participacidadao.dto;

public class IBGEMunicipioDTO {
    // Classe principal
    private Long id;
    private String nome;
    private Microrregiao microrregiao;
    private RegiaoImediata regiaoImediata;
    public RegiaoImediata getRegiaoImediata() {
        return regiaoImediata;
    }

    public void setRegiaoImediata(RegiaoImediata regiaoImediata) {
        this.regiaoImediata = regiaoImediata;
    }
    public static class RegiaoImediata {
        private RegiaoIntermediaria regiaoIntermediaria;

        public RegiaoIntermediaria getRegiaoIntermediaria() {
            return regiaoIntermediaria;
        }

        public void setRegiaoIntermediaria(RegiaoIntermediaria regiaoIntermediaria) {
            this.regiaoIntermediaria = regiaoIntermediaria;
        }
    }

    public static class RegiaoIntermediaria {
        private Microrregiao.Mesorregiao.UF uf;

        public Microrregiao.Mesorregiao.UF getUF() {
            return uf;
        }

        public void setUF(Microrregiao.Mesorregiao.UF uf) {
            this.uf = uf;
        }
    }

    // Getters e Setters da classe principal
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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