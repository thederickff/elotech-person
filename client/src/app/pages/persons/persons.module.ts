import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PersonsPage } from "./persons.page";

const routes: Routes = [
  {
    path: '',
    component: PersonsPage
  }
];

@NgModule({
  declarations: [PersonsPage],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class PersonsPageModule { }