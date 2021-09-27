import { Component } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.modal.html',
  styleUrls: ['./contact-form.modal.scss']
})
export class ContactFormModal {

  form: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<any>
  ) {
    this.form = new FormGroup({
      name: new FormControl(null, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      telephone: new FormControl(null, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      email: new FormControl(null, {
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