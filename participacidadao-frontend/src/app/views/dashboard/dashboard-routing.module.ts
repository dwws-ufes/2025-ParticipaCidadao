import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ReportedIssuesComponent } from '../issue/reported-issues/reported-issues.component';

const routes: Routes = [
  {
    path: '',
    component: ReportedIssuesComponent,
    data: {
      title: 'Problemas'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {
}
