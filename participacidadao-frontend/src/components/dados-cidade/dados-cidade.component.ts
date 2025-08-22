import { Component, Input } from "@angular/core";
import { OnInit, OnChanges, SimpleChanges } from "@angular/core";
import { DadosEnriquecidos } from "../../app/models/dados-enriquecidos.model";
import { LinkedDataService } from "../../app/services/linked-data.service";

@Component({
    selector: 'app-dados-cidade',
    templateUrl: './dados-cidade.component.html',
    styleUrls: ['./dados-cidade.component.css']
})

export class DadosCidadeComponent implements OnInit, OnChanges {
    @Input() nomeCidade: string = '';
    @Input() mostrarDados: boolean = false;

    dadosEnriquecidos: DadosEnriquecidos | null = null;
    carregandoDados: boolean = false;
    erro: string | null = null;

    constructor(private linkedDataService: LinkedDataService) { }

    ngOnInit(): void {
        if (this.nomeCidade && this.mostrarDados) {
            this.carregarDados();
        }
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['nomeCidade'] || changes['mostrarDados']) {
            if (this.nomeCidade && this.mostrarDados && this.nomeCidade.length >= 3) {
                this.carregarDados();
            } else {
                this.dadosEnriquecidos = null;
                this.erro = null;
            }
        }
    }

    carregarDados() {
        if (!this.nomeCidade || this.nomeCidade.length < 3) {
            return;
        }

        this.carregandoDados = true;
        this.erro = null;

        console.log('Buscando dados para:', this.nomeCidade);

        this.linkedDataService.buscarDadosCompletos(this.nomeCidade).subscribe({
            next: (dados) => {
                console.log('Dados recebidos:', dados);
                this.dadosEnriquecidos = dados;
                this.carregandoDados = false;
            },
            error: (error) => {
                console.error('Erro ao buscar dados:', error);
                this.erro = 'Não foi possível carregar os dados da cidade.';
                this.carregandoDados = false;

                // Criar dados básicos mesmo com erro
                this.dadosEnriquecidos = {
                    cidadeNome: this.nomeCidade
                };
            }
        });
    }
}