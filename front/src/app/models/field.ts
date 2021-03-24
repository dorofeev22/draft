import {valueOrDefault} from '../utils/object-utils';

export class Field {
  constructor(type: FieldTypes, formControlName: string, label: string, id?: string, required?: boolean, editing?: FieldEdit) {
    this.type = type;
    this.formControlName = formControlName;
    this.label = label;
    this.id = formControlName;
    this.required = valueOrDefault<boolean>(required, false);
    this.editing = valueOrDefault<FieldEdit>(editing, new FieldEdit());
  }
  type: FieldTypes;
  formControlName: string;
  label: string;
  id: string;
  required: boolean;
  editing: FieldEdit;
}

export class FieldEdit {
  constructor(visible?: boolean, editable?: boolean) {
    this.visible = valueOrDefault<boolean>(visible, true);
    this.editable = valueOrDefault<boolean>(editable, true);
  }
  editable: boolean;  // can be edit
  visible: boolean;   // visible when form editing
}

export enum FieldTypes {
  input = 'INPUT',
  select = 'SELECT',
  date = 'DATE'
}

export class FieldCreationModel {
  constructor(formControlName: string, label: string, editing?: FieldEdit, required?: boolean) {
    this.formControlName = formControlName;
    this.label = label;
    this.editing = editing;
    this.required = required;
  }
  formControlName: string;
  label: string;
  required: boolean;
  editing: FieldEdit;
}

export function createInput(creation: FieldCreationModel): Field {
  return new Field(FieldTypes.input, creation.formControlName, creation.label, undefined, creation.required, creation.editing);
}