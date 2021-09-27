import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { Person } from "../models/person.model";

@Injectable({ providedIn: 'root' })
export class PersonService {

  private mPersons = new BehaviorSubject<Person[]>([]);
  private mBaseUrl = `${environment.baseUrl}/persons`;

  get persons() {
    return this.mPersons.asObservable();
  }

  constructor(
    private http: HttpClient
  ) { }

  fetchAll(page = 0, term?: string) {
    const params = `page=${page}${term ? `&term=${term}` : ''}`;

    return this.http.get<{ content: any[] }>(`${this.mBaseUrl}?${params}`).pipe(
      map(res => {
        const persons: Person[] = [];

        res.content.forEach(item => {
          persons.push(new Person(item));
        });

        this.mPersons.next(persons);
      })
    )
  }
}