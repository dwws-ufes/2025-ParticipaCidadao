import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewIssueComponent } from './new-issue/new-issue.component';
import { UserIssuesComponent } from './user-issues/user-issues.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Problema'
    },
    children: [
      {
        path: 'novo',
        component: NewIssueComponent
      },
      {
        path: 'relatados',
        component: UserIssuesComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IssueRoutingModule { }
