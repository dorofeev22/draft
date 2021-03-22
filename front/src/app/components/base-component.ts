import {Injectable, OnDestroy} from '@angular/core';
import {RestClientService} from '../services/rest-client-service';
import {Observable, Subscription} from 'rxjs';
import {Dialog} from '../models/dialog';
import {ApiError} from '../models/apiError';
import {isEmpty} from '../utils/object-utils';

@Injectable()
export abstract class BaseComponent implements OnDestroy {

  public loading: boolean;
  public header: string;
  public dialog: Dialog;
  protected sub: Subscription;
  protected apiPath: string;
  protected listRoute: string;
  public itemRoute: string;

  protected constructor(protected restClientService: RestClientService, itemName: string) {
    this.loading = true;
    this.sub = new Subscription();
    this.dialog = new Dialog();
    this.initRoutesAndPaths(itemName);
  }

  protected initRoutesAndPaths(itemName: string): void {
    this.itemRoute = itemName;
    this.listRoute = `${itemName}s`;
    this.apiPath = this.listRoute;
  }

  protected processRequest<T>(observable: Observable<T>, responseCallback, completeCallback): void {
    observable.subscribe(
      response => {
        responseCallback(response);
      }, (apiError: ApiError) => {
        this.showErrorMessage(apiError);
      }).add(() => completeCallback());
  }

  protected showErrorMessage(apiError: ApiError): void {
    this.displayDialog('Error', this.extractErrorMessage(apiError));
  }

  private extractErrorMessage(apiError: ApiError): string {
    const status = apiError.status;
    if (status >= 500) {
      return 'Service unavailable';
    } else {
      return isEmpty(apiError.error.errorMessage) ? apiError.statusText : apiError.error.errorMessage;
    }
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