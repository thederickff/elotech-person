export class Contact {
  name: string;
  telephone: string;
  email: string;

  constructor(res: any) {
    this.name = res.name;
    this.telephone = res.telephone;
    this.email = res.email;
  }
}

export class Person {
  id: string;
  name: string;
  socialSecurityNumber: string;
  dateOfBirth: Date;
  contacts: Contact[];

  constructor(res: any) {
    this.id = res.id;
    this.name = res.name;
    this.socialSecurityNumber = res.socialSecurityNumber;
    this.dateOfBirth = new Date(res.dateOfBirth);
    
    if (res.contacts) {
      this.contacts = res.contacts.map((contact: any) => new Contact(contact));
    } else {
      this.contacts = [];
    }
  }
}