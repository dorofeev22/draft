import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {isEmpty} from '../utils/object-utils';

@Injectable({
  providedIn: 'root'
})
export class RestClientService {

  constructor(private httpClient: HttpClient) { }

  private getHttpParams(params: {}): HttpParams {
    let httpParams = new HttpParams();
    for (const param of Object.keys(params)) {
      httpParams = httpParams.set(param.toString(), params[param].toString());
    }
    return httpParams;
  }

  private getUrl(path: string, itemId?: string): string {
    return `/${path}`.concat(isEmpty(itemId) ? '' : `/${itemId}`);
  }

  public get<T>(path: string, params: {}): Observable<T> {
    return this.httpClient.get<T>(this.getUrl(path), {params: this.getHttpParams(params)});
  }

  public getById<T>(path: string, itemId: string): Observable<T> {
    return this.httpClient.get<T>(this.getUrl(path, itemId));
  }

  public save<T>(path: string, itemId: string, body: {}): Observable<T> {
    const url = this.getUrl(path, itemId);
    return isEmpty(itemId) ? this.httpClient.post<T>(url, body) : this.httpClient.put<T>(url, body);
  }

  public delete<T>(path: string, itemId: string): Observable<T> {
    return this.httpClient.delete<T>(this.getUrl(path, itemId));
  }

}
