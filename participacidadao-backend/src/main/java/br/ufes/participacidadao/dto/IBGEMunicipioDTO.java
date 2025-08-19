package main.java.br.ufes.participacidadao.dto;

public class IBGEMunicipioDTO {
    private Long id;
    private String name;
    private Microrregiao microrregiao;

    public static class Microrregiao {
        private Mesorregiao mesorregiao;

        public static class Mesorregiao {
            private UF UF;

            public static class UF {
                private String sigla;
                private String nome;
                private String regiao;

                public static class Regiao {
                    private String nome;
                    private String sigla;
                }
            }
        }
    }

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

    public Microrregiao.Mesorregiao getMesorregiao() {
        return microrregiao != null ? microrregiao.mesorregiao : null;
    }

    public void setMesorregiao(Microrregiao.Mesorregiao mesorregiao) {
        if (microrregiao != null) {
            microrregiao.mesorregiao = mesorregiao;
        }
    }

    public Microrregiao.Mesorregiao.UF getUF() {
        return microrregiao != null ? microrregiao.mesorregiao.UF : null;
    }

    public void setUF(Microrregiao.Mesorregiao.UF UF) {
        if (microrregiao != null && microrregiao.mesorregiao != null) {
            microrregiao.mesorregiao.UF = UF;
        }
    }

    public Microrregiao.Mesorregiao.UF.Regiao getRegiao() {
        return microrregiao != null && microrregiao.mesorregiao != null ? microrregiao.mesorregiao.UF.regiao : null;
    }

    public void setRegiao(Microrregiao.Mesorregiao.UF.Regiao regiao) {
        if (microrregiao != null && microrregiao.mesorregiao != null && microrregiao.mesorregiao.UF != null) {
            microrregiao.mesorregiao.UF.regiao = regiao;
        }
    }

    public String getUFName() {
        return microrregiao != null && microrregiao.mesorregiao != null && microrregiao.mesorregiao.UF != null
                ? microrregiao.mesorregiao.UF.nome
                : null;
    }

    public String getUFAbbreviation() {
        return microrregiao != null && microrregiao.mesorregiao != null && microrregiao.mesorregiao.UF != null
                ? microrregiao.mesorregiao.UF.sigla
                : null;
    }

    public String getRegiaoName() {
        return microrregiao != null && microrregiao.mesorregiao != null && microrregiao.mesorregiao.UF != null
                && microrregiao.mesorregiao.UF.regiao != null ? microrregiao.mesorregiao.UF.regiao.nome : null;
    }

    public String getRegiaoAbbreviation() {
        return microrregiao != null && microrregiao.mesorregiao != null && microrregiao.mesorregiao.UF != null
                && microrregiao.mesorregiao.UF.regiao != null ? microrregiao.mesorregiao.UF.regiao.sigla : null;
    }
}