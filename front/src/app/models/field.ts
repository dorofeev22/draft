export class Field {
  constructor(type: FieldTypes, formControlName: string, label: string, id?: string, required?: boolean) {
    this.type = type;
    this.formControlName = formControlName;
    this.label = label;
    this.id = formControlName;
    this.required = required || false;
  }
  type: FieldTypes;
  formControlName: string;
  label: string;
  id: string;
  required: boolean;
}

export enum FieldTypes {
  input = 'INPUT',
  select = 'SELECT',
  date = 'DATE'
}

export function createInput(formControlName: string, label: string, required?: boolean): Field {
  return new Field(FieldTypes.input, formControlName, label, undefined, required);
}