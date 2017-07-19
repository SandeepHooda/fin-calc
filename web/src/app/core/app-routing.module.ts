import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { XIRR }   from '../finance/xirr/xirr.component';
import {AddFund} from '../finance/addFund/addfund.component';
import {Sip} from '../finance/sip/sip.component';

const routes: Routes = [
  { path: '', redirectTo: '/sip', pathMatch: 'full' },
   { path: 'lumpsump',  component: AddFund },
   { path: 'sip',  component: Sip }
  
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{useHash:true}) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
