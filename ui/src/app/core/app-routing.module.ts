import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { XIRR }   from '../finance/xirr/xirr.component';


const routes: Routes = [
  { path: '', redirectTo: '/xirr', pathMatch: 'full' },
  { path: 'xirr',  component: XIRR }
  
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
