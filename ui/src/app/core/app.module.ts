import { NgModule  }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule, JsonpModule}    from '@angular/http';
import {CalendarModule , InputTextModule, ButtonModule} from 'primeng/primeng';
import { AppRoutingModule } from './app-routing.module';
import {XIRR} from '../finance/xirr/xirr.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { Angular2FontawesomeModule } from 'angular2-fontawesome/angular2-fontawesome';
import {XirrService} from '../finance/xirr/xirr-service';
import {XirrRequest} from '../finance/xirr/xirrRequestVO';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    Angular2FontawesomeModule,
    CalendarModule,
    InputTextModule,
    ButtonModule
  ],
  declarations: [
    XIRR
  ],
  providers: [ XirrService , XirrRequest],
  bootstrap: [ XIRR ]
})
export class AppModule { }
