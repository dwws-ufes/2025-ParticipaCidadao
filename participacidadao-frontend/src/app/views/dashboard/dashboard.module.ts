import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';

import { CardModule, FormModule, GridModule, ButtonModule } from '@coreui/angular';
import { IssueRoutingModule } from '../issue/issue-routing.module';
import { ReportedIssuesComponent } from '../issue/reported-issues/reported-issues.component';

@NgModule({
  declarations: [ ReportedIssuesComponent ],
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
export class DashboardModule {
}
