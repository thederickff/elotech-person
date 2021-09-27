import { Pipe, PipeTransform } from "@angular/core";

@Pipe({ name: 'socialSecurityNumber' })
export class SocialSecurityNumberPipe implements PipeTransform {

  transform(value: string) {
    if (value.length != 11) {
      return value;
    }

    return `${value.substr(0, 3)}.${value.substr(3, 3)}.${value.substr(6, 3)}-${value.substr(9)}`;
  }
}