export interface IBGEMunicipio {
    id: number;
    nome: string;
    microrregiao: {
        mesorregiao: {
            UF: {
                sigla: string;
                nome: string;
                regiao: {
                    nome: string;
                    sigla: string;
                }
            }
        }
    }
}
