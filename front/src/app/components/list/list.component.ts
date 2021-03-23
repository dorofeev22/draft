import {Injectable} from '@angular/core';
import {
  Column,
  createActionColumn,
  createColumn,
  createDateColumn,
  createDeleteAction,
  createEditAction
} from '../../models/column';
import {RestClientService} from '../../services/rest-client-service';
import {BaseComponent} from '../base-component';
import {Page} from '../../models/page';
import {LazyLoadEvent} from 'primeng/api';
import {Item} from '../../models/Item';

@Injectable()
export abstract class ListComponent extends BaseComponent {

  public items: Item[];
  public columns: Column[];
  public rows: number;
  public totalRecords: number;
  public itemDeleting: boolean;
  public createNewItemLabel: string;
  private currentSearchParam: {};

  protected constructor(restClientService: RestClientService, itemRoute: string) {
    super(restClientService, itemRoute);
    this.rows = 10;
    this.items = [];
    this.columns = [];
    this.itemDeleting = false;
    this.createNewItemLabel = 'Create';
    this.header = `${this.itemRoute} list`;
  }

  protected createColumn(field: string, header: string, filtering?: boolean): void {
    this.columns.push(createColumn(field, header, filtering));
  }

  protected createDateColumn(field: string, header: string, filtering?: boolean): void {
    this.columns.push(createDateColumn(field, header, filtering));
  }

  protected createDeleteAndEditColumn(): void {
    this.columns.push(createActionColumn([createEditAction(this.itemRoute), createDeleteAction()]));
  }

  public load(event: LazyLoadEvent): void {
    this.createSearchParam(event.first, event.rows);
    this.find();
  }

  protected find(): void {
    this.sub.add(
      this.processRequest<Page<Item>>(
        this.restClientService.get<Page<Item>>(this.apiPath, this.currentSearchParam),
        this.loadItems.bind(this),
        this.loadItemsCallback.bind(this)
      )
    );
  }

  private createSearchParam(off: number, lim: number): any {
    this.currentSearchParam = { $offset: off, $limit: lim };
    return this.currentSearchParam;
  }

  private loadItems(page: Page<Item>): void {
    this.totalRecords = page.totalElements;
    this.items = page.items;
  }

  private loadItemsCallback(): void {
    this.loading = false;
  }

  deleteItem(id: string): void {
    this.itemDeleting = true;
    this.sub.add(
      this.processRequest<any>(
        this.restClientService.delete(this.apiPath, id), this.find.bind(this), this.deleteItemCallback.bind(this)
      )
    );
  }

  private deleteItemCallback(): void {
    this.itemDeleting = false;
  }
}
