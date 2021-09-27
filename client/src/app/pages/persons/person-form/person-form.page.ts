import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { appCatchError, appShowLoading, appShowMessage } from "src/app/app.functions";
import { Contact, Person } from "src/app/models/person.model";
import { PersonService } from "src/app/services/person.service";
import { ContactFormModal } from "./contact-form-modal/contact-form.modal";

@Component({
  selector: 'app-person-form',
  templateUrl: './person-form.page.html',
  styleUrls: ['./person-form.page.scss']
})
export class PersonForm implements OnInit {

  form: FormGroup;
  person: Person;
  contacts: Contact[] = [];
  isEdit = false;
  isLoading = false;

  constructor(
    private personService: PersonService,
    private route: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.route.paramMap.subscribe(paramMap => {
      if (paramMap.has('id')) {
        this.isEdit = true;
        this.isLoading = true;
        const loading = appShowLoading(this.dialog);
        
        this.personService
        .find(+paramMap.get('id'))
        .subscribe(person => {
          this.person = person;
          this.isLoading = false;
          loading.close();
          this.initForm(person);
        }, error => {
            loading.close();
            this.isLoading = false;
            appCatchError(this.dialog)(error);
          });
      } else {
        this.initForm();
      }
    });
  }

  private initForm(person?: Person) {
    this.form = new FormGroup({
      id: new FormControl(person ? person.id : null, {}),
      name: new FormControl(person ? person.name : null, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      socialSecurityNumber: new FormControl(person ? person.socialSecurityNumber : null, {
        updateOn: 'change',
        validators: [Validators.required]
      }),
      dateOfBirth: new FormControl(person ? person.dateOfBirth.toISOString().split('T')[0] : null, {
        updateOn: 'change',
        validators: [Validators.required]
      })
    });

    if (person) {
      this.contacts = person.contacts;
    }
  }

  addContact(index?: number) {
    const ref = this.dialog.open(ContactFormModal, {
      width: '50em',
      data: this.contacts[index]
    });

    ref.afterClosed().subscribe(result => {
      if (index === 0 || index > 0) {
        this.contacts[index] = result;
      } else {
        this.contacts.push(result);
      }
    });
  }

  isInvalid(field: string){
    return this.form.get(field).invalid && this.form.get(field).touched;
  }

  submit() {
    if (!this.form || this.form.invalid) {
      return;
    }

    const loading = appShowLoading(this.dialog);
    this.isLoading = true;
    this.personService.save({
      ...this.person,
      ...this.form.value,
      contacts: this.contacts
    }).subscribe(() => {
        loading.close();
        this.isLoading = false;
        this.router.navigate(['/persons']);
        
        appShowMessage(this.dialog, {
          header: `${this.isEdit ? 'Editar' : 'Criar' } pessoa`,
          message: `${this.isEdit ? 'Editado' : 'criado' } com sucesso`,
          buttons: ['Ok']
        });
      }, error => {
        this.isLoading = false;
        loading.close();
        appCatchError(this.dialog)(error);
      }
    );
  }
}