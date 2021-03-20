import {Component, Input, OnInit} from '@angular/core';
import {Dialog} from '../../models/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './message-dialog.component.html',
})
export class MessageDialogComponent implements OnInit {

  @Input() dialog: Dialog;
  constructor() { }

  ngOnInit(): void {
  }

}