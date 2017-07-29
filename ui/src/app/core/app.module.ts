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
import {AddCompany} from '../finance/addFund/addCompany/addCompany.component';
import {AddFunds} from '../finance/addFund/addFund.component'
import {AddFundsDetails} from '../finance/addFund/addfundDetails/addFundDetails.component'
import {AddProfile } from '../finance/addFund/addProfile/addProfile.component'
import {Sip} from '../finance/sip/sip.component';
import {SipService} from '../finance/sip/sip-service';
import {NavService} from '../finance/addFund/addfundDetails/nav.service';
import {DataTableModule,SharedModule,DataGridModule, PanelModule} from 'primeng/primeng';
import {MdProgressSpinnerModule} from '@angular/material';

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
    ListboxModule,
    MdProgressSpinnerModule, DataTableModule,SharedModule,DataGridModule, PanelModule
  ],
  declarations: [
    XIRR,   NAVBAR,    AddCompany, Sip, AddFunds, AddProfile, AddFundsDetails
  ],
  providers: [ XirrService , XirrRequest, FundService, SipService, NavService],
  bootstrap: [ XIRR ]
})
export class AppModule { }
