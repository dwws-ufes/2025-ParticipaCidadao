import { Component, OnInit } from '@angular/core';
import { IssueService } from '../../../services/issue.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-reported-issues',
  templateUrl: './reported-issues.component.html',
  styleUrls: ['./reported-issues.component.scss']
})
export class ReportedIssuesComponent implements OnInit {
  issues: any[] = [];
  error: string | null = null;

  constructor(private issueService: IssueService, private http: HttpClient) {}

  // Retorna as chaves de dadosEnriquecidos que não são exibidas explicitamente
  getOutrosCampos(dados: any): string[] {
    if (!dados) return [];
    const ignorar = [
      'nome', 'uf', 'populacao', 'area', 'areaTerritorial',
      'pibPerCapita', 'regiao', 'dbpediaUri', 'wikidataUri'
    ];
  return Object.keys(dados).filter(key => !ignorar.includes(key));
  }

  ngOnInit() {
    this.issueService.getIssues().subscribe({
      next: (data) => this.issues = data,
      error: (err) => this.error = 'Erro ao carregar problemas.'
    });
  }

  enriquecerIssue(issue: any) {
    // Chama endpoint backend para enriquecer dados da cidade
    this.http.get<any>(`http://localhost:8080/api/ibge/cidade/${encodeURIComponent(issue.city)}/dados-completos`).subscribe({
      next: (dados) => {
        issue.dadosEnriquecidos = dados;
      },
      error: (err) => {
        alert('Não foi possível enriquecer os dados IBGE para esta cidade.');
      }
    });
  }

  visualizarRdf(): void {
    window.open('http://localhost:8080/issues/rdf', '_blank');
  }
}
