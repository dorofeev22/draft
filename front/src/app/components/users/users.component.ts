import {Component, OnInit} from '@angular/core';
import {ListComponent} from '../list/list.component';
import {RestClientService} from '../../services/rest-client-service';

@Component({
  selector: 'app-users',
  templateUrl: '../list/list.component.html',
})
export class UsersComponent extends ListComponent implements OnInit {

  constructor(restClientServer: RestClientService) {
    super(restClientServer, 'user');
  }

  ngOnInit(): void {
    this.createColumn('login', 'Login');
    this.createColumn('name', 'Name');
    this.createDateColumn('creationMoment', 'Moment of creation');
    this.createDeleteAndEditColumn();
  }

}