import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Issue from '../../../models/Issue';

@Component({
  selector: 'app-new-issue',
  templateUrl: './new-issue.component.html',
  styleUrls: ['./new-issue.component.scss']
})
export class NewIssueComponent {

  public issue?: Issue;

  public issueForm!: FormGroup;

  constructor(private formBuilder: FormBuilder) {
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
      createdBy: ['1', null]
    })
  }

  public onSubmit() {
    console.log(this.issueForm.value);
  }
}
