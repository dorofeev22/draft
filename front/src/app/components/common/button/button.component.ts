import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ButtonType} from './button-type';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html'
})
export class ButtonComponent implements OnInit {

  @Input() type: ButtonType;
  @Input() label: string;
  @Input() disabled: boolean;

  constructor() {
    if (!this.type) {
      this.type = ButtonType.button;
    }
  }

  @Output() buttonClick = new EventEmitter();

  ngOnInit(): void {
  }

  onClick(): void {
    if (this.type !== ButtonType.submit) {
      this.buttonClick.emit();
    }
  }

}