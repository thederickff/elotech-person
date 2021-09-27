import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { SharedModule } from "src/app/shared/shared.module";
import { ContactsModal } from "./contacts-modal/contacts.modal";
import { ContactFormModal } from "./person-form/contact-form-modal/contact-form.modal";
import { PersonForm } from "./person-form/person-form.page";
import { PersonsPage } from "./persons.page";

const routes: Routes = [
  {
    path: '',
    component: PersonsPage
  },
  {
    path: 'new',
    component: PersonForm
  },
  {
    path: ':id',
    component: PersonForm
  }
];

@NgModule({
  declarations: [PersonsPage, ContactsModal, PersonForm, ContactFormModal],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    SharedModule
  ]
})
export class PersonsPageModule { }