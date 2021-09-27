import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Person } from "src/app/models/person.model";

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.modal.html',
  styleUrls: ['./contacts.modal.scss']
})
export class ContactsModal {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Person
  ) {
    console.log(this.data);
  }

}