import {Component, OnInit} from '@angular/core';
import {ItemComponent} from '../item/item.component';
import {RestClientService} from '../../services/rest-client-service';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {createInput} from '../../models/field';

@Component({
  selector: 'app-user',
  templateUrl: '../item/item.component.html'
})
export class UserComponent extends ItemComponent implements OnInit {

  constructor(restClientServer: RestClientService, formBuilder: FormBuilder, activatedRoute: ActivatedRoute, router: Router) {
    super(restClientServer, formBuilder, activatedRoute, router, 'user');
  }

  ngOnInit(): void {
    this.fields = [
      createInput('name', 'User name'),
      createInput('login', 'Login', true),
      createInput('password', 'Password', true)
    ];
    this.createForm();
  }

}