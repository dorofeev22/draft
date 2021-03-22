import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {Field, FieldTypes} from '../../../models/field';
import {ButtonType} from '../button/button-type';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  @Input() loading: boolean;
  @Input() formGroup: FormGroup;
  @Input() fields: Field[];
  @Input() submitting: boolean;
  FieldTypes = FieldTypes;
  ButtonType = ButtonType;

  constructor() { }

  @Output() submitForm = new EventEmitter();
  @Output() cancel = new EventEmitter();

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.submitForm.emit();
  }

  onCancel(): void {
    this.cancel.emit();
  }
}