import { Component, Inject } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Contact } from "src/app/models/person.model";

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.modal.html',
  styleUrls: ['./contact-form.modal.scss']
})
export class ContactFormModal {

  form: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Contact,
    private dialogRef: MatDialogRef<any>
  ) {
    this.form = new FormGroup({
      name: new FormControl(this.data ? this.data.name : null, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      telephone: new FormControl(this.data ? this.data.telephone : null, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      email: new FormControl(this.data ? this.data.email : null, {
        updateOn: 'change',
        validators: [Validators.required]
      })
    });
  }
  
  isInvalid(field: string){
    return this.form.get(field).invalid && this.form.get(field).touched;
  }

  submit() {
    this.dialogRef.close(this.form.value);
  }  
}