export interface DadosEnriquecidos {
    id?: number;
    cidadeNome: string;
    populacao?: number;
    area?: number;
    pibPerCapita?: number;
    uf?: string;
    regiao?: string;
    codigoIbge?: number;
    dbpediaUri?: string;
    wikidataUri?: string;
    dataAtualizacao?: string;
}