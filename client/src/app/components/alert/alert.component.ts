import { Inject, OnInit } from "@angular/core";
import { Component } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";

export class AlertModel {
  header: string;
  message: string;
  buttons: string[] | { text: string, handler: () => void}[];
  spinner: boolean;
}

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: AlertModel
  ) { }

  ngOnInit() {
  }
}