import {Component, OnInit} from '@angular/core';
import {ItemComponent} from '../item/item.component';
import {RestClientService} from '../../services/rest-client-service';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {FieldEdit} from '../../models/field';

@Component({
  selector: 'app-user',
  templateUrl: '../item/item.component.html'
})
export class UserComponent extends ItemComponent implements OnInit {

  constructor(restClientServer: RestClientService, formBuilder: FormBuilder, activatedRoute: ActivatedRoute, router: Router) {
    super(restClientServer, formBuilder, activatedRoute, router, 'user');
  }

  ngOnInit(): void {
    this.createInput('name', 'User name');
    this.createInput('login', 'Login', new FieldEdit(true, false), true);
    this.createInput('password', 'Password', new FieldEdit(false), true);
    this.createForm();
  }

}
