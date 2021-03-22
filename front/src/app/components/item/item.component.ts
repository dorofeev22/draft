import {Injectable} from '@angular/core';
import {BaseComponent} from '../base-component';
import {RestClientService} from '../../services/rest-client-service';
import {Field} from '../../models/field';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Injectable()
export abstract class ItemComponent extends BaseComponent {

  public fields: Field[];
  public form: FormGroup;
  public submitting: boolean;
  protected itemId: string;

  protected constructor(restClientService: RestClientService,
                        protected formBuilder: FormBuilder,
                        protected activatedRoute: ActivatedRoute,
                        protected router: Router,
                        itemName: string) {
    super(restClientService, itemName);
    this.submitting = false;
    this.header = `${itemName}`;
    this.sub.add(this.activatedRoute.params.subscribe((params: Params) => this.itemId = params.id));
    this.form = this.formBuilder.group({});
  }

  protected createForm(): void {
    this.createFormControls();
    this.loading = false;
  }

  private createFormControls(): void {
    this.fields.forEach(f => this.form.addControl(f.formControlName, this.createFormControl(f)));
  }

  private createFormControl(field: Field): FormControl {
    return field.required ? new FormControl('', Validators.required) : new FormControl('');
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