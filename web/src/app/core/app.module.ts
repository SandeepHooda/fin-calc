import { NgModule  }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule, JsonpModule}    from '@angular/http';
import {CalendarModule , InputTextModule, ButtonModule, ListboxModule} from 'primeng/primeng';
import { AppRoutingModule } from './app-routing.module';
import {XIRR} from '../finance/xirr/xirr.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { Angular2FontawesomeModule } from 'angular2-fontawesome/angular2-fontawesome';
import {XirrService} from '../finance/xirr/xirr-service';
import {XirrRequest} from '../finance/sip/xirrRequestVO';
import {NAVBAR} from '../finance/navigation/nav.component';
import {FundService} from '../finance/addFund/fundservice';
import {AddFund} from '../finance/addFund/addfund.component';
import {Sip} from '../finance/sip/sip.component';
import {SipService} from '../finance/sip/sip-service';

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
    ButtonModule,
    ListboxModule
  ],
  declarations: [
    XIRR,   NAVBAR,    AddFund, Sip
  ],
  providers: [ XirrService , XirrRequest, FundService, SipService],
  bootstrap: [ XIRR ]
})
export class AppModule { }
