import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { XIRR }   from '../finance/xirr/xirr.component';
import {AddFunds} from '../finance/addFund/addFund.component'
import {Sip} from '../finance/sip/sip.component';
import {Chart} from '../finance/addFund/chart/chart.component';
import {Pension} from '../finance/pension/sip.component';
const routes: Routes = [
  { path: '', redirectTo: '/lumpsump', pathMatch: 'full' },
   { path: 'lumpsump',  component: AddFunds },
   { path: 'sip',  component: Sip },
   { path: 'Analytics',  component: Chart },
   { path: 'Pension',  component: Pension }
  
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{useHash:true}) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
