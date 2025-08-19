import { Component, Input } from "@angular/core";
import { OnInit } from "@angular/core";
import { DadosInterligados } from "../../app/models/dados-interligados.model";
import { LinkedDataService } from "../../app/services/linked-data.service";

@Component({
    selector: 'app-dados-cidade',
    templateUrl: './dados-cidade.component.html',
    styleUrls: ['./dados-cidade.component.css']
})

export class DadosCidadeComponent implements OnInit {
    @Input() cityName: string = '';
    dadosInterligados: DadosInterligados | null = null;

    constructor(private linkedDataService: LinkedDataService) { }

    ngOnInit(): void {
        if (this.cityName) {
            this.carregarDados();
        }
    }

    carregarDados() {
        this.linkedDataService.buscarDadosCidade(this.cityName).subscribe(dados => {
            this.dadosInterligados = dados;
        });
    }
}