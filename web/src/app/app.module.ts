import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { Angular2FontawesomeModule } from 'angular2-fontawesome/angular2-fontawesome';
import {  NgModule,  ApplicationRef} from '@angular/core';
import {  removeNgStyles,  createNewHosts,  createInputTransfer} from '@angularclass/hmr';
import {  RouterModule,  PreloadAllModules} from '@angular/router';

/*
 * Platform and Environment providers/directives/pipes
 */
import { ENV_PROVIDERS } from './environment';
import { ROUTES } from './app.routes';
// App is our top level component
import { AppComponent } from './app.component';
import { APP_RESOLVER_PROVIDERS } from './app.resolver';
import {XIRR} from './finance/xirr/xirr.component';
import {XirrService} from './finance/xirr/xirr-service';
import {XirrRequest} from './finance/xirr/xirrRequestVO'
import {CalendarModule , InputTextModule, ButtonModule} from 'primeng/primeng';

import '../styles/styles.scss';
import '../styles/headings.css';

// Application wide providers
const APP_PROVIDERS = [
  ...APP_RESOLVER_PROVIDERS
];



/**
 * `AppModule` is the main entry point into Angular2's bootstraping process
 */
@NgModule({
  bootstrap: [ XIRR ],
  declarations: [
   XIRR,AppComponent
  ],
  imports: [ // import Angular's modules
    BrowserAnimationsModule,
    Angular2FontawesomeModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    CalendarModule,
    InputTextModule,
    ButtonModule,
    RouterModule.forRoot(ROUTES, { useHash: true, preloadingStrategy: PreloadAllModules })
  ],
  providers: [ // expose our Services and Providers into Angular's dependency injection
    ENV_PROVIDERS,
    APP_PROVIDERS, 
    XirrService , XirrRequest
  ]
})
export class AppModule {

  constructor(
  ) {}

  

}
