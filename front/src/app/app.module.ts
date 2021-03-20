import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';
import {UsersComponent} from './components/list/users.component';
import {RestClientService} from './services/rest-client-service';
import {HttpClientModule} from '@angular/common/http';
import {PanelModule} from 'primeng/panel';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog';
import {ButtonModule} from 'primeng/button';
import {MessageDialogComponent} from './components/dialog/message-dialog.component';
import {TableComponent} from './components/table/table.component';

@NgModule({
  declarations: [
    AppComponent, UsersComponent, MessageDialogComponent, TableComponent
  ],
  imports: [
    BrowserModule, HttpClientModule, DialogModule, PanelModule, TableModule, BrowserAnimationsModule, ButtonModule
  ],
  providers: [RestClientService],
  bootstrap: [AppComponent]
})
export class AppModule { }