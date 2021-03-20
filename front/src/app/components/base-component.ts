import {Injectable, OnDestroy} from '@angular/core';
import {RestClientService} from '../services/rest-client-service';
import {Observable, Subscription} from 'rxjs';
import {Dialog} from '../models/dialog';
import {ApiError} from '../models/apiError';

@Injectable()
export abstract class BaseComponent implements OnDestroy {

  public loading: boolean;
  public header: string;
  public dialog: Dialog;
  protected sub: Subscription;
  protected path: string;

  protected constructor(protected restClientService: RestClientService) {
    this.loading = true;
    this.sub = new Subscription();
    this.dialog = new Dialog();
  }

  protected processRequest<T>(observable: Observable<T>, responseCallback): void {
    observable.subscribe(
      response => {
        responseCallback(response);
      }, (apiError: ApiError) => {
        this.showErrorMessage(apiError);
      }, () => {
        this.loading = true;
      });
  }

  private showErrorMessage(apiError: ApiError): void {
    this.displayDialog('Error', apiError.status >= 500 ? 'Service unavailable' : apiError?.error?.errorMessage);
  }

  protected displayDialog(title: string, message: string): void {
    this.dialog.display = true;
    this.dialog.title = title;
    this.dialog.message = message;
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

}