import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

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

  public get<T>(path: string, params: {} ): Observable<T> {
    return this.httpClient.get<T>(path, {params: this.getHttpParams(params)});
  }

}