import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { DadosEnriquecidos } from '../models/dados-enriquecidos.model';
import { map, catchError, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { IBGEMunicipio } from '../models/ibge-municipio.model';

@Injectable({
    providedIn: 'root'
})
export class LinkedDataService {
    private apiUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) { }

    // Buscar municípios para autocomplete
    buscarMunicipios(termo: string, uf?: string): Observable<IBGEMunicipio[]> {
        let url = `${this.apiUrl}/ibge/municipios`;
        const params: string[] = [];

        if (uf) params.push(`uf=${uf}`);
        if (termo && termo.length >= 2) params.push(`nome=${encodeURIComponent(termo)}`);

        if (params.length > 0) {
            url += '?' + params.join('&');
        }

        return this.http.get<IBGEMunicipio[]>(url).pipe(
            map(municipios => municipios.filter(m =>
                m.nome.toLowerCase().includes(termo.toLowerCase())
            )),
            catchError(error => {
                console.error('Erro ao buscar municípios:', error);
                return of([]);
            })
        );
    }

    // Buscar dados completos da cidade
    buscarDadosCompletos(nomeCidade: string): Observable<DadosEnriquecidos> {
        return this.http.get<DadosEnriquecidos>(
            `${this.apiUrl}/ibge/cidade/${encodeURIComponent(nomeCidade)}/dados-completos`
        ).pipe(
            catchError(error => {
                console.error('Erro ao buscar dados da cidade:', error);
                return of({
                    cidadeNome: nomeCidade
                } as DadosEnriquecidos);
            })
        );
    }

    // Executar consulta SPARQL
    executarConsultaSparql(query: string): Observable<any> {
        return this.http.post(`${this.apiUrl}/linked-data/sparql`, query, {
            headers: { 'Content-Type': 'application/sparql-query' }
        });
    }
}