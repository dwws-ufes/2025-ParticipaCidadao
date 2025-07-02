import { Component, OnInit } from '@angular/core';
import { IssueService } from '../../../services/issue.service';

@Component({
  selector: 'app-reported-issues',
  templateUrl: './reported-issues.component.html',
  styleUrls: ['./reported-issues.component.scss']
})
export class ReportedIssuesComponent implements OnInit {
  issues: any[] = [];
  error: string | null = null;

  constructor(private issueService: IssueService) {}

  ngOnInit() {
    this.issueService.getIssues().subscribe({
      next: (data) => this.issues = data,
      error: (err) => this.error = 'Erro ao carregar problemas.'
    });
  }
}
