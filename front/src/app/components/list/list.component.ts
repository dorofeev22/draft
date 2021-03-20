import {Injectable} from '@angular/core';
import {Column} from '../../models/column';
import {RestClientService} from '../../services/rest-client-service';
import {BaseComponent} from '../base-component';
import {SearchParams} from '../../models/search-params';
import {Page} from '../../models/page';
import {LazyLoadEvent} from 'primeng/api';

@Injectable()
export abstract class ListComponent extends BaseComponent {

  public items: any[];
  public columns: Column[];
  public rows: number;
  public totalRecords: number;

  protected constructor(restClientService: RestClientService) {
    super(restClientService);
    this.rows = 10;
    this.items = [];
  }

  public load(event: LazyLoadEvent): void {
    this.find(this.createSearchParam(event.first, event.rows));
  }

  protected find(params: SearchParams): void {
    this.sub.add(
      this.processRequest<Page<any>>(this.restClientService.get<Page<any>>(this.path, params), this.loadItems.bind(this))
    );
  }

  private createSearchParam(off: number, lim: number): any {
    return { $offset: off, $limit: lim };
  }

  private loadItems(page: Page<any>): void {
    this.totalRecords = page.totalElements;
    this.items = page.items;
  }

}