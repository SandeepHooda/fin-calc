import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { XIRR }   from '../finance/xirr/xirr.component';
import {AddFunds} from '../finance/addFund/addFund.component'
import {Sip} from '../finance/sip/sip.component';

const routes: Routes = [
  { path: '', redirectTo: '/sip', pathMatch: 'full' },
   { path: 'lumpsump',  component: AddFunds },
   { path: 'sip',  component: Sip }
  
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{useHash:true}) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
