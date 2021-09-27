import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { Subscription } from "rxjs";
import { appCatchError, appShowLoading, appShowMessage } from "src/app/app.functions";
import { Person } from "src/app/models/person.model";
import { PersonService } from "src/app/services/person.service";

@Component({
  selector: 'app-persons',
  templateUrl: './persons.page.html',
  styleUrls: ['./persons.page.scss']
})
export class PersonsPage implements OnInit, OnDestroy {

  displayedColumns: string[] = ['id', 'name', 'socialSecurityNumber', 'dateOfBirth', 'actions'];
  dataSource: MatTableDataSource<Person>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  subs: Subscription[] = [];

  constructor(
    private personService: PersonService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource([]);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource.data = this.emptyTable();

    this.subs.push(
      this.personService.persons.subscribe(persons => {
        this.dataSource.data = persons;

        if (persons.length === 0) {
          this.dataSource.data = this.emptyTable();
        }
      })
    );


    const loading = appShowLoading(this.dialog);
    this.personService.fetchAll().subscribe(() => {
      loading.close();
    }, error => {
      loading.close();
      appCatchError(this.dialog)(error);
    });
  }

  ngOnDestroy() {
    this.subs.forEach(sub => {
      sub.unsubscribe();
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  delete(id: number) {
    appShowMessage(this.dialog, {
      header: 'Apagar Pessoa',
      message: 'Deseja realmente apagar essa pessoa?',
      buttons: [
        {
          text: 'Não'
        },
        {
          text: 'Sim',
          handler: () => {
            const loading = appShowLoading(this.dialog);
            this.personService.delete(id).subscribe(() => {
              loading.close();
            }, error => {
              loading.close();
              appCatchError(this.dialog)(error);
            });
          }
        }
      ]
    });
  }

  private emptyTable(): any[] {
    return [{ id: 'Nenhuma pessoa encontrada!' }];
  }
}