import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Issue from '../../../models/Issue';
import { IssueService } from '../../../services/issue.service';

@Component({
  selector: 'app-new-issue',
  templateUrl: './new-issue.component.html',
  styleUrls: ['./new-issue.component.scss']
})
export class NewIssueComponent {

  public issue?: Issue;
  public issueForm!: FormGroup;
  public successMessage: string | null = null;
  public errorMessage: string | null = null;

  constructor(private formBuilder: FormBuilder, private issueService: IssueService) {
    this.createForm();
  }

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
