import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, of } from "rxjs";
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

  find(id: number): Observable<Person> {
    return this.http.get(`${this.mBaseUrl}/${id}`).pipe(
      map(res => {
        return new Person(res);
      })
    );
  }

  save(person: Person): Observable<void> {
    let obs: Observable<any>;

    if (person.id) {
      obs = this.http.put(`${this.mBaseUrl}/${person.id}`, person);
    } else {
      obs = this.http.post(`${this.mBaseUrl}`, person);
    }

    return obs.pipe(
      switchMap(() => {
        return this.fetchAll();
      })
    );
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