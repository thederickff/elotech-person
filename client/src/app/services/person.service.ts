import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { map, switchMap, take, tap } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { Person } from "../models/person.model";

@Injectable({ providedIn: 'root' })
export class PersonService {

  private mPersons = new BehaviorSubject<Person[]>([]);
  private mPage = new BehaviorSubject<any>([]);
  private mBaseUrl = `${environment.baseUrl}/persons`;

  get persons() {
    return this.mPersons.asObservable();
  }

  get page() {
    return this.mPage.asObservable();
  }

  constructor(
    private http: HttpClient
  ) { }

  fetchAll(page = 0, term?: string): Observable<void> {
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

  delete(id: number): Observable<void> {
    return this.http.delete(`${this.mBaseUrl}/${id}`).pipe(
      switchMap(() => {
        return this.persons;
      }),
      take(1),
      map(persons => {
        this.mPersons.next(persons.filter(person => person.id !== id));
      })
    )
  }
}