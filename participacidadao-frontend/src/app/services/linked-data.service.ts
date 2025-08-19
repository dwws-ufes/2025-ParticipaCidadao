import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DadosInterligados } from '../models/dados-interligados.model'; // Adjust the path as needed

@Injectable({
    providedIn: 'root'
})

export class LinkedDataService {
    private apiUrl = 'https://localhost:8080/api/linked-data';

    constructor(private http: HttpClient) { }

    buscarDadosCidade(cityName: string): Observable<DadosInterligados> {
        return this.http.get<DadosInterligados>(`${this.apiUrl}/cidade/${cityName}`);
    }

    executarConsultaSPARQL(query: string): Observable<any> {
        return this.http.post<any>(`${this.apiUrl}/sparql`, query, {
            headers: { 'Content-Type': 'application/sparql-query' },
            responseType: 'json'
        });
    }
}
