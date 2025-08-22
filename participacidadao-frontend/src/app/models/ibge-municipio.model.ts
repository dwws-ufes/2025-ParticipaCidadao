export interface IBGEMunicipio {
    id: number;
    name: string;
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
