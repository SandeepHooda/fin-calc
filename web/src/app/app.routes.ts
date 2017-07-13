import { Routes } from '@angular/router';
import { XIRR } from './finance/xirr/xirr.component';


import { DataResolver } from './app.resolver';

export const ROUTES: Routes = [
  { path: '',      component: XIRR },
  { path: 'xirr',  component: XIRR }
];