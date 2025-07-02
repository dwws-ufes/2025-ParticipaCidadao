import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IssueRoutingModule } from './issue-routing.module';
import { NewIssueComponent } from './new-issue/new-issue.component';
import { ReactiveFormsModule } from '@angular/forms';

import { CardModule, FormModule, GridModule, ButtonModule } from '@coreui/angular';

@NgModule({
  declarations: [
    NewIssueComponent
  ],
  imports: [
    CommonModule,
    IssueRoutingModule,
    ReactiveFormsModule,
    CardModule,
    FormModule,
    GridModule,
    ButtonModule
  ]
})
export class IssueModule { }
