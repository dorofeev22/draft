import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActionColumn, ActionType, ColumnType, DataColumn} from '../../../models/column';
import {LazyLoadEvent} from 'primeng/api';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
})
export class TableComponent implements OnInit {

  @Input() items: any[];
  @Input() columns: (DataColumn | ActionColumn) [];
  @Input() rows: number;
  @Input() totalRecords: number;
  @Input() loading: boolean;
  @Input() rowDeleting: boolean;
  ColumnType = ColumnType;
  ActionType = ActionType;

  constructor() { }

  @Output() loadData = new EventEmitter();
  @Output() deleteRow = new EventEmitter<string>();

  ngOnInit(): void {
  }

  onLoadData(event: LazyLoadEvent): void {
    this.loadData.emit(event);
  }

  onDeleteRow(id: string): void {
    this.deleteRow.emit(id);
  }

}
