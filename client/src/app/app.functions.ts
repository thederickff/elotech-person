import { MatDialog } from "@angular/material/dialog"
import { AlertComponent } from "./components/alert/alert.component";

export const appCatchError = (
  dialog: MatDialog,
  message = 'Erro desconhecido',
  title = 'Ocorreu um Erro'
) => {
  return (error: Error) => {

    console.error(error);

    dialog.open(AlertComponent, {
      width: '20em',
      data: {
        title,
        message,
        buttons: ['OK']
      }
    });
  }
}