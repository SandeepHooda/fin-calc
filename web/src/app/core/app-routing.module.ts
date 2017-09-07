import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { XIRR }   from '../finance/xirr/xirr.component';
import {AddFunds} from '../finance/addFund/addFund.component'
import {Stock} from '../finance/addStock/addStock.component'
import {Sip} from '../finance/sip/sip.component';
import {Chart} from '../finance/addFund/chart/chart.component';
import {Pension} from '../finance/pension/sip.component';
import { Loan} from '../finance/loan/sip.component';
const routes: Routes = [
  { path: '', redirectTo: '/Stock', pathMatch: 'full' },
  { path: 'Stock',  component: Stock },
   { path: 'lumpsump',  component: AddFunds },
   { path: 'sip',  component: Sip },
   //{ path: 'Analytics',  component: Chart },
   { path: 'Pension',  component: Pension },
   { path: 'Loan',  component: Loan }
   
  
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{useHash:true}) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
