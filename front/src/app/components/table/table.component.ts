import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Column} from '../../models/column';
import {LazyLoadEvent} from 'primeng/api';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
})
export class TableComponent implements OnInit {

  @Input() items: any[];
  @Input() columns: Column[];
  @Input() rows: number;
  @Input() totalRecords: number;
  @Input() loading: boolean;

  constructor() { }

  @Output() loadItems = new EventEmitter();

  ngOnInit(): void {
  }

  loadData(event: LazyLoadEvent): void {
    this.loadItems.emit(event);
  }

}