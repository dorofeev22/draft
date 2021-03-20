import {Component, OnInit} from '@angular/core';
import {ListComponent} from './list.component';
import {RestClientService} from '../../services/rest-client-service';
import {createColumn, createDateColumn} from '../../models/column';

@Component({
  selector: 'app-users',
  templateUrl: './list.component.html',
})
export class UsersComponent extends ListComponent implements OnInit {

  constructor(restClientServer: RestClientService) {
    super(restClientServer);
    this.path = '/users';
    this.header = 'User list';
  }

  ngOnInit(): void {
    this.columns = [
      createColumn('login', 'Login'),
      createColumn('name', 'Name'),
      createDateColumn('creationMoment', 'Moment of creation')
    ];
  }

}