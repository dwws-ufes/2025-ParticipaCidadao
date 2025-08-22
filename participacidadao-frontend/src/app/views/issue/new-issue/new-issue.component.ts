import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Issue from '../../../models/Issue';
import { IssueService } from '../../../services/issue.service';

import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, startWith } from 'rxjs/operators';
import { LinkedDataService } from '../../../services/linked-data.service';
import { IBGEMunicipio } from '../../../models/ibge-municipio.model';

import { DadosEnriquecidos } from '../../../models/dados-enriquecidos.model';

@Component({
  selector: 'app-new-issue',
  templateUrl: './new-issue.component.html',
  styleUrls: ['./new-issue.component.scss'],
})

export class NewIssueComponent {

  public issue?: Issue;
  public issueForm!: FormGroup;
  public successMessage: string | null = null;
  public errorMessage: string | null = null;

  // Propriedades para dados interligados
  public cidadesFiltradas$!: Observable<IBGEMunicipio[]>;
  public cidadeSelecionada: IBGEMunicipio | null = null;
  public dadosEnriquecidos: DadosEnriquecidos | null = null;
  public carregandoDados = false;
  public mostrarDadosCidade = false;

  private cidadeSearchSubject = new Subject<string>();

  constructor(private formBuilder: FormBuilder, private issueService: IssueService, private linkedDataService: LinkedDataService) {
    this.createForm();
    this.setupCidadeSearch();
  }

  onCidadeChange(nomeCidade: string) {
    // sempre que o usuário digita, disparamos no Subject
    this.cidadeSearchSubject.next(nomeCidade);
  }

  ngOnInit() { }

  public createForm() {
    this.issueForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      description: ['', [Validators.required]],
      street: ['', [Validators.required]],
      city: ['', [Validators.required]],
      neighborhood: ['', Validators.required],
      refPoint: ['', [Validators.required]],
      createdBy: ['1', null],
      // The following fields will be set automatically on submit
    })

    // Observar mudanças no campo cidade
    this.issueForm.get('city')?.valueChanges.subscribe(value => {
      if (typeof value === 'string' && value.length >= 2) {
        this.cidadeSearchSubject.next(value);
      } else {
        this.limparDadosCidade();
      }
    });
  }

  private setupCidadeSearch() {
    this.cidadesFiltradas$ = this.cidadeSearchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(termo => this.linkedDataService.buscarMunicipios(termo))
    );
  }

  public selecionarCidade(municipio: IBGEMunicipio) {
    this.cidadeSelecionada = municipio;
    this.issueForm.patchValue({
      city: municipio.name
    });

    // Buscar dados enriquecidos da cidade
    this.carregarDadosEnriquecidos(municipio.name);
  }

  // public onCidadeInput(event: any) {
  //   const valor = event.target.value;
  //   if (valor && valor.length >= 2) {
  //     this.cidadeSearchSubject.next(valor);
  //   }
  // }

  onCidadeInput(evt: Event): void {
    const value = (evt.target as HTMLInputElement | null)?.value ?? '';
    this.cidadeSelecionada = null;
    this.mostrarDadosCidade = value.length >= 2;
    if (!this.mostrarDadosCidade) {
      this.dadosEnriquecidos = null;
    }
  }

  private carregarDadosEnriquecidos(nomeCidade: string) {
    this.carregandoDados = true;
    this.mostrarDadosCidade = false;

    this.linkedDataService.buscarDadosCompletos(nomeCidade).subscribe({
      next: (dados) => {
        this.dadosEnriquecidos = dados;
        this.mostrarDadosCidade = true;
        this.carregandoDados = false;
        console.log('Dados enriquecidos carregados:', dados);
      },
      error: (erro) => {
        console.error('Erro ao carregar dados da cidade:', erro);
        this.carregandoDados = false;
        this.dadosEnriquecidos = { cidadeNome: nomeCidade };
        this.mostrarDadosCidade = true;
      }
    });
  }

  private limparDadosCidade() {
    this.cidadeSelecionada = null;
    this.dadosEnriquecidos = null;
    this.mostrarDadosCidade = false;
  }

  public displayFn(municipio?: IBGEMunicipio): string {
    return municipio ? `${municipio.name} - ${municipio.microrregiao.mesorregiao.UF.sigla}` : '';
  }

  public onSubmit() {
    this.successMessage = null;
    this.errorMessage = null;
    console.log('Submitting issue form', this.issueForm.value, 'Valid:', this.issueForm.valid);
    if (this.issueForm.valid) {
      const formValue = this.issueForm.value;
      const issueData = {
        ...formValue,
        imageUrl: 'default.jpg',
        status: 'REPORTADO', // use allowed enum value
        createdAt: new Date().toISOString().split('T')[0],
        createdBy: { id: Number(formValue.createdBy) } // send as object
      };
      console.log('Payload sent:', issueData);
      this.issueService.createIssue(issueData).subscribe({
        next: (res) => {
          this.successMessage = 'Problema criado com sucesso!';
          this.issueForm.reset();
          // Optionally, reset createdBy to default value
          this.issueForm.patchValue({ createdBy: '1' });
          console.log('Issue created:', res);
        },
        error: (err) => {
          this.errorMessage = 'Erro ao criar o problema.';
          console.error('Error creating issue:', err);
        }
      });
    } else {
      this.issueForm.markAllAsTouched();
    }
  }
}
