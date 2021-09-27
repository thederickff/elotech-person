import { ChangeDetectorRef, Component, HostListener, OnInit } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { MatDrawerMode } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  sideBarOpen = true;
  mode: MatDrawerMode = 'over';

  constructor(
    private mpi: MatPaginatorIntl,
    private ref: ChangeDetectorRef,
    private router: Router
  ) { }

  ngOnInit() {
    this.onWindowResize();

    this.mpi.itemsPerPageLabel = 'Items por Página';
    this.mpi.nextPageLabel = 'Próxima Página';
    this.mpi.previousPageLabel = 'Página Anterior';
    this.mpi.firstPageLabel = 'Primeira Página';
    this.mpi.lastPageLabel = 'Última Página';
    this.mpi.changes.next();
  }

  ngAfterContentChecked() : void {
    this.ref.detectChanges();
  }

  sideBarToggle() {
    this.sideBarOpen = !this.sideBarOpen;

    if (this.mode === 'side') {
      setTimeout(() => {
        window.dispatchEvent(
          new Event('resize')
        );
      }, 200);
    }
  }

  @HostListener('window:resize')
  onWindowResize() {
    if (window.innerWidth <= 960) {
      this.sideBarOpen = false;
      this.mode = 'over';
    } else {
      this.mode = 'side';
    }
  }
  
  navigate(url: string) {
    if (this.mode == 'over') {
      this.sideBarOpen = false;
    }

    this.router.navigate([url]);
  }
}
