import { MatDialog } from "@angular/material/dialog"
import { AlertComponent } from "./components/alert/alert.component";

export const appShowMessage = (
  dialog: MatDialog,
  data: {
    header: string,
    message: string,
    buttons?: any[]
  }
) => {
  dialog.open(AlertComponent, {
    width: '20em',
    data
  });
}

export const appShowLoading = (
  dialog: MatDialog,
  message = 'Carregando...'
) => {
  return dialog.open(AlertComponent, {
    width: '20em',
    data: {
      header: message,
      spinner: true
    }
  });
}

export const appCatchError = (
  dialog: MatDialog,
  message = 'Erro desconhecido',
  header = 'Ocorreu um Erro'
) => {
  return (error: any) => {
    const errorType = error.error ? error.error.message : error.message;

    switch (errorType) {
      case 'PersonSocialSecurityNumberMustBeValid':
        header = 'Pessoa';
        message = 'CPF deve ser um número válido!';
        break;
      case 'PersonDateOfBirthMustBeInThePast':
        header = 'Pessoa';
        message = 'Data de nascimento deve estar no passado';
        break;
      case 'PersonContactsAtLeastOneRequired':
      case 'PersonContactsRequired':
        header = 'Pessoa';
        message = 'Pelo menos um contato deverá ser informado!';
        break;
      default:
        console.error(error);
    }

    dialog.open(AlertComponent, {
      width: '20em',
      data: {
        header,
        message,
        buttons: ['OK']
      }
    });
  }
}