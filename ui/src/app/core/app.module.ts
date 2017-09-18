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
import {XirrRequest} from '../finance/sip/scheme/xirrRequestVO';
import {NAVBAR} from '../finance/navigation/nav.component';
import {FundService} from '../finance/addFund/fundservice';
import {AddCompany} from '../finance/addFund/addCompany/addCompany.component';
import {AddFunds} from '../finance/addFund/addFund.component'
import {AddFundsDetails} from '../finance/addFund/addfundDetails/addFundDetails.component'
import {AddProfile } from '../finance/addFund/addProfile/addProfile.component'
import {Sip} from '../finance/sip/sip.component';
import {SipService as schemeSipService} from '../finance/sip/scheme/sip-service';
import {NavService} from '../finance/addFund/addfundDetails/nav.service';
import {DataTableModule,SharedModule,DataGridModule, PanelModule, DialogModule, ChartModule} from 'primeng/primeng';
import {AccordionModule, MessagesModule, DropdownModule, ToggleButtonModule, } from 'primeng/primeng';
import {MdProgressSpinnerModule} from '@angular/material';
import {SipScheme} from '../finance/sip/scheme/sip.scheme.component';
import {SipService  } from '../finance/sip/sip-service';
import {Chart} from '../finance/addFund/chart/chart.component';
import {ChartService} from '../finance/addFund/chart/chart.service';
import { PensionService} from '../finance/pension/sip-service';
import {SchemePensionService } from '../finance/pension/scheme/sip-service';
import { Pension} from '../finance/pension/sip.component';
import {PensionScheme} from '../finance/pension/scheme/sip.scheme.component';
import { LoanService} from '../finance/loan/sip-service';
import {SchemeLoanService } from '../finance/loan/scheme/sip-service';
import { Loan} from '../finance/loan/sip.component';
import {LoanScheme} from '../finance/loan/scheme/sip.scheme.component';
import {EventService} from '../common/EventService';
import {Stock} from '../finance/addStock/addStock.component';
import {StockService} from '../finance/addStock/stockService';
import {SearchStock} from '../finance/addStock/searchStock/searchStock.component';
import {AddStockDetails} from '../finance/addStock/addStockDetails/addStockDetails.component';
import {PriceChart} from '../finance/priceChart/priceChart.component';
import {PriceChartService} from '../finance/priceChart/priceChart.service';
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
    MdProgressSpinnerModule, DataTableModule,SharedModule,DataGridModule, PanelModule, DialogModule,
    AccordionModule, ChartModule, MessagesModule, DropdownModule, ToggleButtonModule
  ],
  declarations: [
    XIRR,   NAVBAR,    AddCompany, Sip, AddFunds, AddProfile, AddFundsDetails, SipScheme, Chart, 
    Pension, PensionScheme, Loan , LoanScheme,Stock , SearchStock,AddStockDetails,PriceChart
  ],
  providers: [ XirrService , XirrRequest, FundService, SipService, NavService, schemeSipService, 
  ChartService, EventService,
  PensionService,SchemePensionService, SchemeLoanService, LoanService, StockService,PriceChartService],
  bootstrap: [ XIRR ]
})
export class AppModule { }
