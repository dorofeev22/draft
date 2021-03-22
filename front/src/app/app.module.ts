import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';
import {UsersComponent} from './components/users/users.component';
import {RestClientService} from './services/rest-client-service';
import {HttpClientModule} from '@angular/common/http';
import {PanelModule} from 'primeng/panel';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog';
import {ButtonModule} from 'primeng/button';
import {MessageDialogComponent} from './components/common/dialog/message-dialog.component';
import {TableComponent} from './components/common/table/table.component';
import {FormComponent} from './components/common/form/form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {UserComponent} from './components/users/user.component';
import {RouterModule} from '@angular/router';
import {ButtonComponent} from './components/common/button/button.component';
import {LinkComponent} from './components/common/link/link.component';
import {InputTextModule} from 'primeng/inputtext';

const appRoutes = RouterModule.forRoot([
  {path: '', redirectTo: '/users', pathMatch: 'full'},
  {path: 'users', component: UsersComponent},
  {path: 'user', component: UserComponent},
  {path: 'user/:id', component: UserComponent}
], {
  useHash: true
});

@NgModule({
  declarations: [
    AppComponent, UsersComponent, MessageDialogComponent, TableComponent, FormComponent, UserComponent, ButtonComponent, LinkComponent
  ],
  imports: [
    appRoutes, BrowserModule, HttpClientModule, DialogModule, PanelModule, TableModule, BrowserAnimationsModule, ButtonModule,
    ReactiveFormsModule, InputTextModule
  ],
  providers: [RestClientService],
  bootstrap: [AppComponent]
})
export class AppModule {
}