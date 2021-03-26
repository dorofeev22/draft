import {Injectable} from '@angular/core';
import {BaseComponent} from '../base-component';
import {RestClientService} from '../../services/rest-client-service';
import {createInput, Field, FieldCreationModel, FieldEdit} from '../../models/field';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Item} from '../../models/Item';
import {isEmpty} from '../../utils/object-utils';

@Injectable()
export abstract class ItemComponent extends BaseComponent {

  public fields: Field[];
  public form: FormGroup;
  public submitting: boolean;
  protected itemId: string;
  private readonly editing: boolean;

  protected constructor(restClientService: RestClientService,
                        protected formBuilder: FormBuilder,
                        protected activatedRoute: ActivatedRoute,
                        protected router: Router,
                        itemName: string) {
    super(restClientService, itemName);
    this.sub.add(this.activatedRoute.params.subscribe((params: Params) => this.itemId = params.id));
    this.editing = !isEmpty(this.itemId);
    this.header = `${itemName}`;
    this.form = this.formBuilder.group({});
    this.fields = [];
    this.submitting = false;
  }

  protected createInput(formControlName: string, label: string, editing?: FieldEdit, required?: boolean): void {
    this.createField(new FieldCreationModel(formControlName, label, editing, required), createInput);
  }

  protected createField(filedCreationModel: FieldCreationModel, fieldCreationFunction): void {
    if (this.canCreateField(filedCreationModel)) {
      this.fields.push(fieldCreationFunction(filedCreationModel));
    }
  }

  private canCreateField(filedCreationModel: FieldCreationModel): boolean {
    return !(this.editing && !isEmpty(filedCreationModel.editing) && !filedCreationModel.editing.visible);
  }

  protected createForm(): void {
    this.createFormControls();
    if (this.editing) {
      this.fillForm();
    } else {
      this.loading = false;
    }
  }

  private createFormControls(): void {
    this.fields.forEach(f => this.form.addControl(f.formControlName, this.createFormControl(f)));
  }

  private createFormControl(field: Field): FormControl {
    return field.required ? new FormControl('', Validators.required) : new FormControl('');
  }

  private fillForm(): void {
    this.sub.add(
      this.processRequest<Item>(
        this.restClientService.getById<Item>(this.apiPath, this.itemId),
        this.patchFormControls.bind(this),
        () => console.log('item has been loaded'))
    );
  }

  private patchFormControls(item: Item): void {
    Object.keys(item).forEach(fieldName => this.form.controls[fieldName]?.patchValue(item[fieldName]));
    this.disableElements();
    this.loading = false;
  }

  protected disableElements(): void {
    this.fields.forEach(f => {
      if (!f.editing.editable) {
        this.form.controls[f.formControlName].disable();
      }
    });
  }

  public save(): void {
    this.submitting = true;
    this.sub.add(
      this.processRequest<any>(
        this.restClientService.save<any>(this.apiPath, this.itemId, this.form.value),
        this.navigateToList.bind(this),
        this.savingCallback.bind(this)
      )
    );
  }

  navigateToList(): void {
    this.router.navigate([this.listRoute]);
  }

  private savingCallback(): void {
    this.submitting = false;
  }

}